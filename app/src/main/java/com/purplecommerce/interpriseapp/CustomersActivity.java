package com.purplecommerce.interpriseapp;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.purplecommerce.interpriseapp.Database.ChangeLogDBManager;
import com.purplecommerce.interpriseapp.Database.CustomersDBManager;
import com.purplecommerce.interpriseapp.Database.CustomersTable;
import com.purplecommerce.interpriseapp.Services.CustomersService;
import com.purplecommerce.interpriseapp.SessionManager.SessionManager;
import com.purplecommerce.interpriseapp.Utils.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TimeZone;

import io.realm.RealmResults;

public class CustomersActivity extends AppCompatActivity {

    LinearLayout Orders_parent_layout , ll_toolbar , ll_refresh ;
    TextView nxt , previous , find , last_update ;
    int pageCount = 0 , totalPage = 1  , perpagecount = Utils.PerPageCount , remainingcount = 0  ;
    EditText ed_srch_order ;
    CustomersDBManager dbManager ;
    RealmResults<CustomersTable> CustomerRows ;
    TextView OrderPageCount ;
    SessionManager sm ;
    public static String prevoiusOne = "test" ;
    ArrayList<String> hs_page = new ArrayList<>();
    ArrayList<String> hs_orderpage = new ArrayList<>();
    ChangeLogDBManager changeLogDBManager ;
    private CustomersRequestReceiver receiver;
    Dialog progress_dialog ;
    ListView SearchlistView ;
    ArrayList<String> AllCustomerCodes = new ArrayList<>();
    ArrayAdapter<String> SearchAdapter ;
    String lastUpdate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);


        init();


        ll_toolbar.addView(Toolbartxts("HOME > "));
        ll_toolbar.addView(Toolbartxts("CUSTOMERS"));

        ////////////////////register broadcast receiver

        IntentFilter filter = new IntentFilter(CustomersService.BROADCASTCUSTOMER);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new CustomersRequestReceiver();
        registerReceiver(receiver, filter);

        /////////////////////



        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        final String date = df.format(Calendar.getInstance().getTime());
        StringTokenizer tokenizer = new StringTokenizer(date , "T");

        String to = tokenizer.nextToken();
        String totime = tokenizer.nextToken();
        String todatetime = "" ;

        // india time
        try {
            DateFormat df2 = new SimpleDateFormat("HH:mm:ss");
            Date dateIST= null;
            dateIST = df2.parse(totime);
            df2.setTimeZone(TimeZone.getTimeZone("GMT+1"));
            todatetime = to + "T" + String.valueOf(df2.format(dateIST)) ;
        } catch (ParseException e) {
            e.printStackTrace();
        }



        final String finalTodatetime = todatetime;

        ll_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //http://seventies.apexhelp.co.uk:82/Interprise.Web.Services/changelog/Customer?from=2017-07-07&to=2017-11-14

                if (changeLogDBManager.GetChangeLogAccordingName((Utils.CustomerChangeLog))==null){
                    last_update.setText("Updating...");
                    lastUpdate = "2016-06-11T11:55:37";
                }else {
                    lastUpdate = changeLogDBManager.GetChangeLogAccordingName(Utils.CustomerChangeLog).getChangeLogLastUpdate();
                    StringTokenizer st = new StringTokenizer(lastUpdate , "T");
                    last_update.setText(st.nextToken()+"\n"+st.nextToken());
                }

                 String PageSize = "2000";
                 String PageNumber = "1";

                progress_dialog.show();
                Log.e("**to time",""+ finalTodatetime);

                Intent serviceIntent = new Intent(CustomersActivity.this, CustomersService.class);
                serviceIntent.putExtra("URL","/changelog/Customer?from="+ "2016-06-11T11:55:37" +"&to="+ finalTodatetime +"&page[number]=1&page[size]="+PageSize);
                serviceIntent.putExtra("TODATETIME" , finalTodatetime);
                startService(serviceIntent);
            }
        });



        if (dbManager.CustomerTableisEmpty()){

            dbManager.SaveCustomers("CUST-000001" , "Toy Prospect UK Ltd" , "0800 032 4000" ,"info@toyprospect.co.uk" , "Retail" ,"" , "SHIP-000002" , "United Kingdom" , "CCTC-000002");

            dbManager.SaveCustomers("CUST-000002" , "Undergound Toys UK" , "0194 494839" ,"simon.mermon@underground.co.uk" , "Wholesale" ,"" , "SHIP-000003" , "United Kingdom" , "CCTC-000003");

            dbManager.SaveCustomers("CUST-000003" , "The Gadget Store" , "0191 948392" ,"tony.sandland@gadgetstore.co.uk" , "Wholesale" ,"" , "SHIP-000004" , "United Kingdom" , "CCTC-000004");

            dbManager.SaveCustomers("CUST-001004" , "We Love Toys" , "01978 828348" ,"cherry.mcclenent@lovetoys.co.uk" , "Wholesale" ,"" , "SHIP-000005" , "United Kingdom" , "CCTC-000005");




        }



        PupulateListFromDbOnFirstPage();


