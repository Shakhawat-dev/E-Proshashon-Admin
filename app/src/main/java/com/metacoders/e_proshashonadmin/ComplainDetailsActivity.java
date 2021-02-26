package com.metacoders.e_proshashonadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.tntkhang.fullscreenimageview.library.FullScreenImageViewActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.metacoders.e_proshashonadmin.Const.Const;
import com.metacoders.e_proshashonadmin.Models.ComplainModel;
import com.metacoders.e_proshashonadmin.Models.EmpModel;
import com.metacoders.e_proshashonadmin.databinding.ActivityComplainDetailsBinding;
import com.metacoders.e_proshashonadmin.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ComplainDetailsActivity extends AppCompatActivity {

    ComplainModel model;
    private ActivityComplainDetailsBinding binding;
    String status  = "" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityComplainDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // recive the model
        model = (ComplainModel) getIntent().getSerializableExtra("COMPLAIN_MODEL");
        status = model.getComplain_status();

        // this model has all the data

        setViews(model);


        loadProfile(model.getEmp_uid());
        loadStatus();

        binding.acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                String comment = binding.commentEd.getText().toString();
                model.setComment(comment);
                model.setComplain_status(status);
                updateTheComplain(model);
            }
        });

        binding.statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position== 0){

                }else {
                    status = parent.getSelectedItem().toString() ;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        binding.cCompalinLink.setText(model.getComplain_ref_link());
        binding.cCompalinType.setText(model.getComplain_type());
        binding.cThanaUpzilla.setText(model.getComplain_thana_upzilla());
        binding.department.setText(model.getEmp_role());
        binding.cCompalinStatus.setText(model.getComplain_status());

        if (!model.getComment().toLowerCase().equals("null")) {
            binding.commentEd.setText(model.getComment());
        }
        // load image
        Glide.with(getApplicationContext())
                .load(model.getAttachments().getImage_1())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.cImage1);
        Glide.with(getApplicationContext())
                .load(model.getAttachments().getImage_2())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.cImage2);
        Glide.with(getApplicationContext())
                .load(model.getAttachments().getImage_3())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.cImage3);


        binding.cImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadImages();

            }
        });
        binding.cImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadImages();
            }
        });
        binding.cImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadImages();
            }
        });

    }

    private void loadProfile(String uid) {
        DatabaseReference mref = FirebaseDatabase.getInstance().getReference(Const.EMPLOYEE_LIST).child(uid);
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                EmpModel model = snapshot.getValue(EmpModel.class);
                Glide.with(getApplicationContext())
                        .load(model.getEmp_pp())
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .into(binding.pp);
                binding.cOfficerName.setText(model.getEmp_name());
                binding.cOfficerNumber.setText(model.getEmp_ph());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadImages() {
        ArrayList<String> images = new ArrayList<>();
        images.add(model.getAttachments().getImage_1());
        images.add(model.getAttachments().getImage_2());
        images.add(model.getAttachments().getImage_3());
        Intent fullImageIntent = new Intent(ComplainDetailsActivity.this, FullScreenImageViewActivity.class);
// uriString is an ArrayList<String> of URI of all images
        fullImageIntent.putExtra(FullScreenImageViewActivity.URI_LIST_DATA, images);
// pos is the position of image will be showned when open
        //  fullImageIntent.putExtra(FullScreenImageViewActivity.IMAGE_FULL_SCREEN_CURRENT_POS, 0);
        startActivity(fullImageIntent);
    }

    private void loadStatus() {
        List<String> deptList = Const.statusList();
        List<String> newDeptList = new ArrayList<>(deptList);
        ArrayAdapter<String> departmentTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, newDeptList);
        departmentTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.statusSpinner.setAdapter(departmentTypeAdapter);
    }


}