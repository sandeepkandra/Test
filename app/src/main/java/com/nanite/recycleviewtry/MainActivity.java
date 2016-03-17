package com.nanite.recycleviewtry;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;

import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nanite.recycleviewtry.gson.OrderParent;
import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

public class MainActivity extends Activity {

    String deal_token;
    CustomAdapter kadapter;
    TokenAdapter token_adapter;
    static final String[] MOBILE_OS = new String[]{"Token1", "Token3", "Token4", "Token5"};

    RecyclerView recycle_tokenId, rvrecycle_token;
    OrderParent orderParent;

    String getOrderId;

int setSelected=-1;
    StaggeredGridLayoutManager gridLayoutManager ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //   for token Id
        rvrecycle_token = (RecyclerView) findViewById(R.id.recycle_token);
        recycle_tokenId = (RecyclerView) findViewById(R.id.recycle_tokenId);



        gridLayoutManager=  new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
// Attach the layout manager to the recycler view
        recycle_tokenId.setLayoutManager(gridLayoutManager);

       // recycle_tokenId.setNestedScrollingEnabled(true);



// for token Number
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
// Control orientation of the items
// also supports LinearLayoutManager.HORIZONTAL
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
// Optionally customize the position you want to default scroll to
        layoutManager.scrollToPosition(0);
      //  layoutManager.scrollToPositionWithOffset(2, 20);

// Attach layout manager to the RecyclerView
        rvrecycle_token.setLayoutManager(layoutManager);

        Pusher pusher = new Pusher("1f006f9bd40000fbe5e8");

        Channel channel = pusher.subscribe("k_channel");

        channel.bind("k_event", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                System.out.println(data);
                Log.i("channel", channelName + "");
                //
                try {

                    JSONObject jsonObj = new JSONObject(data);
                    String message = jsonObj.getString("message");
                    String order_status = jsonObj.getString("order_status");

                    getOrderId = jsonObj.getString("order_id");
                    getOrders();

                    generateNotification(MainActivity.this, "Kitchen" + message);

                    // getOrderidDetails();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.wtf("eerpush", e.toString());
                }


            }
        });

        pusher.connect();


        getOrders();
        //rvrecycle_token.setBackgroundResource(R.drawable.backgroundgreenlite);




        rvrecycle_token.addOnItemTouchListener(
                new ItmTouch(getApplicationContext(), new ItmTouch.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever

                       /* for(int i=0;i<orderParent.Orders.OrderArray.size();i++)
                        {

                            orderParent.Orders.OrderArray.get(i).colorIsGreen=false;
                        }
*/
                      //  kadapter.notifyItemRemoved(position);

                        if(setSelected!=-1 && orderParent.Orders.OrderArray.get(setSelected).colorIsGreen)
                            orderParent.Orders.OrderArray.get(setSelected).colorIsGreen=false;
                        setSelected=position;
                        orderParent.Orders.OrderArray.get(position).colorIsGreen=true;


                       // kadapter.notifyItemChanged(position);
                        token_adapter.notifyDataSetChanged();
                       // colorIsGreen
                       // int itemPosition = rvrecycle_token.getChildPosition(view);
                       // setSelected = position;
                        //String item = kadapter.getItemCount(itemPosition);

                         /*  view.findViewById(R.id.parentTokens).setBackgroundResource(R.drawable.backgroundgreen);
                        view.findViewById(R.id.txtVwTokenNoHori).setBackgroundResource(R.drawable.backgroundgreen);
                        view.findViewById(R.id.txtVwTokenIdHori).setBackgroundResource(R.drawable.backgroundgreen);*/

                        recycle_tokenId.smoothScrollToPosition(position);
                       // rvrecycle_token.getChildAdapterPosition(view).setBackgroundResource(R.drawable.backgroundgreen);
                    //gridLayoutManager.scrollToPositionWithOffset(setSelected, 20);
                       // Toast.makeText(getApplicationContext(), itemPosition + "", Toast.LENGTH_LONG).show();
                    }
                })
        );

       // recycle_tokenId.set

    }


    private void getOrders() {
        //
        String url = "http://sqweezy.com/DriveThru/Get_OrderDetails.php?merchant_id=1&recent=true";
        // String url = "http://sqweezy.com/DriveThru/getOrderDetailsGrouped.php?merchant_id=20";
        StringRequest req = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {


                    GsonBuilder builder = new GsonBuilder();
                    Gson gson1 = builder.create();
                    orderParent = gson1.fromJson(response, OrderParent.class);

                    final OrderParent.OrderArrayDetails data = null;

                    rvrecycle_token = (RecyclerView) findViewById(R.id.recycle_token);
                    recycle_tokenId = (RecyclerView) findViewById(R.id.recycle_tokenId);



                    gridLayoutManager=  new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
// Attach the layout manager to the recycler view
                    recycle_tokenId.setLayoutManager(gridLayoutManager);

                    // recycle_tokenId.setNestedScrollingEnabled(true);

      // comment

// for token Number
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
// Control orientation of the items
// also supports LinearLayoutManager.HORIZONTAL
                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
// Optionally customize the position you want to default scroll to
                    layoutManager.scrollToPosition(0);
                    //  layoutManager.scrollToPositionWithOffset(2, 20);

// Attach layout manager to the RecyclerView
                    rvrecycle_token.setLayoutManager(layoutManager);


                        kadapter = new CustomAdapter(getApplicationContext(), R.layout.grid_layout, orderParent.Orders.OrderArray);

                        recycle_tokenId.setAdapter(kadapter);


                        token_adapter = new TokenAdapter(getApplicationContext(), R.layout.list_vw_tokens, orderParent.Orders.OrderArray);

                        rvrecycle_token.setAdapter(token_adapter);



                }
                catch(Exception e)
                {
                Log.e("e4",e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                getOrders();
            }
        });
        MyApp.reqstQ.add(req);
    }

    public void getOrderidDetails() {


        String url = "http://sqweezy.com/DriveThru/Get_OrderDetail.php?order_id="+getOrderId;

        StringRequest req = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson1 = builder.create();
                    orderParent = gson1.fromJson(response, OrderParent.class);



                    kadapter = new CustomAdapter(getApplicationContext(), R.layout.grid_layout,orderParent.Orders.OrderArray);

                    recycle_tokenId.setAdapter(kadapter);


                    token_adapter = new TokenAdapter(getApplicationContext(), R.layout.list_vw_tokens, orderParent.Orders.OrderArray);

                    rvrecycle_token.setAdapter(token_adapter);
                }
                catch(Exception e)
                {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // getOrders();
            }
        });
        MyApp.reqstQ.add(req);
    }




    private static void generateNotification(Context context, String message) {

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.cooking)
                        .setContentTitle("DriveThru")
                        .setContentText("This is a DriveThru notification"+"\n"+message);


        Intent notificationIntent = new Intent(context, MainActivity.class);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(contentIntent);
        builder.setAutoCancel(true);
        builder.setLights(Color.BLUE, 500, 500);
        long[] pattern = {500,500,500,500,500,500,500,500,500};
        builder.setVibrate(pattern);
        builder.setStyle(new NotificationCompat.InboxStyle());
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);
// Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());

    }

}
