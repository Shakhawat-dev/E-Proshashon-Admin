package com.metacoders.e_proshashonadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.metacoders.e_proshashonadmin.Const.Const;
import com.metacoders.e_proshashonadmin.Models.UserModel;
import com.metacoders.e_proshashonadmin.R;

import java.util.List;

/*** Created by Rahat Shovo on 2/14/2021 
 */
public class userAdapter extends RecyclerView.Adapter<userAdapter.viewholder> {

    private final Context context;
    private List<UserModel> items;
    private ItemClickListener itemClickListener;
    private DatabaseReference databaseReference;

    public userAdapter(List<UserModel> items, Context context, ItemClickListener itemClickListener) {
        this.items = items;
        this.context = context;
        this.itemClickListener = itemClickListener;
        databaseReference = FirebaseDatabase.getInstance().getReference(Const.USER_PROFILE_REPO);
    }

    @NonNull
    @Override
    public @NotNull
    viewholder onCreateViewHolder(@NonNull ViewGroup parent,
                                  int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_user, parent, false);
        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, final int position) {
        UserModel item = items.get(position);

        holder.itemView.setOnClickListener(v -> {
            itemClickListener.onItemClick(item);
        });
        holder.title.setText(item.getName());
        holder.desc.setText(item.getNumber());

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser(position, item.getUser_id());
            }
        });

    }

    public void deleteUser(int position, String uid) {

        if (databaseReference != null) {
            databaseReference.child(uid).removeValue();
            items.remove(position);
            notifyItemRemoved(position);
            notifyDataSetChanged();

        }


    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public interface ItemClickListener {
        void onItemClick(UserModel model);
    }

    class viewholder extends RecyclerView.ViewHolder {
        public TextView title, desc;
        public Button deleteButton;


        viewholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.complain_title);
            desc = itemView.findViewById(R.id.complain_details);
            deleteButton = itemView.findViewById(R.id.delete);
        }


    }


}