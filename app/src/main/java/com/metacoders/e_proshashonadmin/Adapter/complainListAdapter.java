package com.metacoders.e_proshashonadmin.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.annotations.NotNull;
import com.metacoders.e_proshashonadmin.Models.ComplainModel;
import com.metacoders.e_proshashonadmin.R;

import java.util.ArrayList;
import java.util.List;

/*** Created by Rahat Shovo on 2/12/2021 
 */
public class complainListAdapter extends RecyclerView.Adapter<complainListAdapter.viewholder> {

    private final Context context;
    private List<ComplainModel> mData = new ArrayList<>();
    private List<ComplainModel> mDataFiltered = new ArrayList<>();
    private final ItemClickListener itemClickListener;

    public complainListAdapter(List<ComplainModel> items, Context context, ItemClickListener itemClickListener) {
        this.mData = items;
        this.mDataFiltered = items;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public @NotNull
    viewholder onCreateViewHolder(@NonNull ViewGroup parent,
                                  int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_complaints, parent, false);
        return new viewholder(v);
    }

    public void addItems(List<ComplainModel> newItems) {
        mData.addAll(newItems);
        Log.d("TAG", "old data  Size -? : " + newItems.size());
        mDataFiltered = mData;
        Log.d("TAG", "filltered Size -> : " + mDataFiltered.size());
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        ComplainModel item = mDataFiltered.get(position);

        holder.itemView.setOnClickListener(v -> {
            itemClickListener.onItemClick(item);
        });

        holder.title.setText(item.getComplain_type());
        //    fareView.setText(fare);

        if (item.getComplain_status().equals("ACCEPTED")) {
            holder.iconImage.setBackgroundColor(ContextCompat.getColor(context, R.color.green_500));
            holder.iconImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_verified_24));
        } else if (item.getComplain_status().equals("REJECTED")) {
            holder.iconImage.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRed));
            holder.title.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
            holder.iconImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_new_releases_24));
        } else if (item.getComplain_status().equals("COMPLETED")) {
            holder.iconImage.setBackgroundColor(ContextCompat.getColor(context, R.color.green_500));
            holder.iconImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.completed_icon_24));
        }

        if (item.getComment().equals("NULL") || item.getComment().equals("নাই") || item.getComment().equals("")) {
            holder.desc.setText("মন্তব্যঃ কোন মন্তব্য পরেনি।");
        } else {
            holder.desc.setText("মন্তব্যঃ " + item.getComment());
        }

    }


    @Override
    public int getItemCount() {
        if (mDataFiltered == null) {
            return 0;
        }
        return mDataFiltered.size();
    }



    public interface ItemClickListener {
        void onItemClick(ComplainModel model);
    }

    class viewholder extends RecyclerView.ViewHolder {

        public TextView title, desc;
        public ImageView iconImage;


        viewholder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.complain_title);
            desc = itemView.findViewById(R.id.complain_details);
            iconImage = itemView.findViewById(R.id.status_image);
        }


    }


}