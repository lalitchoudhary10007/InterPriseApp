package com.purplecommerce.interpriseapp.InventoryItems;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.AnalyticsListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.purplecommerce.interpriseapp.CustomersActivity;
import com.purplecommerce.interpriseapp.Database.ChangeLogDBManager;
import com.purplecommerce.interpriseapp.Database.CustomersDBManager;
import com.purplecommerce.interpriseapp.Database.CustomersTable;
import com.purplecommerce.interpriseapp.Database.ItemsInventoryDBManager;
import com.purplecommerce.interpriseapp.Database.ItemsInventoryTable;
import com.purplecommerce.interpriseapp.InvoicesActivity;
import com.purplecommerce.interpriseapp.MainActivity;
import com.purplecommerce.interpriseapp.R;
import com.purplecommerce.interpriseapp.Services.CustomersService;
import com.purplecommerce.interpriseapp.Services.ItemsInventoryService;
import com.purplecommerce.interpriseapp.SessionManager.SessionManager;
import com.purplecommerce.interpriseapp.SetterGetters.CustomerInvoicesResponse;
import com.purplecommerce.interpriseapp.Utils.Utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimeZone;

import io.realm.RealmResults;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class InventoryItemsActivity extends AppCompatActivity {

    LinearLayout Items_parent_layout , ll_toolbar , ll_refresh;
    TextView nxt , previous , find , last_update ;
    int pageCount = 0 , totalPage = 1  , perpagecount = 5 , remainingcount = 0  ;
    EditText ed_srch_items ;

    final String TAG = "ITEMS-INVENTORY";

    Dialog progress_dialog ;
    TextView  ItemsPageCount ;

    RealmResults<ItemsInventoryTable> ItemsInventoryRows ;
    public static String prevoiusOne = "test" ;
    ArrayList<String> hs_page = new ArrayList<>();
    ArrayList<String> hs_orderpage = new ArrayList<>();
    ChangeLogDBManager changeLogDBManager ;
    ItemsInventoryDBManager itemsDBManager ;
    private ItemsRequestReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_items);



        init();

        ll_toolbar.addView(Toolbartxts("HOME > "));
        ll_toolbar.addView(Toolbartxts("ITEMS"));


//        if (itemsDBManager.ItemsInventoryIsEmpty()){
//
//            itemsDBManager.SaveNewItems("ITEM-000615" , "PRFE001-000-000" , "Stock" , "A" , "" , "Federal" ,                      "2017-06-29T13:33:14.08") ;
//
//        }


        ////////////////////register broadcast receiver

        IntentFilter filter = new IntentFilter(ItemsInventoryService.BROADCASTACTIONITEMS);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new ItemsRequestReceiver();
        registerReceiver(receiver, filter);

        /////////////////////


        PupulateListFromDbOnFirstPage();


        /////////////////Start Service


        String lastUpdate = "";


        if (changeLogDBManager.GetChangeLogAccordingName((Utils.ItemsChangeLog))==null){
            Toast.makeText(this, "No Change Logs", Toast.LENGTH_SHORT).show();
            last_update.setText("Not Yet");
        }else {
            lastUpdate = changeLogDBManager.GetChangeLogAccordingName(Utils.ItemsChangeLog).getChangeLogLastUpdate();
            StringTokenizer st = new StringTokenizer(lastUpdate , "T");
            last_update.setText(st.nextToken()+"\n"+st.nextToken());
        }

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
        final String finalLastUpdate = lastUpdate;
        ll_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //http://seventies.apexhelp.co.uk:82/Interprise.Web.Services/changelog/Customer?from=2017-07-07&to=2017-11-14


                String PageSize = "2000";
                String PageNumber = "1";

                progress_dialog.show();
                Log.e("**to time",""+ finalTodatetime);

                Intent serviceIntent = new Intent(InventoryItemsActivity.this, ItemsInventoryService.class);
                serviceIntent.putExtra("URL","/changelog/InventoryItem?from="+ finalLastUpdate +"&to="+ finalTodatetime                         +"&page[number]=1&page[size]="+PageSize);
                serviceIntent.putExtra("TODATETIME" , finalTodatetime);
                startService(serviceIntent);
            }
        });



