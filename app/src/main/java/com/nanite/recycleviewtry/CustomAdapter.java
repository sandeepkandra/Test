package com.nanite.recycleviewtry;

/**
 * Created by praveen kumar on 1/23/2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.nanite.recycleviewtry.gson.OrderParent;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by praveen kumar on 1/23/2016.
 */
public class CustomAdapter extends
        RecyclerView.Adapter<CustomAdapter.ViewHolder>{
    String orderid,orderStatus;
    int consumerid;
    Context mctxt;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.grid_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    OrderParent.OrderArrayDetails data = null;
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Get the data model based on position

        try {
            data = mkit.get(position);


            // Set item views based on the data model



                holder.txtVwTokenNo.setText("Order: " + data.Order_ID + "");


                holder.txtVwTokenId.setText("Token: "
                        + data.Token + "");


            String fVal="";

            holder.txVwCredentials.measure(0, 0);       //must call measure!
            holder.txVwCredentials.getMeasuredHeight(); //get width

            int width= holder.txVwCredentials.getMeasuredWidth()-5;
                for (int i = 0; i < data.Products.size();i++ ) {
                    String output = "";

                    output =  "<b>"+data.Products.get(i).Product_Name + "                " + data.Products.get(i).Quantity + "</b> <br /> "+"\n----------------------------------------------------\n";
                    //Log.wtf("ii", data.Products.get(i).Product_Name);
                 //   if (data.Products.get(i).Customization.size() > 0) {
                    String customized = "";
                        for (int j = 0; j < data.Products.get(i).Customization.size(); j++) {
                            //   final OrderParent.CustomisationCls da = data.Products.get(i).Customization.get(position);
                            customized +=  data.Products.get(i).Customization.get(j).category + ": " + data.Products.get(i).Customization.get(j).category_value+"<br /> ";

                            // Log.wtf("cus", data.Products.get(i).Customization.get(i).category);
                            // holder.txVwCredentials.setText(output + "\n" + data.Products.get(i).Customization.get(position).category);
                        }
                    fVal+="<br /> "+output+"<br /> "+customized;


                }




            holder.txVwCredentials.setText(Html.fromHtml(fVal));

        }
            catch(Exception e)
            {
                Log.e("e2", e.toString());
            }

            //holder.txVwCredentials.setText("Product Name: "+data. + "\n"+"Quantity: "+data.quantity+"\n"+"customization_Required: "+data.customization_Required +"\n"+"customisation:"+"\n"+"Categoryid"+data.category+"\n"+"category_value"+data.category_value);

            // scroll text View

            holder.txVwCredentials.setMaxLines(100);
            holder.txVwCredentials.setVerticalScrollBarEnabled(true);
            holder.txVwCredentials.setMovementMethod(new ScrollingMovementMethod());
            View.OnTouchListener listener = new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    boolean isLarger;

                    isLarger = ((TextView) v).getLineCount()
                            * ((TextView) v).getLineHeight() > v.getHeight();
                    if (event.getAction() == MotionEvent.ACTION_MOVE
                            && isLarger) {
                        v.getParent().requestDisallowInterceptTouchEvent(true);

                    } else {
                        v.getParent().requestDisallowInterceptTouchEvent(false);

                    }
                    return false;
                }
            };

            holder.txVwCredentials.setOnTouchListener(listener);


            //for(int i=0;i<data.ProductsDetails.size();i++)

            //holder.txVwCredentials.setText(data.Products.get(position).Product_Name+data.Products.get(position).Quantity);


            // holder.txVwCredentials.setText(output);
            if (data.Status_id.contains("2")) {

                holder.txtVwCheckStatusOrder.setEnabled(false);
                holder.txtVwCheckStatusCompleted.setEnabled(true);
                holder.txtVwCheckStatusCooking.setEnabled(false);
                holder.txtVwCheckStatusOrder.setBackgroundResource(R.drawable.backgroundgreenlite);

                holder.txtVwCheckStatusCooking.setBackgroundResource(R.drawable.backgroundgreenlite);
                holder.txtVwCheckStatusCompleted.setBackgroundResource(R.drawable.backgroundgreen);


            } else if (data.Status_id.contains("3")) {

                holder.txtVwCheckStatusOrder.setEnabled(false);
                holder.txtVwCheckStatusCompleted.setEnabled(false);
                holder.txtVwCheckStatusCooking.setEnabled(false);

                holder.txtVwCheckStatusOrder.setBackgroundResource(R.drawable.backgroundgreenlite);

                holder.txtVwCheckStatusCooking.setBackgroundResource(R.drawable.backgroundgreenlite);
                holder.txtVwCheckStatusCompleted.setBackgroundResource(R.drawable.backgroundgreenlite);


                //holder.txtVwCheckStatus.setText("Ready To Pickup");

            } else if (data.Status_id.contains("1")){

                holder.txtVwCheckStatusOrder.setEnabled(false);
                holder.txtVwCheckStatusCompleted.setEnabled(true);
                holder.txtVwCheckStatusCooking.setEnabled(true);

                holder.txtVwCheckStatusOrder.setBackgroundResource(R.drawable.backgroundgreenlite);

                holder.txtVwCheckStatusCooking.setBackgroundResource(R.drawable.backgroundgreen);
                holder.txtVwCheckStatusCompleted.setBackgroundResource(R.drawable.background_white);


            }
            else if (data.Status_id.contains("4")){

                holder.txtVwCheckStatusCooking.setEnabled(false);
                holder.txtVwCheckStatusCompleted.setEnabled(false);
                holder.txtVwCheckStatusOrder.setEnabled(false);

                holder.txtVwCheckStatusOrder.setBackgroundResource(R.drawable.backgroundgreenlite);

                holder.txtVwCheckStatusCooking.setBackgroundResource(R.drawable.backgroundgreenlite);
                holder.txtVwCheckStatusCompleted.setBackgroundResource(R.drawable.backgroundgreenlite);


            }

            else if (data.Status_id.contains("5")){

                holder.txtVwCheckStatusCooking.setEnabled(false);
                holder.txtVwCheckStatusCompleted.setEnabled(false);
                holder.txtVwCheckStatusOrder.setEnabled(false);

                holder.txtVwCheckStatusOrder.setBackgroundResource(R.drawable.backgroundgreenlite);

                holder.txtVwCheckStatusCooking.setBackgroundResource(R.drawable.backgroundgreenlite);
                holder.txtVwCheckStatusCompleted.setBackgroundResource(R.drawable.backgroundgreenlite);


            }

            final OrderParent.OrderArrayDetails finalData = data;

        holder.txtVwCheckStatusCooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderid = finalData.Order_ID + "";
                finalData.Status_id ="2";
                orderStatus="2";

                consumerid=finalData.consumerid;

                holder.txtVwCheckStatusOrder.setBackgroundResource(R.drawable.backgroundgreenlite);

                holder.txtVwCheckStatusCooking.setBackgroundResource(R.drawable.backgroundgreenlite);
                holder.txtVwCheckStatusCompleted.setBackgroundResource(R.drawable.backgroundgreen);
                holder.txtVwCheckStatusOrder.setEnabled(false);
                holder.txtVwCheckStatusCompleted.setEnabled(true);
                holder.txtVwCheckStatusCooking.setEnabled(false);
                setorderStatus();


               notifyDataSetChanged();


            }
        });
        holder.txtVwCheckStatusCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderid = finalData.Order_ID + "";

                if(finalData.Status_id =="2") {
                    orderStatus = "3";
                    finalData.Status_id = "3";
                    consumerid = finalData.consumerid;

                    holder.txtVwCheckStatusCompleted.setEnabled(false);

                    holder.txtVwCheckStatusOrder.setBackgroundResource(R.drawable.backgroundgreenlite);

                    holder.txtVwCheckStatusCooking.setBackgroundResource(R.drawable.backgroundgreenlite);
                    holder.txtVwCheckStatusCompleted.setBackgroundResource(R.drawable.backgroundgreenlite);


                    setorderStatus();
                    notifyDataSetChanged();
                }
                else {
                    Toast.makeText(mctxt,"Please Change Status of Cooking",Toast.LENGTH_LONG).show();

                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return mkit.size();
    }
    // Store a member variable for the contacts
    private ArrayList<OrderParent.OrderArrayDetails> mkit;

    // Pass in the contact array into the constructor
    public CustomAdapter(Context context, int layoutResourceId,ArrayList<OrderParent.OrderArrayDetails> kit) {
        mkit = kit;
        mctxt=context;


    }
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row

        public TextView txtVwTokenNo,txtVwTokenId,txVwCredentials,txtVwCheckStatusOrder,txtVwCheckStatusCooking,txtVwCheckStatusCompleted;

        LinearLayout parentLayout;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            txtVwTokenNo = (TextView) itemView.findViewById(R.id.txtVwTokenNo);
            txtVwTokenId = (TextView) itemView.findViewById(R.id.txtVwTokenId);
            txVwCredentials = (TextView) itemView.findViewById(R.id.txVwCredentials);

            txtVwCheckStatusOrder=(TextView) itemView.findViewById(R.id.txtVwCheckStatusOrder);
            txtVwCheckStatusCooking=(TextView) itemView.findViewById(R.id.txtVwCheckStatusCooking);
            txtVwCheckStatusCompleted=(TextView) itemView.findViewById(R.id.txtVwCheckStatusCompleted);

            parentLayout=(LinearLayout) itemView.findViewById(R.id.parentLayout);


        }
    }

    private void setorderStatus() {
        String url = "http://sqweezy.com/DriveThru/order_status_update.php?order_id="+orderid+"&status="+orderStatus+"&consumer_id="+consumerid;
        JsonObjectRequest req = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
        Toast.makeText(mctxt,"Sucess",Toast.LENGTH_LONG).show();
               /* Intent i=new Intent(mctxt,MainActivity.class);

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mctxt.startActivity(i);*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setorderStatus();
            }
        });
        MyApp.reqstQ.add(req);
    }


}
