package com.metacoders.e_proshashonadmin.Acitivity.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.metacoders.e_proshashonadmin.Models.EmpModel;
import com.metacoders.e_proshashonadmin.R;
import com.metacoders.e_proshashonadmin.databinding.ActivityEmpProfileBinding;
import com.metacoders.e_proshashonadmin.utils.SharedPrefManager;

public class EmpProfile extends AppCompatActivity {
    EmpModel model;
    String imagePath, imageUrl = "NULL";
    StorageReference imageRef;
    Uri imageUri = null;
    private ActivityEmpProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmpProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // model = (EmpModel) SharedPrefManager.getInstance(getApplicationContext()).getUser();


        binding.update.setOnClickListener(v -> {
            if (model != null) {
                Intent p = new Intent(getApplicationContext(), EditOwnProfile.class);
                p.putExtra("MODEL", model);
                startActivity(p);
            }
        });

    }

    private void loadProfile() {
        ProgressDialog dialog = new ProgressDialog(EmpProfile.this);
        dialog.setMessage("Loading Profile...");
        dialog.show();
        dialog.setCancelable(false);
        DatabaseReference mref = FirebaseDatabase.getInstance().getReference("emp_list").child(SharedPrefManager.getInstance(getApplicationContext()
        ).getUser().getEmp_uid());

        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dialog.dismiss();
                if (snapshot.exists()) {
                    EmpModel user = snapshot.getValue(EmpModel.class);
                    model = user;
                    setView(user);
                    Gson gson = new Gson();
                    String data = gson.toJson(user);
                    SharedPrefManager.getInstance(getApplicationContext())
                            .userLogin(data);

                } else {
                    Toast.makeText(getApplicationContext(), "Error !!Profile Not Found!!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Error !!" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setView(EmpModel model) {
        binding.email.setText(model.getEmp_mail());
        binding.mobile.setText(model.getEmp_ph());
        binding.name.setText(model.getEmp_name());
        Glide.with(getApplicationContext())
                .load(model.getEmp_pp())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.profilePic);

        imageUrl = model.getEmp_pp();

        binding.adminTypeSpinner.setText(model.getDepartment());
        if (model.getUpzila().contains("z")) {
            binding.upozillaSpinner.setText("জেলা");
        } else {
            binding.upozillaSpinner.setText(model.getUpzila());
        }
        binding.positionList.setText(model.getDepartment_name());
        if (model.getEmp_role().contains("_")) {
            String[] a = model.getEmp_role().split("_");
            binding.roleSpinner.setText(a[1]);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProfile();
    }
}