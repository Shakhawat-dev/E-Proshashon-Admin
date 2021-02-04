package com.metacoders.e_proshashonadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.annotations.NotNull;
import com.metacoders.e_proshashonadmin.R;

import java.util.List;

/*** Created by Rahat Shovo on 2/4/2021 
 */
public class Check_box_adapter extends RecyclerView.Adapter<Check_box_adapter.viewholder> {

    private final Context context;
    private List<String> items;
    private ItemClickListener itemClickListener;

    public Check_box_adapter(List<String> items, Context context, ItemClickListener itemClickListener) {
        this.items = items;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public
    viewholder onCreateViewHolder(@NonNull ViewGroup parent,
                                  int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_checkbox, parent, false);
        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        String item = items.get(position);

//        holder.checkBox.setOnCheckedChangeListener(v -> {
//
//        });

        holder.checkBox.setText(item);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                itemClickListener.onItemClick(item , isChecked);
            }
        });

    }


    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    static class viewholder extends RecyclerView.ViewHolder {
        CheckBox checkBox ;


        viewholder(@NonNull View itemView) {

            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);

        }


    }

    public interface ItemClickListener {
        void onItemClick(String model, boolean isSelected);
    }


}