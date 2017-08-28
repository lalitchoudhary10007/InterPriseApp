package com.purplecommerce.interpriseapp;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    int pageCount = 0 , totalPage = 1  , perpagecount = 5 , remainingcount = 0  ;
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

        String lastUpdate = "";

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        final String date = df.format(Calendar.getInstance().getTime());
        StringTokenizer tokenizer = new StringTokenizer(date , "T");
        lastUpdate = date ;
        if (changeLogDBManager.GetChangeLogAccordingName((Utils.CustomerChangeLog))==null){
            Toast.makeText(this, "No Change Logs", Toast.LENGTH_SHORT).show();
            last_update.setText("Not Yet");
        }else {
            lastUpdate = changeLogDBManager.GetChangeLogAccordingName(Utils.CustomerChangeLog).getChangeLogLastUpdate();
            StringTokenizer st = new StringTokenizer(lastUpdate , "T");
            last_update.setText(st.nextToken()+"\n"+st.nextToken());
        }


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
        final String finalLastUpdate = lastUpdate;
        ll_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //http://seventies.apexhelp.co.uk:82/Interprise.Web.Services/changelog/Customer?from=2017-07-07&to=2017-11-14


                 String PageSize = "2000";
                 String PageNumber = "1";

                progress_dialog.show();
                Log.e("**to time",""+ finalTodatetime);

                Intent serviceIntent = new Intent(CustomersActivity.this, CustomersService.class);
                serviceIntent.putExtra("URL","/changelog/Customer?from="+ finalLastUpdate +"&to="+ finalTodatetime +"&page[number]=1&page[size]="+PageSize);
                serviceIntent.putExtra("TODATETIME" , finalTodatetime);
                startService(serviceIntent);
            }
        });




        if (dbManager.CustomerTableisEmpty()){

            dbManager.SaveCustomers("CUST-001003" , "John Greenwood" , "01708 474429" ,"info@seventies.co.uk" , "Wholesale" ,"TRAD01" , "SHIP-001232" , "United Kingdom" , "CCTC-001294");

            dbManager.SaveCustomers("CUST-001004" , "Curlys Shack" , "0755 242 6490" ,"curlysshack@gmail.com" , "Wholesale" ,"TRAD01" , "SHIP-001232" , "United Kingdom" , "CCTC-001294");

            dbManager.SaveCustomers("CUST-001005" , "Rasmus Paimre" , "+372 5559 3835" ,"info@seventies.co.uk" , "Wholesale" ,"TRAD01" , "SHIP-001232" , "United Kingdom" , "CCTC-001294");

        }



        PupulateListFromDbOnFirstPage();



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

                prevoiusOne = "2" ;

                if (pageCount+1 == hs_page.size()) {
                    Toast.makeText(CustomersActivity.this, "Page Finished !!", Toast.LENGTH_SHORT).show();
                }else {

                    pageCount = pageCount + 1;

                    if (pageCount < hs_page.size()) {

                        Orders_parent_layout.removeAllViews();

                        int max = Integer.parseInt(hs_orderpage.get(pageCount));
                        int min = Integer.parseInt(hs_orderpage.get(pageCount - 1));

                        Log.e("**max", "" + max);
                        Log.e("**min", "" + min);

                        if (max < perpagecount) {

                            int max2 = min + max;
                            OrderPageCount.setText(min + "-" + max2 + "/" + CustomerRows.size());
                            Log.e("max2", "for last" + max2);

                            for (int i = min; i < max2; i++) {

                                Orders_parent_layout.addView(AddOrdersOnPArent(CustomerRows.get(i)));

                            }

                        } else {

                            OrderPageCount.setText(min + "-" + max + "/" + CustomerRows.size());
                            for (int i = min; i < max; i++) {

                                Orders_parent_layout.addView(AddOrdersOnPArent(CustomerRows.get(i)));

                            }

                        }


                    } else {
                        //  Toast.makeText(paginationlogic.this, "That' it", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });


        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (prevoiusOne.equals("1")){

                    Toast.makeText(CustomersActivity.this, "Page Finished !!", Toast.LENGTH_SHORT).show();

                }else {

                    pageCount = pageCount - 1 ;
                    if (pageCount == 0){

                        prevoiusOne = "1" ;

                        Orders_parent_layout.removeAllViews();

                        Log.e("order page",""+hs_orderpage);
                        Log.e(" page",""+hs_page);
                        int max = Integer.parseInt(hs_orderpage.get(pageCount));
                        OrderPageCount.setText("1"+"-"+max+"/"+CustomerRows.size());
                        for (int i= 0 ; i < max ; i++){

                            Orders_parent_layout.addView(AddOrdersOnPArent(CustomerRows.get(i)));

                        }


                    }else if (pageCount < hs_page.size()){

                        prevoiusOne = "2" ;

                        Orders_parent_layout.removeAllViews();

                        Log.e("order page",""+hs_orderpage);
                        Log.e(" page",""+hs_page);
                        int max = Integer.parseInt(hs_orderpage.get(pageCount));
                        int min = Integer.parseInt(hs_orderpage.get(pageCount-1));

                        Log.e("**max",""+max);
                        Log.e("**min",""+min);

                        if (max < perpagecount){

                            int max2 = min + max ;
                            OrderPageCount.setText(min+"-"+max2+"/"+CustomerRows.size());
                            Log.e("max2","for last"+max2);
                            for (int i = min ; i < max2 ; i++){

                                Orders_parent_layout.addView(AddOrdersOnPArent(CustomerRows.get(i)));
                            }



                        }else {

                            Orders_parent_layout.removeAllViews();

                            OrderPageCount.setText(min+"-"+max+"/"+CustomerRows.size());
                            for (int i = min ; i < max ; i++){
                                Orders_parent_layout.addView(AddOrdersOnPArent(CustomerRows.get(i)));
                            }

                        }


                    }
                    else {
                        //  Toast.makeText(paginationlogic.this, "That' it", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });






    }




    public void PupulateListFromDbOnFirstPage(){

        Orders_parent_layout.removeAllViews();

        CustomerRows =  dbManager.getAllrows();

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

        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.progress_view, null);
        progress_dialog = new Dialog(CustomersActivity.this);
        progress_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress_dialog.setCancelable(false);
        progress_dialog.setContentView(v);
        Utils.clearParentsBackgrounds(v);


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
        customer_pricelevel.setText(""+customersTable.getCustomerDefaultPrice()+" ("+customersTable.getCustomerDefaultPricingLevel()+")");
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
