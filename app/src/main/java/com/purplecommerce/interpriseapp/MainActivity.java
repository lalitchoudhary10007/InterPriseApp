package com.purplecommerce.interpriseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.purplecommerce.interpriseapp.Database.CustomersDBManager;
import com.purplecommerce.interpriseapp.Database.InvoicesDBManager;
import com.purplecommerce.interpriseapp.Database.ItemsInventoryDBManager;
import com.purplecommerce.interpriseapp.Database.SalesOrdersDBManager;
import com.purplecommerce.interpriseapp.InventoryItems.InventoryItemsActivity;
import com.purplecommerce.interpriseapp.Services.MyBackgroundService;
import com.purplecommerce.interpriseapp.SessionManager.SessionManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    FrameLayout fm_orders , fm_customers , fm_items , fm_invoices ;
    SessionManager sm ;
    TextView CustomersCount  , ItemsCount , orders_count , invoice_count;
    CustomersDBManager dbManager ;
    ItemsInventoryDBManager inventoryDBManager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




      //  startService(new Intent(this, MyBackgroundService.class));


        Init();

        fm_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , OrdersFromDBActivity.class));
            }
        });

        fm_customers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , CustomersActivity.class));
            }
        });

        fm_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , InventoryItemsActivity.class));
            }
        });

        fm_invoices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , InvoicesFromDBActivity.class));
            }
        });



    }

    private void Init() {

        fm_orders = (FrameLayout)findViewById(R.id.frame_orders);
        fm_customers = (FrameLayout)findViewById(R.id.frame_customers) ;
        fm_items = (FrameLayout)findViewById(R.id.fm_items);
        fm_invoices = (FrameLayout)findViewById(R.id.frame_invoices) ;
        sm = new SessionManager(MainActivity.this);
        dbManager = new CustomersDBManager(MainActivity.this);
        inventoryDBManager = new ItemsInventoryDBManager(MainActivity.this);
        Log.e("**Key"+sm.getUrlDetails().get(SessionManager.KEY),"url"+sm.getUrlDetails().get(SessionManager.URL));
        CustomersCount = (TextView)findViewById(R.id.txt_customer_count);
        ItemsCount = (TextView)findViewById(R.id.items_count);
        orders_count = (TextView)findViewById(R.id.orders_count);
        invoice_count = (TextView)findViewById(R.id.txt_invoices);

    }


    @Override
    protected void onResume() {
        super.onResume();
        CustomersCount.setText(String.valueOf(dbManager.getCustomersCount()));
        ItemsCount.setText(String.valueOf(inventoryDBManager.GetItemsIventoryCount()));
        orders_count.setText(String.valueOf(new SalesOrdersDBManager(MainActivity.this).GetSalesOrderCount()));
        invoice_count.setText(String.valueOf(new InvoicesDBManager(MainActivity.this).GetInvoicesCount()));
    }
}
