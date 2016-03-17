package com.nanite.recycleviewtry;

/**
 * Created by sandy on 1/21/2016.
 */


import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class Custom_Adapter extends ArrayAdapter<Kitchen> implements Filterable {
    private Context context;
    ArrayList<Kitchen> data;
    int layoutResourceId;
    DealHolder holder;
    //private final String[] countries;

  /*  public Custom_Adapter(Context context, String[] countries) {
        this.context = context;
        this.countries = countries;
    }*/
    public Custom_Adapter(Context context, int layoutResourceId, ArrayList<Kitchen> data) {
        super(context, R.layout.grid_layout, data);
        this.context = context;
        this.data = data;
       // this.searcheddata = data;
        this.layoutResourceId = layoutResourceId;
    }
    public View getView(int position, View convertView, ViewGroup parent) {

       /* LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            gridView = inflater.inflate(R.layout.grid_layout, null);

            TextView textView = (TextView) gridView.findViewById(R.id.txtVwTokenId);*/
        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.grid_layout, parent, false);
            holder = new DealHolder();
            holder.textView = (TextView) row.findViewById(R.id.txtVwTokenId);
            holder.txVwCredentials = (TextView) row.findViewById(R.id.txVwCredentials);
            //  textView.setText(countries[position]);
            holder.textView.setText("ABCD123");

            holder.txVwCredentials.setMovementMethod(new ScrollingMovementMethod());
            //  TextView txtVwCheckStatus = (TextView) row.findViewById(R.id.txtVwCheckStatus);
           /*ScrollView  scrollbar =(ScrollView)gridView.findViewById(R.id.scrollbar);
            scrollbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });*/
            // ImageView flag = (ImageView) gridView .findViewById(R.id.flag);

            //String mobile = countries[position];

            /*if (holder.textView.equals("Token1")) {
                txtVwCheckStatus.setText("AABDCd");
            } else if (holder.textView.equals("Token2")) {
                txtVwCheckStatus.setText("AAB2Cd");
               // flag.setImageResource(R.drawable.go2);
            } else if (holder.textView.equals("Token3")) {
                txtVwCheckStatus.setText("AABD2d");
            } else {
                txtVwCheckStatus.setText("Token4");
            }
else {
            row = (View) convertView;
        }

*/

        row.setTag(holder);
    } else {
        holder = (DealHolder) row.getTag();

    }
        final Kitchen deal = data.get(position);
        Log.i("ino", deal.getdeal_condition());

        holder.txVwCredentials.setMovementMethod(new ScrollingMovementMethod());


        //Toast.makeText(get())
        return row;
    }


   /* @Override
    public int getCount() {
        return countries.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }*/
   public int getCount() {
       return data.size();
   }

    public Kitchen getItem(int position) {
        return data.get(position);
    }

    public long getItemId(int position) {
        return data.get(position).hashCode();
    }


    static class DealHolder {
        TextView textView,txVwCredentials;

    }
}