//        ed_srch_order.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//               // SearchlistView.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                CustomersActivity.this.SearchAdapter.getFilter().filter(charSequence.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });



        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ed_srch_order.getText().toString().isEmpty()){
                    Toast.makeText(CustomersActivity.this, "Please Enter Customer Code !!", Toast.LENGTH_SHORT).show();
                }else {

                    RealmResults<CustomersTable> cust = dbManager.getCustomerAccordingCUSTID("CUST-"+ed_srch_order.getText().toString().trim());
                    Log.e("**customer","customer"+cust);
                    if (cust==null){
                        Toast.makeText(CustomersActivity.this, "Customer Code Not Exist !!", Toast.LENGTH_SHORT).show();
                    }else {

                        Utils.hideKeyboard(CustomersActivity.this);
                        Orders_parent_layout.removeAllViews();

                        for (int i = 0 ; i < cust.size() ; i++){
                            Orders_parent_layout.addView(AddOrdersOnPArent(cust.get(i)));
                        }

                    }


                }
            }
        });




        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

           nextClick(Orders_parent_layout , CustomerRows.size() , perpagecount , hs_page , hs_orderpage ,OrderPageCount , CustomerRows);

            }
        });


        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

           PreviousClick(Orders_parent_layout , CustomerRows.size() ,perpagecount ,hs_page ,hs_orderpage , OrderPageCount , CustomerRows);

            }
        });




    }


    public void nextClick(LinearLayout ll_parent1 , int totaldata1 , int perpagecount1 ,
                          ArrayList<String> hs_page1 , ArrayList<String> hs_orderpage1 , TextView pagetx1 , RealmResults<CustomersTable> data1){


        if (!hs_orderpage1.isEmpty()) {


            prevoiusOne = "2";

            if (pageCount + 1 == hs_page1.size()) {
                Toast.makeText(CustomersActivity.this, "Page Finished !!", Toast.LENGTH_SHORT).show();
            } else {

                pageCount = pageCount + 1;

                if (pageCount < hs_page1.size()) {

                    ll_parent1.removeAllViews();

                    int max = Integer.parseInt(hs_orderpage1.get(pageCount));
                    int min = Integer.parseInt(hs_orderpage1.get(pageCount - 1));

                    Log.e("**max", "" + max);
                    Log.e("**min", "" + min);

                    if (max < perpagecount1) {

                        int max2 = min + max;
                        pagetx1.setText(min + "-" + max2 + "/" + totaldata1);
                        Log.e("max2", "for last" + max2);

                        for (int i = min; i < max2; i++) {

                            ll_parent1.addView(AddOrdersOnPArent(CustomerRows.get(i)));

                        }

                    } else {

                        pagetx1.setText(min + "-" + max + "/" + totaldata1);
                        for (int i = min; i < max; i++) {

                            ll_parent1.addView(AddOrdersOnPArent(CustomerRows.get(i)));

                        }

                    }


                } else {
                    //  Toast.makeText(paginationlogic.this, "That' it", Toast.LENGTH_SHORT).show();
                }

            }
        }else {
            Toast.makeText(CustomersActivity.this, "No Pages !!", Toast.LENGTH_SHORT).show();
        }

    }


    public void PreviousClick(LinearLayout ll_parent1 , int totaldata1 , int perpagecount1 ,
                              ArrayList<String> hs_page1 , ArrayList<String> hs_orderpage1 , TextView pagetx1 , RealmResults<CustomersTable> data1){

        if (!hs_orderpage1.isEmpty()) {

            if (prevoiusOne.equals("1")) {

                Toast.makeText(CustomersActivity.this, "Page Finished !!", Toast.LENGTH_SHORT).show();

            } else {

                if (pageCount==0){

                }else {
                    pageCount = pageCount - 1;
                }


                if (pageCount == 0) {

                    prevoiusOne = "1";

                    ll_parent1.removeAllViews();

                    Log.e("order page", "" + hs_orderpage1);
                    Log.e(" page", "" + hs_page1);
                    int max = Integer.parseInt(hs_orderpage1.get(pageCount));
                    pagetx1.setText("1" + "-" + max + "/" + totaldata1);
                    for (int i = 0; i < max; i++) {

                        ll_parent1.addView(AddOrdersOnPArent(data1.get(i)));

                    }


                } else if (pageCount < hs_page1.size()) {

                    prevoiusOne = "2";

                    ll_parent1.removeAllViews();

                    Log.e("order page", "" + hs_orderpage);
                    Log.e(" page", "" + hs_page);
                    int max = Integer.parseInt(hs_orderpage1.get(pageCount));
                    int min = Integer.parseInt(hs_orderpage1.get(pageCount - 1));

                    Log.e("**max", "" + max);
                    Log.e("**min", "" + min);

                    if (max < perpagecount1) {

                        int max2 = min + max;
                        pagetx1.setText(min + "-" + max2 + "/" + totaldata1);
                        Log.e("max2", "for last" + max2);
                        for (int i = min; i < max2; i++) {

                            ll_parent1.addView(AddOrdersOnPArent(data1.get(i)));
                        }


                    } else {

                        ll_parent1.removeAllViews();

                        pagetx1.setText(min + "-" + max + "/" + totaldata1);
                        for (int i = min; i < max; i++) {
                            ll_parent1.addView(AddOrdersOnPArent(data1.get(i)));
                        }

                    }


                } else {
                    //  Toast.makeText(paginationlogic.this, "That' it", Toast.LENGTH_SHORT).show();
                }

            }

        }else {
            Toast.makeText(CustomersActivity.this, "No Pages !!", Toast.LENGTH_SHORT).show();
        }


    }







    public void PupulateListFromDbOnFirstPage(){

        Orders_parent_layout.removeAllViews();

        CustomerRows =  dbManager.getAllrows();

        if (CustomerRows.isEmpty()){
            Toast.makeText(this, "No Customers !!", Toast.LENGTH_SHORT).show();
        }else {
            if (CustomerRows.size() < perpagecount){

                OrderPageCount.setText("1"+"-"+CustomerRows.size()+"/"+CustomerRows.size());

                for (int i = 0 ; i < CustomerRows.size() ; i++){

                    Orders_parent_layout.addView(AddOrdersOnPArent(CustomerRows.get(i)));

                }

            }else {

                hs_page.clear();
                hs_orderpage.clear();

                pageCount = 0 ;

                totalPage = CustomerRows.size() / perpagecount ;
                remainingcount = CustomerRows.size() % perpagecount ;

                for (int i = 1 ; i <= totalPage ; i++){
                    hs_page.add(""+i);
                    hs_orderpage.add(String.valueOf(i*perpagecount));
                }
                if (remainingcount == 0){
                    // Toast.makeText(OrdersActivity.this, "No remain", Toast.LENGTH_SHORT).show();
                }else {
                    hs_page.add(String.valueOf(totalPage+1));
                    hs_orderpage.add(String.valueOf(remainingcount));
                }
                int siz = Integer.parseInt(hs_orderpage.get(pageCount));

                OrderPageCount.setText("1"+"-"+siz+"/"+CustomerRows.size());

                for (int i = 0 ; i < siz ; i++){

                    Orders_parent_layout.addView(AddOrdersOnPArent(CustomerRows.get(i)));

                }

            }
        }







    }




    private void init() {
        ll_toolbar = (LinearLayout)findViewById(R.id.ll_toolbar);
        ll_refresh = (LinearLayout)findViewById(R.id.refresh_customers);
        Orders_parent_layout = (LinearLayout)findViewById(R.id.ll_orders_add_parent);
        nxt = (TextView)findViewById(R.id.txt_next);
        previous = (TextView)findViewById(R.id.txt_previous);
        ed_srch_order = (EditText) findViewById(R.id.ed_search_order);
        last_update = (TextView) findViewById(R.id.last_update_txt);
        find = (TextView) findViewById(R.id.txt_find);
        dbManager = new CustomersDBManager(CustomersActivity.this);
        OrderPageCount = (TextView)findViewById(R.id.ordr_count_page);
        sm = new SessionManager(CustomersActivity.this);
        changeLogDBManager = new ChangeLogDBManager(CustomersActivity.this);
        SearchlistView = (ListView)findViewById(R.id.searched_list_view);
        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.progress_view, null);
        progress_dialog = new Dialog(CustomersActivity.this);
        progress_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress_dialog.setCancelable(false);
        progress_dialog.setContentView(v);
        Utils.clearParentsBackgrounds(v);

