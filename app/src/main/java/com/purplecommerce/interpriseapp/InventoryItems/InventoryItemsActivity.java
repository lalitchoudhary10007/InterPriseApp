package com.purplecommerce.interpriseapp.InventoryItems;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.purplecommerce.interpriseapp.Database.ChangeLogDBManager;
import com.purplecommerce.interpriseapp.Database.ItemsInventoryDBManager;
import com.purplecommerce.interpriseapp.Database.ItemsInventoryTable;
import com.purplecommerce.interpriseapp.R;
import com.purplecommerce.interpriseapp.Services.ItemsInventoryService;
import com.purplecommerce.interpriseapp.Utils.Utils;

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

import static com.bumptech.glide.Glide.with;

public class InventoryItemsActivity extends AppCompatActivity {

    LinearLayout Items_parent_layout , ll_toolbar , ll_refresh;
    TextView nxt , previous , find , last_update , scan ;
    int pageCount = 0 , totalPage = 1  , perpagecount = Utils.PerPageCount , remainingcount = 0  ;
    EditText ed_srch_items ;

    final String TAG = "ITEMS-INVENTORY";

    Dialog progress_dialog ;
    TextView  ItemsPageCount ;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 0;
    RealmResults<ItemsInventoryTable> ItemsInventoryRows ;
    public static String prevoiusOne = "test" ;
    ArrayList<String> hs_page = new ArrayList<>();
    ArrayList<String> hs_orderpage = new ArrayList<>();
    ChangeLogDBManager changeLogDBManager ;
    ItemsInventoryDBManager itemsDBManager ;
    private ItemsRequestReceiver receiver;
    String lastUpdate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_items);



        init();

        ll_toolbar.addView(Toolbartxts("HOME > "));
        ll_toolbar.addView(Toolbartxts("ITEMS"));


//        if (itemsDBManager.ItemsInventoryIsEmpty()){
//
//            itemsDBManager.SaveNewItems("ITEM-000615" , "PRFE001-000-000" , "Stock" , "A" , "" , "Federal" ,"2017-06-29T13:33:14.08") ;
//
//        }


        ////////////////////register broadcast receiver



        /////////////////////


        PupulateListFromDbOnFirstPage();


        /////////////////Start Service




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

                if (changeLogDBManager.GetChangeLogAccordingName((Utils.ItemsChangeLog))==null){
                    last_update.setText("Updating...");
                    lastUpdate = "2016-06-11T11:55:37" ;
                }else {
                    lastUpdate = changeLogDBManager.GetChangeLogAccordingName(Utils.ItemsChangeLog).getChangeLogLastUpdate();
                    StringTokenizer st = new StringTokenizer(lastUpdate , "T");
                    last_update.setText(st.nextToken()+"\n"+st.nextToken());
                }


                String PageSize = "2000";
                String PageNumber = "1";

                progress_dialog.show();
                Log.e("**to time",""+ finalTodatetime);

                Intent serviceIntent = new Intent(InventoryItemsActivity.this, ItemsInventoryService.class);
                serviceIntent.putExtra("URL","/changelog/InventoryItem?from="+ lastUpdate +"&to="+ finalTodatetime                         +"&page[number]=1&page[size]="+PageSize);
                serviceIntent.putExtra("TODATETIME" , finalTodatetime);
                startService(serviceIntent);

            }
        });



