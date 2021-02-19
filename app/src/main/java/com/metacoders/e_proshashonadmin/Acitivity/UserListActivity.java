package com.metacoders.e_proshashonadmin.Acitivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.metacoders.e_proshashonadmin.Adapter.userAdapter;
import com.metacoders.e_proshashonadmin.ComplainDetailsActivity;
import com.metacoders.e_proshashonadmin.Const.Const;
import com.metacoders.e_proshashonadmin.Models.UserModel;
import com.metacoders.e_proshashonadmin.databinding.ActivityUserListBinding;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity implements userAdapter.ItemClickListener {

    List<UserModel> userModelList = new ArrayList<>();
    private ActivityUserListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.list.setLayoutManager(new LinearLayoutManager(this));
        loadUserList();
    }

    void loadUserList() {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(Const.USER_PROFILE_REPO);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                /*
                loop the data  for the certain number and pass
                 */
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    UserModel user = postSnapshot.getValue(UserModel.class);
                    // replce with real uid
                    userModelList.add(user);
                }

                binding.list.setAdapter(new userAdapter(userModelList, getApplicationContext(), UserListActivity.this));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(UserModel model) {

    }
}