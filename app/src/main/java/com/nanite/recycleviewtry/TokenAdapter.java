package com.nanite.recycleviewtry;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nanite.recycleviewtry.gson.OrderParent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by praveen kumar on 1/23/2016.
 */
public class TokenAdapter extends
        RecyclerView.Adapter<TokenAdapter.ViewHolder> {

    Context mctxt;
    private static int focusedItem = 0;
    private static int lastCheckedPos = 0;
    private String mItem;




    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.list_vw_tokens, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // Get the data model based on position
      //  Tokens contact = mContacts.get(position);
        final OrderParent.OrderArrayDetails data = mkit.get(position);


        // Set item views based on the data model


        TextView txtVwTokenNo = holder.txtVwTokenNo;
        txtVwTokenNo.setText("Order: "+data.Order_ID+"");
        holder.txtVwTokenNo.setBackgroundResource(R.drawable.backgroundgreen);
        holder.txtVwTokenId.setBackgroundResource(R.drawable.backgroundgreen);

        holder.txtVwTokenId.setText("Token: "+data.Token + "");

      /*  holder.parentTokens.setBackgroundColor(data.colorIsGreen ? Color.parseColor("#00FF00") : Color.parseColor("#ffffff"));
        holder.txtVwTokenId.setBackgroundColor(data.colorIsGreen ? Color.parseColor("#00FF00") : Color.parseColor("#ffffff"));
        holder.txtVwTokenNo.setBackgroundColor(data.colorIsGreen ? Color.parseColor("#00FF00") : Color.parseColor("#ffffff"));
*/

        holder.parentTokens.setBackgroundResource(data.colorIsGreen ? R.drawable.backgroundgreen :  R.drawable.backgroundgreengray);
        holder.txtVwTokenId.setBackgroundResource(data.colorIsGreen ? R.drawable.backgroundgreen :  R.drawable.backgroundgreengray);
        holder.txtVwTokenNo.setBackgroundResource(data.colorIsGreen ? R.drawable.backgroundgreen :  R.drawable.backgroundgreengray);

       /* holder.parentTokens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.txtVwTokenId.setBackgroundResource(R.drawable.backgroundgreen);
                holder.parentTokens.setBackgroundResource(R.drawable.backgroundgreen);
                holder.txtVwTokenNo.setBackgroundResource(R.drawable.backgroundgreen);
                // holder.parentTokens.setBackgroundColor(Color.DKGRAY);
                // Toast.makeText(mctxt, data.Order_ID + "", Toast.LENGTH_LONG).show();


            }
        });


        holder.txtVwTokenNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.txtVwTokenId.setBackgroundResource(R.drawable.backgroundgreen);
                holder.parentTokens.setBackgroundResource(R.drawable.backgroundgreen);
                holder.txtVwTokenNo.setBackgroundResource(R.drawable.backgroundgreen);
                // holder.parentTokens.setBackgroundColor(Color.DKGRAY);
                // Toast.makeText(mctxt, data.Order_ID + "", Toast.LENGTH_LONG).show();


            }
        });

        holder.txtVwTokenId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.txtVwTokenId.setBackgroundResource(R.drawable.backgroundgreen);
                holder.parentTokens.setBackgroundResource(R.drawable.backgroundgreen);
                holder.txtVwTokenNo.setBackgroundResource(R.drawable.backgroundgreen);
                // holder.parentTokens.setBackgroundColor(Color.DKGRAY);
                // Toast.makeText(mctxt, data.Order_ID + "", Toast.LENGTH_LONG).show();


            }
        });
*/



       /* if (contact.isOnline()) {
            button.setText("ABCDE12");
            button.setEnabled(true);
        }
        else {
            button.setText("BDSSFDS23");
            button.setEnabled(false);
        }
*/
    }

    @Override
    public int getItemCount() {
        return mkit.size();
    }
    // Store a member variable for the contacts
    private ArrayList<OrderParent.OrderArrayDetails> mkit;
    // Store a member variable for the contacts
    private List<Tokens> mContacts;




    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);


        // Handle key up and key down and attempt to move selection
        recyclerView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();

                // Return false if scrolled to the bounds and allow focus to move off the list
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                        return tryMoveSelection(lm, 1);
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                        return tryMoveSelection(lm, -1);
                    }
                }

                return false;
            }
        });
    }

    private boolean tryMoveSelection(RecyclerView.LayoutManager lm, int direction) {
        int tryFocusItem = focusedItem + direction;

        // If still within valid bounds, move the selection, notify to redraw, and scroll
        if (tryFocusItem >= 0 && tryFocusItem < getItemCount()) {
            notifyItemChanged(focusedItem);
            focusedItem = tryFocusItem;
            notifyItemChanged(focusedItem);
            lm.scrollToPosition(focusedItem);
            return true;
        }

        return false;
    }


    // Pass in the contact array into the constructor
    public TokenAdapter(Context context, int layoutResourceId,ArrayList<OrderParent.OrderArrayDetails> kit) {
        mkit = kit;
        mctxt=context;
    }



    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView txtVwTokenNo;
        public TextView txtVwTokenId;
        LinearLayout parentTokens;
        private String mItem;

        public void setItem(String item) {
            mItem = item;
            txtVwTokenId.setText(item);
        }

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView)  {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            itemView.setOnClickListener( this);

            txtVwTokenNo = (TextView) itemView.findViewById(R.id.txtVwTokenNoHori);
            txtVwTokenId = (TextView) itemView.findViewById(R.id.txtVwTokenIdHori);
            parentTokens = (LinearLayout) itemView.findViewById(R.id.parentTokens);

// Handle item click and set the selection
            parentTokens.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Redraw the old selection and the new
                    notifyItemChanged(focusedItem);
                    focusedItem = getLayoutPosition();
                    notifyItemChanged(focusedItem);


                }
            });

        }

        @Override
        public void onClick(View v) {


        }
    }
}
