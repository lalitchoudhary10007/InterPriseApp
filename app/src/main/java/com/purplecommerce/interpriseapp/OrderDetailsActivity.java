package com.purplecommerce.interpriseapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
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
import com.purplecommerce.interpriseapp.SessionManager.SessionManager;
import com.purplecommerce.interpriseapp.SetterGetters.CustomerSalesOrdersResponse;
import com.purplecommerce.interpriseapp.SetterGetters.ErrorResponse;
import com.purplecommerce.interpriseapp.SetterGetters.OrderProductsResponse;
import com.purplecommerce.interpriseapp.Utils.Utils;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;


public class OrderDetailsActivity extends AppCompatActivity {


    LinearLayout ll_parent_products , ll_toolbar ;
    TextView  Customer_id , Customer_name , ship_to , bill_to , subtotal , freight , total ,
    Invoice ;
    SessionManager sessionManager ;
    OkHttpClient okHttpClient ;
    final String TAG = "CUSTOMER-ORDERS";
    GsonBuilder gsonBuilder;
    Gson gson;
    Dialog progress_dialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);


        init();

        Intent ii = getIntent();
       // toolbartxt.setText("HOME > ORDERS > "+ii.getStringExtra("OrderNo"));

        ll_toolbar.addView(Toolbartxts("HOME >"));
        ll_toolbar.addView(Toolbartxts("ORDERS >"));
        ll_toolbar.addView(Toolbartxts(""+ii.getStringExtra("OrderNo")));

        Customer_id.setText(""+ii.getStringExtra("CustomeId"));
        Customer_name.setText(""+ii.getStringExtra("CustomerName"));
        ship_to.setText(""+ii.getStringExtra("ShipTo"));
        bill_to.setText(""+ii.getStringExtra("BillTo"));
        subtotal.setText( String.format("%.2f" , Double.parseDouble(ii.getStringExtra("SubTotal"))));
        //subtotal.setText(""+ii.getStringExtra("SubTotal"));
        freight.setText( String.format("%.2f" , Double.parseDouble(ii.getStringExtra("Freight"))));
        //freight.setText(""+ii.getStringExtra("Freight"));
        total.setText(String.format("%.2f" , Double.parseDouble(ii.getStringExtra("Total"))));



        //SO-015284
        String url = sessionManager.getUrlDetails().get(SessionManager.URL)+"/salesorder/"+ii.getStringExtra("OrderNo")+"/detail";

        GetOrderProducts(url);


    }

    private void init() {

        ll_parent_products = (LinearLayout)findViewById(R.id.parent_products_layout);
       // toolbartxt = (TextView)findViewById(R.id.toolbar_txt);
        Customer_id = (TextView)findViewById(R.id.txt_customerid);
        Customer_name = (TextView)findViewById(R.id.txt_customername);
        ship_to = (TextView)findViewById(R.id.txt_shipto);
        bill_to = (TextView)findViewById(R.id.txt_billto);
        subtotal = (TextView)findViewById(R.id.txt_subtotal);
        freight = (TextView)findViewById(R.id.txt_freight);
        total  = (TextView)findViewById(R.id.txt_total);
        Invoice = (TextView)findViewById(R.id.txt_invoice);
        ll_toolbar = (LinearLayout)findViewById(R.id.ll_toolbar);




        okHttpClient = new OkHttpClient.Builder().authenticator(new Authenticator() {
            @Override
            public Request authenticate(Route route, Response response) throws IOException {

                String credential = Credentials.basic(sessionManager.getUrlDetails().get(SessionManager.KEY), "");

                return response.request().newBuilder().header("Authorization" , credential).build();
            }
        }).build();

        sessionManager = new SessionManager(OrderDetailsActivity.this);
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.progress_view, null);
        progress_dialog = new Dialog(OrderDetailsActivity.this);
        progress_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress_dialog.setCancelable(false);
        progress_dialog.setContentView(v);
        Utils.clearParentsBackgrounds(v);



    }




    private void GetOrderProducts(String url){

        progress_dialog.show();

        AndroidNetworking.get(url)
                .setTag(this).setPriority(Priority.MEDIUM)
                .setOkHttpClient(okHttpClient)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d(TAG, " bytesSent : " + bytesSent);
                        Log.d(TAG, " bytesReceived : " + bytesReceived);
                        Log.d(TAG, " isFromCache : " + isFromCache);
                    }
                }).getAsString(new StringRequestListener() {


            @Override
            public void onResponse(String response) {
                Log.d("*** RESPONSE ", "" + response);

                progress_dialog.dismiss();

                OrderProductsResponse productsResponse = new OrderProductsResponse();
                productsResponse = gson.fromJson(response , OrderProductsResponse.class);

                for (int i = 0 ; i < productsResponse.getData().size() ; i++){

                    ll_parent_products.addView(AddProductDetails(String.valueOf(i+1) , productsResponse.getData().get(i).getAttributes()));

                }




            }

            @Override
            public void onError(ANError anError) {

                progress_dialog.dismiss();

                Log.e("*errror", "" + anError.getErrorBody());
                Log.e("*errror", "" + anError.getErrorDetail());
                Log.e("*errror", "" + anError.getMessage());
                Log.e("*error", "" + anError.getStackTrace());
                Log.e("*error", "" + anError.getCause());

                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse = gson.fromJson(anError.getErrorBody() , ErrorResponse.class);

                Toast.makeText(OrderDetailsActivity.this, ""+errorResponse.getErrors().get(0).getTitle(), Toast.LENGTH_SHORT).show();

            }
        });

    }




    public View AddProductDetails(String no , OrderProductsResponse.DataBean.AttributesBean attributes){

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        View vv = inflater.inflate(R.layout.layout_product_details, null);

        TextView txt_no = (TextView)vv.findViewById(R.id.product_no);
        TextView txt_name = (TextView)vv.findViewById(R.id.product_name);
        TextView txt_amount = (TextView)vv.findViewById(R.id.product_amount);
        TextView txt_qty = (TextView)vv.findViewById(R.id.product_qty);
        TextView txt_price = (TextView)vv.findViewById(R.id.product_price);
        TextView txt_tax = (TextView)vv.findViewById(R.id.product_tax);

        txt_no.setText(no);
        txt_name.setText(attributes.getItemCode());
        txt_amount.setText(String.format( "%.2f", Double.parseDouble(""+attributes.getExtPrice())));
      //  txt_amount.setText(""+attributes.getExtPrice());
        txt_qty.setText(""+attributes.getQuantityOrdered());
        txt_price.setText(String.format( "%.2f", Double.parseDouble(""+attributes.getSalesPriceRate())));
      //  txt_price.setText(""+attributes.getSalesPriceRate());
        txt_tax.setText(String.format( "%.2f", Double.parseDouble(""+attributes.getSalesTaxAmountRate())));
      //  txt_tax.setText(""+attributes.getSalesTaxAmountRate());


        return vv ;


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
                    Intent i = new Intent(OrderDetailsActivity.this , MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }else if (toolbartxt.getText().toString().equals("ORDERS >")){
                    finish();
                }else {

                }
            }
        });


       return vv ;

    }




}
