package com.metacoders.e_proshashonadmin.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.annotations.NotNull;
import com.metacoders.e_proshashonadmin.Models.ComplainModel;
import com.metacoders.e_proshashonadmin.R;

import java.util.ArrayList;
import java.util.List;

/*** Created by Rahat Shovo on 2/12/2021 
 */
public class complainListAdapter extends RecyclerView.Adapter<complainListAdapter.viewholder>
        implements Filterable {

    private final Context context;
    private List<ComplainModel> mData = new ArrayList<>();
    private List<ComplainModel> mDataFiltered = new ArrayList<>();
    private ItemClickListener itemClickListener;

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

        holder.title.setText(item.getName());
        //    fareView.setText(fare);
        holder.desc.setText(item.getComment());
    }


    @Override
    public int getItemCount() {
        if (mDataFiltered == null) {
            return 0;
        }
        return mDataFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence department) {
                // uid , upzila , role

                String Key = department.toString();
                String str[] = Key.split(",");
                String a = "";

                if (str[0].isEmpty()) {
                    str[0] = "";
                }
                if (str[1].isEmpty()) {
                    str[1] = "";
                }

                if (str[2].isEmpty()) {
                    str[2] = "";
                }

                if (Key.isEmpty()) {

                    mDataFiltered = mData;
                    Log.d("TAG", "i am hese : ");
                } else {
                    List<ComplainModel> lstFiltered = new ArrayList<>();
                    for (ComplainModel row : mData) {
                        //Log.d("TAG", "Filtering : " + row.getProductTitle());
                        if (row.getEmp_uid().contains(str[0]) &&
                                row.getUser_thana_upzila().contains(str[1]) &&
                                row.getEmp_role().contains(str[2])) {


                            lstFiltered.add(row);
                        } else {
                            //  Log.d("TAG", "Fillered: " + row.getPost_id());
                        }

                    }
                    // Log.d("TAG", "Size: " + lstFiltered.size());
                    mDataFiltered = lstFiltered;
                    // Log.d("TAG", "dataset Size: " + mDataFiltered.size());

                }


                FilterResults filterResults = new FilterResults();
                filterResults.values = mDataFiltered;
                return filterResults;

            }


            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {


                mDataFiltered = (List<ComplainModel>) results.values;
                notifyDataSetChanged();

            }
        };
    }

    public interface ItemClickListener {
        void onItemClick(ComplainModel model);
    }

    class viewholder extends RecyclerView.ViewHolder {

        public TextView title, desc;


        viewholder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.complain_title);
            desc = itemView.findViewById(R.id.complain_details);
        }


    }


}