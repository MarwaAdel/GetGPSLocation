package com.example.getgpslocation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.getgpslocation.R;
import com.example.getgpslocation.models.VistedStore;

import java.util.List;


/**
 * Created by Eman on 11/27/2016.
 */

public class VistedStoresAdapter extends RecyclerView.Adapter<VistedStoresAdapter.MyViewHolder>  {
    Context mContext;

    final private VistedStoresAdapter.VisitedOnClickHolder mClickHolder;

    private List<VistedStore> oderName;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView itemPlace;
        public TextView itemName;
        public TextView itemData;
        public MyViewHolder(View view) {
            super(view);
            itemName = (TextView) view.findViewById(R.id.txt_name);
            itemPlace = (TextView) view.findViewById(R.id.txt_place);
            itemData = (TextView) view.findViewById(R.id.txt_data);


            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPostion = getAdapterPosition();
            Log.e("values",  " "+ adapterPostion +" ");
            mClickHolder.onClick(this, adapterPostion );

        }


    }
    public static interface OnItemCheckedClick {
        void onItemCheckedClick(VistedStoresAdapter.MyViewHolder vh, int position);
    }
    public static interface VisitedOnClickHolder {
        void onClick(VistedStoresAdapter.MyViewHolder vh, int position);
    }




    public VistedStoresAdapter(List<VistedStore>  oderName, Context mContext, VistedStoresAdapter.VisitedOnClickHolder dh) {
        this.oderName = oderName;
        this.mContext = mContext;
        mClickHolder = dh;
    }


    @Override
    public VistedStoresAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.visited_item, parent, false);

        return new VistedStoresAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VistedStoresAdapter.MyViewHolder holder, int position) {
        VistedStore place = oderName.get(position);
        holder.itemName.setText(place.getAgent_name());
        holder.itemPlace.setText( place.getTitle());
        String data = place.getCreated_at();
        holder.itemData.setText( data.substring(0, data.indexOf(' ')));


    }


    @Override
    public int getItemCount() {
        return oderName.size();
    }
}