////////////////


        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT < 23) {
                    //Do not need to check the permission
                   // new IntentIntegrator((Activity) InventoryItemsActivity.this).initiateScan(item_batches);
                } else {
                    if (checkAndRequestPermissions()) {
                        //If you have already permitted the permission
                      //  new IntentIntegrator((Activity) InventoryItemsActivity.this).initiateScan(item_batches);
                    } else {

                    }

                }
            }
        });




        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ed_srch_items.getText().toString().isEmpty()){
                    Toast.makeText(InventoryItemsActivity.this, "Please Enter Customer Code !!", Toast.LENGTH_SHORT).show();
                }else {

                    RealmResults<ItemsInventoryTable> cust = itemsDBManager.SearchItems(ed_srch_items.getText().toString().trim());
                    Log.e("**customer","customer"+cust);
                    if (cust==null){
                     //   Toast.makeText(InventoryItemsActivity.this, "Customer Code Not Exist !!", Toast.LENGTH_SHORT).show();
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


                nextClick(Items_parent_layout , ItemsInventoryRows.size() , perpagecount , hs_page , hs_orderpage , ItemsPageCount ,
                        ItemsInventoryRows );

            }
        });


        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

          PreviousClick(Items_parent_layout , ItemsInventoryRows.size() , perpagecount , hs_page , hs_orderpage , ItemsPageCount ,
                  ItemsInventoryRows );

            }
        });


    }



    private View AddItemsOnPArent(final ItemsInventoryTable attributesBean) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        View vv = inflater.inflate(R.layout.items_add_layout, null);

        ImageView item_image = (ImageView) vv.findViewById(R.id.img_item);
        TextView second = (TextView)vv.findViewById(R.id.txt_item);
        // TextView third = (TextView)vv.findViewById(R.id.third_txt);

       final StringTokenizer tokenizer = new StringTokenizer(attributesBean.getItemlastUpdate(),"T");


        if (attributesBean.getItemManufactureCode().isEmpty()){
            second.setText(attributesBean.getItemName()+"\n"+attributesBean.getItemCode()+"("+attributesBean.getItemType()+")"+"\n"
                    +"Last Updated On "+tokenizer.nextToken());
        }else {
            second.setText(attributesBean.getItemName()+"\n"+attributesBean.getItemCode()+"("+attributesBean.getItemType()+")"+"\n" +"By "+attributesBean.getItemManufactureCode()+
                    "\n"+"Last Updated On "+tokenizer.nextToken());
        }



        Log.e("**image is ","database"+attributesBean.getItemPhoto());

        Bitmap bmp = BitmapFactory.decodeByteArray(attributesBean.getItemPhoto(), 0, attributesBean.getItemPhoto().length);

        Glide.with(InventoryItemsActivity.this)
                .fromBytes()
                .load(attributesBean.getItemPhoto())
                .placeholder(R.drawable.png_loading)
                .error(R.drawable.no_image)
                .into(item_image);

        //item_image.setImageBitmap(bmp);

        vv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(InventoryItemsActivity.this , ItemDetailsActivity.class)
                        .putExtra("ITEMCODE" , attributesBean.getItemCode())
                        .putExtra("ITEMNAME"  , attributesBean.getItemName())
                        .putExtra("ITEMTYPE" , attributesBean.getItemType())
                        .putExtra("MANUFACTURECODE" , attributesBean.getItemManufactureCode())
                        .putExtra("IMAGEBYTES" , attributesBean.getItemPhoto())
                        .putExtra("LastUpdate" , attributesBean.getItemlastUpdate()));
            }
        });

        return vv ;

    }

    public void PupulateListFromDbOnFirstPage(){

        Items_parent_layout.removeAllViews();

        ItemsInventoryRows =  itemsDBManager.getAllItems();


        Log.e("items size ","in db"+ItemsInventoryRows.size());

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

            Log.e("in receiver","**"+responseString);

            lastUpdate = new ChangeLogDBManager(InventoryItemsActivity.this).GetChangeLogAccordingName(Utils.ItemsChangeLog).getChangeLogLastUpdate();
            StringTokenizer st = new StringTokenizer(lastUpdate , "T");
            last_update.setText(st.nextToken()+"\n"+st.nextToken());


            if (responseString.equals(ItemsInventoryService.ADDRESPONSE)){

                PupulateListFromDbOnFirstPage();


            }else if (responseString.equals(ItemsInventoryService.UPDATERESPONSE)){

                Toast.makeText(context, "Already Updated !!", Toast.LENGTH_SHORT).show();

            }else if (responseString.equals(ItemsInventoryService.NONEWCUSTOMER)){
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
        scan = (TextView)findViewById(R.id.txt_scan);
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
                if (toolbartxt.getText().toString().equals("HOME > ")){
                    finish();

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


    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(ItemsInventoryService.BROADCASTACTIONITEMS);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new ItemsRequestReceiver();
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                  Toast.makeText(this, "Scan Cancelled !!", Toast.LENGTH_LONG).show();

            } else {
                Log.e("content idd",""+result.getContents());

            ItemsInventoryTable inventoryTable =   itemsDBManager.GetItemAccordingItemCode(""+result.getContents());

                if (inventoryTable==null){
                    Toast.makeText(this, "Item Code Does Not Exist !!", Toast.LENGTH_SHORT).show();
                }else {
                    startActivity(new Intent(InventoryItemsActivity.this , ItemDetailsActivity.class)
                            .putExtra("ITEMCODE" , inventoryTable.getItemCode())
                            .putExtra("ITEMNAME"  , inventoryTable.getItemName())
                            .putExtra("ITEMTYPE" , inventoryTable.getItemType())
                            .putExtra("MANUFACTURECODE" , inventoryTable.getItemManufactureCode())
                            .putExtra("IMAGEBYTES" , inventoryTable.getItemPhoto())
                            .putExtra("LastUpdate" , inventoryTable.getItemlastUpdate()));
                }



            }

        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



    private boolean checkAndRequestPermissions() {
        int permissionCAMERA = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);


//        int storagePermission = ContextCompat.checkSelfPermission(this,
//
//
//                Manifest.permission.READ_EXTERNAL_STORAGE);
//
//        int smsPermission = ContextCompat.checkSelfPermission(this,
//
//
//                Manifest.permission.READ_SMS);
//
//        int callPermission = ContextCompat.checkSelfPermission(this,
//
//
//                Manifest.permission.CALL_PHONE);

        List<String> listPermissionsNeeded = new ArrayList<>();
//        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
//        }
        if (permissionCAMERA != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
//        if (smsPermission != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
//        }
//        if (callPermission != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
//        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,

                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MY_PERMISSIONS_REQUEST_CAMERA);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                  //  new IntentIntegrator((Activity) InventoryItemsActivity.this).initiateScan(item_batches);
                    //Permission Granted Successfully. Write working code here.
                } else {

                    //You did not accept the request can not use the functionality.
                }
                break;
        }

    }



    public void nextClick(LinearLayout ll_parent1 , int totaldata1 , int perpagecount1 ,
                          ArrayList<String> hs_page1 , ArrayList<String> hs_orderpage1 , TextView pagetx1 , RealmResults<ItemsInventoryTable> data1){


        if (!hs_orderpage1.isEmpty()) {


            prevoiusOne = "2";

            if (pageCount + 1 == hs_page1.size()) {
                Toast.makeText(InventoryItemsActivity.this, "Page Finished !!", Toast.LENGTH_SHORT).show();
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

                            ll_parent1.addView(AddItemsOnPArent(data1.get(i)));

                        }

                    } else {

                        pagetx1.setText(min + "-" + max + "/" + totaldata1);
                        for (int i = min; i < max; i++) {

                            ll_parent1.addView(AddItemsOnPArent(data1.get(i)));

                        }

                    }


                } else {
                    //  Toast.makeText(paginationlogic.this, "That' it", Toast.LENGTH_SHORT).show();
                }

            }
        }else {
            Toast.makeText(InventoryItemsActivity.this, "No Pages !!", Toast.LENGTH_SHORT).show();
        }

    }


    public void PreviousClick(LinearLayout ll_parent1 , int totaldata1 , int perpagecount1 ,
                              ArrayList<String> hs_page1 , ArrayList<String> hs_orderpage1 , TextView pagetx1 , RealmResults<ItemsInventoryTable> data1){

        if (!hs_orderpage1.isEmpty()) {

            if (prevoiusOne.equals("1")) {

                Toast.makeText(InventoryItemsActivity.this, "Page Finished !!", Toast.LENGTH_SHORT).show();

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

                        ll_parent1.addView(AddItemsOnPArent(data1.get(i)));

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

                            ll_parent1.addView(AddItemsOnPArent(data1.get(i)));
                        }


                    } else {

                        ll_parent1.removeAllViews();

                        pagetx1.setText(min + "-" + max + "/" + totaldata1);
                        for (int i = min; i < max; i++) {
                            ll_parent1.addView(AddItemsOnPArent(data1.get(i)));
                        }

                    }


                } else {
                    //  Toast.makeText(paginationlogic.this, "That' it", Toast.LENGTH_SHORT).show();
                }

            }

        }else {
            Toast.makeText(InventoryItemsActivity.this, "No Pages !!", Toast.LENGTH_SHORT).show();
        }


    }






}