//        RealmResults<CustomersTable> AllCustomerCodes22 = dbManager.getAllrows();
//        for (int i = 0 ; i < AllCustomerCodes22.size() ; i++){
//           AllCustomerCodes.add(AllCustomerCodes22.get(i).getCustomerCode());
//        }
//
//        SearchAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, data);
//        SearchlistView.setAdapter(SearchAdapter);
//        SearchlistView.setTextFilterEnabled(true);
    }


    public View AddOrdersOnPArent(final CustomersTable customersTable){

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        View vv = inflater.inflate(R.layout.layout_customers_include_two, null);

        TextView customer_code = (TextView)vv.findViewById(R.id.customer_code);
        TextView customer_name = (TextView)vv.findViewById(R.id.name);
        TextView customer_contact = (TextView)vv.findViewById(R.id.customer_phone);
        TextView customer_pricelevel = (TextView)vv.findViewById(R.id.price_level);
        TextView customer_country = (TextView)vv.findViewById(R.id.country);
        TextView customer_email = (TextView)vv.findViewById(R.id.customer_email);

        customer_code.setText(""+customersTable.getCustomerCode());
        customer_name.setText(""+customersTable.getCustomerName());
        customer_contact.setText(""+customersTable.getCustomerPhone());
        Log.e("","");
        if (customersTable.getCustomerDefaultPricingLevel().equals("")){
            customer_pricelevel.setText(""+customersTable.getCustomerDefaultPrice());

        }else {
            customer_pricelevel.setText(""+customersTable.getCustomerDefaultPrice()+" ("+customersTable.getCustomerDefaultPricingLevel()+")");

        }
        customer_country.setText(""+customersTable.getCustomerCountry());
        customer_email.setText(""+customersTable.getCustomerEmail());
        vv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CustomersActivity.this , CustomerDetailsActivity.class);
                i.putExtra("CustCode",customersTable.getCustomerCode());
                startActivity(i);
            }
        });


        return vv ;


    }


    public class CustomersRequestReceiver extends BroadcastReceiver {

        public static final String PROCESS_RESPONSE = "com.purplecommerce.interprise.newcustomer";

        @Override
        public void onReceive(Context context, Intent intent) {
            String responseString = intent.getStringExtra("Response");

            progress_dialog.dismiss();

            final String lastUpdate = new ChangeLogDBManager(CustomersActivity.this).GetChangeLogAccordingName(Utils.CustomerChangeLog).getChangeLogLastUpdate();
            StringTokenizer st = new StringTokenizer(lastUpdate , "T");
            last_update.setText(st.nextToken()+"\n"+st.nextToken());


            if (responseString.equals(CustomersService.ADDRESPONSE)){

                PupulateListFromDbOnFirstPage();


            }else if (responseString.equals(CustomersService.UPDATERESPONSE)){

                Toast.makeText(context, "Already Updated !!", Toast.LENGTH_SHORT).show();

            }else if (responseString.equals(CustomersService.NONEWCUSTOMER)){
                Toast.makeText(context, "NO NEW CUSTOMER FOUND !!", Toast.LENGTH_SHORT).show();
            }

            else {

             //   Toast.makeText(context, "Please Try Again !!", Toast.LENGTH_SHORT).show();

            }



        }


    }


    @Override
    protected void onPause() {
        super.onPause();

       try {
           unregisterReceiver(receiver);
       }catch (Exception e){
           Log.e("*Broadcast Exception","*"+e);
       }

    }

    private View Toolbartxts(String txt){

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        View vv = inflater.inflate(R.layout.tool_bar_txt_view, null);
        final TextView toolbartxt = (TextView)vv.findViewById(R.id.toolbar_txt);
        toolbartxt.setText(txt);

        vv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toolbartxt.getText().toString().equals("HOME > ")){
                    finish();
                    Intent i = new Intent(CustomersActivity.this , MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }else if (toolbartxt.getText().toString().equals("CUSTOMERS")){
                    finish();
                }else {

                }
            }
        });


        return vv ;

    }

}
