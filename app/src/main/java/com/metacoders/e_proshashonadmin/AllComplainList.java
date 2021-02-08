package com.metacoders.e_proshashonadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.metacoders.e_proshashonadmin.Adapter.viewholderForAllCompainList;
import com.metacoders.e_proshashonadmin.Models.ComplainModel;
import com.metacoders.e_proshashonadmin.databinding.ActivityAllComplainListBinding;

public class AllComplainList extends AppCompatActivity {

    private ActivityAllComplainListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllComplainListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.list.setLayoutManager(new LinearLayoutManager(this));



        loadAllList() ;

    }

    void loadAllList() {
        FirebaseRecyclerOptions<ComplainModel> options;
        FirebaseRecyclerAdapter<ComplainModel, viewholderForAllCompainList> firebaseRecyclerAdapter;
        DatabaseReference mref = FirebaseDatabase.getInstance().getReference("complain_box");

        options = new FirebaseRecyclerOptions.Builder<ComplainModel>().setQuery(mref, ComplainModel.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ComplainModel, viewholderForAllCompainList>(options) {
            @Override
            protected void onBindViewHolder(@NonNull viewholderForAllCompainList holder, int position, @NonNull ComplainModel model) {

                holder.setDataToView(getApplicationContext(), model);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // change intent
                        Intent p = new Intent(getApplicationContext() , ComplainDetailsActivity.class);
                        p.putExtra("COMPLAIN_MODEL" , model) ;
                        startActivity(p);
                    }
                });


            }

            @NonNull
            @Override
            public viewholderForAllCompainList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View iteamVIew = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_complaints, parent, false);
                final viewholderForAllCompainList viewholders = new viewholderForAllCompainList(iteamVIew);

                return viewholders;
            }
        };

        firebaseRecyclerAdapter.startListening();
        binding.list.setAdapter(firebaseRecyclerAdapter);
    }

}