package com.metacoders.e_proshashonadmin;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.metacoders.e_proshashonadmin.Models.ComplainModel;
import com.metacoders.e_proshashonadmin.databinding.ActivityComplainDetailsBinding;
import com.metacoders.e_proshashonadmin.utils.Utils;

public class ComplainDetailsActivity extends AppCompatActivity {

    ComplainModel model;
    private ActivityComplainDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityComplainDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // recive the model
        model = (ComplainModel) getIntent().getSerializableExtra("COMPLAIN_MODEL");

        // this model has all the data

        setViews(model);

        binding.acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                String comment = binding.commentEd.getText().toString();
                model.setComment(comment);
                //TODO assert Commplian Type
                updateTheComplain(model);
            }
        });

    }

    private void updateTheComplain(ComplainModel model) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference(Utils.COMPLAIN_REPO);
        database.child(model.getPost_id()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //
                Toast.makeText(getApplicationContext(), "Complain Was Updated !!", Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void setViews(ComplainModel model) {
        binding.cDivision.setText(model.getComplain_division());
        binding.cDistrict.setText(model.getComplain_district());
        binding.cComplainDesc.setText(model.getComplain_desc());
        binding.cCompalinType.setText(model.getComplain_type());

    }


}