////////////////









        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ed_srch_items.getText().toString().isEmpty()){
                    Toast.makeText(InventoryItemsActivity.this, "Please Enter Customer Code !!", Toast.LENGTH_SHORT).show();
                }else {

                    RealmResults<ItemsInventoryTable> cust = itemsDBManager.SearchItems("ITEM-"+ed_srch_items.getText().toString().trim());
                    Log.e("**customer","customer"+cust);
                    if (cust==null){
                        Toast.makeText(InventoryItemsActivity.this, "Customer Code Not Exist !!", Toast.LENGTH_SHORT).show();
                    }else {

                        Utils.hideKeyboard(InventoryItemsActivity.this);
                        Items_parent_layout.removeAllViews();

                        for (int i = 0 ; i < cust.size() ; i++){
                            Items_parent_layout.addView(AddItemsOnPArent(cust.get(i)));
                        }

                    }


                }
            }
        });


        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!hs_orderpage.isEmpty()){

                    prevoiusOne = "2" ;

                    if (pageCount+1 == hs_page.size()) {
                        Toast.makeText(InventoryItemsActivity.this, "Page Finished !!", Toast.LENGTH_SHORT).show();
                    }else {

                        pageCount = pageCount + 1;

                        if (pageCount < hs_page.size()) {

                            Items_parent_layout.removeAllViews();

                            int max = Integer.parseInt(hs_orderpage.get(pageCount));
                            int min = Integer.parseInt(hs_orderpage.get(pageCount - 1));

                            Log.e("**max", "" + max);
                            Log.e("**min", "" + min);

                            if (max < perpagecount) {

                                int max2 = min + max;
                                ItemsPageCount.setText(min + "-" + max2 + "/" + ItemsInventoryRows.size());
                                Log.e("max2", "for last" + max2);

                                for (int i = min; i < max2; i++) {

                                    Items_parent_layout.addView(AddItemsOnPArent(ItemsInventoryRows.get(i)));

                                }

                            } else {

                                ItemsPageCount.setText(min + "-" + max + "/" + ItemsInventoryRows.size());
                                for (int i = min; i < max; i++) {

                                    Items_parent_layout.addView(AddItemsOnPArent(ItemsInventoryRows.get(i)));

                                }

                            }


                        } else {
                            //  Toast.makeText(paginationlogic.this, "That' it", Toast.LENGTH_SHORT).show();

                        }
                    }
                    }else{
                            Toast.makeText(InventoryItemsActivity.this, "No Pages !!", Toast.LENGTH_SHORT).show();
                        }

            }
        });


        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!hs_orderpage.isEmpty()) {

                    if (prevoiusOne.equals("1")){

                        Toast.makeText(InventoryItemsActivity.this, "Page Finished !!", Toast.LENGTH_SHORT).show();

                    }else {

                        pageCount = pageCount - 1 ;
                        if (pageCount == 0){

                            prevoiusOne = "1" ;

                            Items_parent_layout.removeAllViews();

                            Log.e("order page",""+hs_orderpage);
                            Log.e(" page",""+hs_page);
                            int max = Integer.parseInt(hs_orderpage.get(pageCount));
                            ItemsPageCount.setText("1"+"-"+max+"/"+ItemsInventoryRows.size());
                            for (int i= 0 ; i < max ; i++){

                                Items_parent_layout.addView(AddItemsOnPArent(ItemsInventoryRows.get(i)));

                            }


                        }else if (pageCount < hs_page.size()){

                            prevoiusOne = "2" ;

                            Items_parent_layout.removeAllViews();

                            Log.e("order page",""+hs_orderpage);
                            Log.e(" page",""+hs_page);
                            int max = Integer.parseInt(hs_orderpage.get(pageCount));
                            int min = Integer.parseInt(hs_orderpage.get(pageCount-1));

                            Log.e("**max",""+max);
                            Log.e("**min",""+min);

                            if (max < perpagecount){

                                int max2 = min + max ;
                                ItemsPageCount.setText(min+"-"+max2+"/"+ItemsInventoryRows.size());
                                Log.e("max2","for last"+max2);
                                for (int i = min ; i < max2 ; i++){

                                    Items_parent_layout.addView(AddItemsOnPArent(ItemsInventoryRows.get(i)));
                                }



                            }else {

                                Items_parent_layout.removeAllViews();

                                ItemsPageCount.setText(min+"-"+max+"/"+ItemsInventoryRows.size());
                                for (int i = min ; i < max ; i++){
                                    Items_parent_layout.addView(AddItemsOnPArent(ItemsInventoryRows.get(i)));
                                }

                            }


                        }
                        else {
                            //  Toast.makeText(paginationlogic.this, "That' it", Toast.LENGTH_SHORT).show();
                        }

                    }

                }else {
                    Toast.makeText(InventoryItemsActivity.this, "No Pages !!", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }










    private View AddItemsOnPArent(ItemsInventoryTable attributesBean) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        View vv = inflater.inflate(R.layout.items_add_layout, null);

        ImageView item_image = (ImageView) vv.findViewById(R.id.img_item);
        TextView second = (TextView)vv.findViewById(R.id.txt_item);
       // TextView third = (TextView)vv.findViewById(R.id.third_txt);

       StringTokenizer tokenizer = new StringTokenizer(attributesBean.getItemlastUpdate(),"T");

       second.setText(attributesBean.getItemName()+"\n"+attributesBean.getItemCode()+"("+attributesBean.getItemType()+")            "+"\n" +"By "+attributesBean.getItemManufactureCode()+
                "\n"+"Last Updated On "+tokenizer.nextToken());

        Bitmap bmp = BitmapFactory.decodeByteArray(attributesBean.getItemPhoto(), 0, attributesBean.getItemPhoto().length);

        item_image.setImageBitmap(bmp);

        return vv ;

    }

    public void PupulateListFromDbOnFirstPage(){

        Items_parent_layout.removeAllViews();

        ItemsInventoryRows =  itemsDBManager.getAllItems();

        if (ItemsInventoryRows.size() < perpagecount){

            ItemsPageCount.setText("1"+"-"+ItemsInventoryRows.size()+"/"+ItemsInventoryRows.size());

            for (int i = 0 ; i < ItemsInventoryRows.size() ; i++){

                Items_parent_layout.addView(AddItemsOnPArent(ItemsInventoryRows.get(i)));

            }

        }else {

            hs_page.clear();
            hs_orderpage.clear();

            pageCount = 0 ;

            totalPage = ItemsInventoryRows.size() / perpagecount ;
            remainingcount = ItemsInventoryRows.size() % perpagecount ;

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

            ItemsPageCount.setText("1"+"-"+siz+"/"+ItemsInventoryRows.size());

            for (int i = 0 ; i < siz ; i++){

                Items_parent_layout.addView(AddItemsOnPArent(ItemsInventoryRows.get(i)));

            }

        }

    }



    public class ItemsRequestReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            String responseString = intent.getStringExtra("Response");

            progress_dialog.dismiss();

            String lastUpdate = new ChangeLogDBManager(InventoryItemsActivity.this).GetChangeLogAccordingName(Utils.ItemsChangeLog).getChangeLogLastUpdate();
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


    private void init() {
        ll_toolbar = (LinearLayout)findViewById(R.id.ll_toolbar);
        ll_refresh = (LinearLayout)findViewById(R.id.refresh_customers);
        Items_parent_layout = (LinearLayout)findViewById(R.id.ll_orders_add_parent);
        nxt = (TextView)findViewById(R.id.txt_next);
        previous = (TextView)findViewById(R.id.txt_previous);
        ed_srch_items = (EditText) findViewById(R.id.ed_search_order);
        last_update = (TextView) findViewById(R.id.last_update_txt);
        find = (TextView) findViewById(R.id.txt_find);
        itemsDBManager = new ItemsInventoryDBManager(InventoryItemsActivity.this);
        ItemsPageCount = (TextView)findViewById(R.id.ordr_count_page);
        //sm = new SessionManager(CustomersActivity.this);
        changeLogDBManager = new ChangeLogDBManager(InventoryItemsActivity.this);

        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.progress_view, null);
        progress_dialog = new Dialog(InventoryItemsActivity.this);
        progress_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress_dialog.setCancelable(false);
        progress_dialog.setContentView(v);
        Utils.clearParentsBackgrounds(v);


        if (changeLogDBManager.GetChangeLogAccordingName(Utils.ItemsChangeLog)==null){
            Toast.makeText(this, "No Change Logs", Toast.LENGTH_SHORT).show();
            last_update.setText("Not Yet");
        }else {
           String lastUpdate = changeLogDBManager.GetChangeLogAccordingName(Utils.ItemsChangeLog).getChangeLogLastUpdate();
            StringTokenizer st = new StringTokenizer(lastUpdate , "T");
            last_update.setText(st.nextToken()+"\n"+st.nextToken());
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
                if (toolbartxt.getText().toString().equals("HOME >")){
                    finish();
                    Intent i = new Intent(InventoryItemsActivity.this , MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            }
        });


        return vv ;

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



}