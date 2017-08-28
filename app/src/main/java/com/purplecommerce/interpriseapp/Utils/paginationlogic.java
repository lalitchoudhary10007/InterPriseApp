package com.purplecommerce.interpriseapp.Utils;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class paginationlogic extends AppCompatActivity {

//    ArrayList<String> data = new ArrayList<>();
//    int pageCount = 0 , totalPage = 1  , perpagecount = 3 , remainingcount = 0  ;
//    TextView pagetx ;
//    LinearLayout ll_parent;
//    public static String prevoiusOne = "test" ;
//
//    ArrayList<String> hs_page = new ArrayList<>();
//    ArrayList<String> hs_orderpage = new ArrayList<>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        pagetx = (TextView)findViewById(R.id.pagetxt);
//        ll_parent = (LinearLayout)findViewById(R.id.add_parent);
//        data.clear();
//        for (int i = 1 ; i < 21 ; i++ ){
//
//            data.add("Hiii"+i);
//
//        }
//
//        if (data.size()>perpagecount){
//
//            pageCount = 0 ;
//
////            for (int i = 0 ; i < perpagecount ; i++){
////
////                ll_parent.addView(ADDview(data.get(i)));
////
////            }
//
//              totalPage = data.size() / perpagecount ;
//              remainingcount = data.size() % perpagecount ;
//
//              for (int i = 1 ; i <= totalPage ; i++){
//                  hs_page.add(""+i);
//                  hs_orderpage.add(String.valueOf(i*perpagecount));
//              }
//
//              if (remainingcount == 0){
//                  Toast.makeText(this, "No remain", Toast.LENGTH_SHORT).show();
//              }else {
//                  Toast.makeText(this, "remains", Toast.LENGTH_SHORT).show();
//                  hs_page.add(String.valueOf(totalPage+1));
//                  hs_orderpage.add(String.valueOf(remainingcount));
//              }
//              int siz = Integer.parseInt(hs_orderpage.get(pageCount));
//              for (int i = 0 ; i < siz ; i++){
//                  ll_parent.addView(ADDview(data.get(i)));
//              }
//
//            pagetx.setText("1"+"-"+siz+"/"+data.size());
//
////            totalPage = data.size() / perpagecount ;
////            remainingcount = data.size() % perpagecount ;
////            dummytotalpage = totalPage ;
////            if (remainingcount == 0){
////                Toast.makeText(this, "No remains", Toast.LENGTH_SHORT).show();
////                dummytotalpage = totalPage ;
////            }else {
////                Toast.makeText(this, "remains"+remainingcount, Toast.LENGTH_SHORT).show();
////                dummytotalpage = totalPage + 1 ;
////            }
////
////            String ss =  NextGetCountsPerpage(totalPage , 0 , perpagecount , pageCount , data.size());
////            Log.e("returned","string"+ss);
////            StringTokenizer stringTokenizer = new StringTokenizer(ss , "/")  ;
////            String max = stringTokenizer.nextToken();
////            String min = stringTokenizer.nextToken();
////            pagetx.setText(min+"-"+max+"/"+data.size());
//
//        }else {
//
//            for (int i = 0 ; i < data.size() ; i++){
//
//                ll_parent.addView(ADDview(data.get(i)));
//
//            }
//
//        }
//
//
//
//
//        findViewById(R.id.nxt).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                prevoiusOne = "2" ;
//
//                if (pageCount+1 == hs_page.size()) {
//                    Toast.makeText(paginationlogic.this, "That' it22", Toast.LENGTH_SHORT).show();
//                }else {
//
//                    pageCount = pageCount + 1;
//
//                    if (pageCount < hs_page.size()) {
//
//                        ll_parent.removeAllViews();
//
//                        int max = Integer.parseInt(hs_orderpage.get(pageCount));
//                        int min = Integer.parseInt(hs_orderpage.get(pageCount - 1));
//
//                        Log.e("**max", "" + max);
//                        Log.e("**min", "" + min);
//
//                        if (max < perpagecount) {
//
//                            int max2 = min + max;
//                            pagetx.setText(min + "-" + max2 + "/" + data.size());
//                            Log.e("max2", "for last" + max2);
//                            for (int i = min; i < max2; i++) {
//
//                                ll_parent.addView(ADDview(data.get(i)));
//                            }
//                        } else {
//                            pagetx.setText(min + "-" + max + "/" + data.size());
//                            for (int i = min; i < max; i++) {
//
//                                ll_parent.addView(ADDview(data.get(i)));
//                            }
//                        }
//
//
//                    } else {
//                      //  Toast.makeText(paginationlogic.this, "That' it", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//
//            }
//        });
//        findViewById(R.id.prev).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                   if (prevoiusOne.equals("1")){
//
//                       Toast.makeText(paginationlogic.this, "That' it previous", Toast.LENGTH_SHORT).show();
//
//                   }else {
//
//                       pageCount = pageCount - 1 ;
//                       if (pageCount == 0){
//
//                           prevoiusOne = "1" ;
//
//                           ll_parent.removeAllViews();
//
//                           Log.e("order page",""+hs_orderpage);
//                           Log.e(" page",""+hs_page);
//                           int max = Integer.parseInt(hs_orderpage.get(pageCount));
//                           pagetx.setText("1"+"-"+max+"/"+data.size());
//                           for (int i= 0 ; i < max ; i++){
//                               ll_parent.addView(ADDview(data.get(i)));
//                           }
//
//
//                       }else if (pageCount < hs_page.size()){
//
//                           prevoiusOne = "2" ;
//
//                           ll_parent.removeAllViews();
//
//                           Log.e("order page",""+hs_orderpage);
//                           Log.e(" page",""+hs_page);
//                           int max = Integer.parseInt(hs_orderpage.get(pageCount));
//                           int min = Integer.parseInt(hs_orderpage.get(pageCount-1));
//
//                           Log.e("**max",""+max);
//                           Log.e("**min",""+min);
//
//                           if (max < perpagecount){
//
//                               int max2 = min + max ;
//                               pagetx.setText(min+"-"+max2+"/"+data.size());
//                               Log.e("max2","for last"+max2);
//                               for (int i = min ; i < max2 ; i++){
//
//                                   ll_parent.addView(ADDview(data.get(i)));
//                               }
//                           }else {
//                               pagetx.setText(min+"-"+max+"/"+data.size());
//                               for (int i = min ; i < max ; i++){
//
//                                   ll_parent.addView(ADDview(data.get(i)));
//                               }
//                           }
//
//
//                       }
//                       else {
//                         //  Toast.makeText(paginationlogic.this, "That' it", Toast.LENGTH_SHORT).show();
//                       }
//
//                   }
//
//
//
//
//            }
//        });
//
//
//
//
//
//
//    }
//
//    private String NextGetCountsPerpage(int totalPage, int remainingcount, int perpagecount, int pageCount , int totalorders) {
//        String s = "" ;
//
//        if (pageCount == totalPage){
//            s = "0" ;
//            Toast.makeText(this, "Page Finished !!", Toast.LENGTH_SHORT).show();
//        }else if (remainingcount == 0){
//            int max = perpagecount *  pageCount ;
//            int min = max - perpagecount + 1 ;
//            s = ""+max+"/"+min;
//        } else {
//
//           int max = totalorders ;
//           int min = totalorders - remainingcount ;
//           s = ""+max + "/" + min ;
//
//        }
//
//        Log.e("max","*"+s);
//
//        return s ;
//    }
//
//
//    public View ADDview(String s){
//
//        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View v = vi.inflate(R.layout.add_layout, null);
//
//        TextView textView = (TextView) v.findViewById(R.id.add_txt);
//        textView.setText(s);
//
//        return v ;
//
//    }
//


}
