package com.metacoders.e_proshashonadmin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.metacoders.e_proshashonadmin.Acitivity.assaginComplain.AssiagnComplainSysAdmin;
import com.metacoders.e_proshashonadmin.Acitivity.assaginComplain.AssiagnComplainUpzilaAdmin;
import com.metacoders.e_proshashonadmin.Acitivity.assaginComplain.Assiagn_ComplainToOfficer;
import com.metacoders.e_proshashonadmin.Acitivity.fillters.Fillter_RegAdmin;
import com.metacoders.e_proshashonadmin.Const.Const;
import com.metacoders.e_proshashonadmin.Models.ComplainModel;
import com.metacoders.e_proshashonadmin.Models.EmpModel;
import com.metacoders.e_proshashonadmin.Models.UserModel;
import com.metacoders.e_proshashonadmin.databinding.ActivityComplainDetailsBinding;
import com.metacoders.e_proshashonadmin.utils.SharedPrefManager;
import com.metacoders.e_proshashonadmin.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ComplainDetailsActivity extends AppCompatActivity {

    ComplainModel model;
    String status = "";
    String userNotificationId = "";
    String department = "";
    Button assaginButton;
    private ActivityComplainDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityComplainDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // recive the model
        model = (ComplainModel) getIntent()
                .getSerializableExtra("COMPLAIN_MODEL");
        status = model
                .getComplain_status();

        // this model has all the data
        setViews(model);
        loadProfile(model.getEmp_uid());
        loadUserProfile(model.getUser_id());
        loadStatus();
        loadAsssginedData();


        binding.acceptBtn.setOnClickListener(v -> {
            //
            String comment = binding.commentEd.getText().toString();
            if (!comment.isEmpty()) {
                String role = department;

                try {
                    String[] b = role.split("_");
                    role = b[1];
                } catch (Exception e) {
                    role = department;

                }
                comment = comment + "\n -- " + role;
            }

            model.setComment(comment);
            model.setComplain_status(status);
            updateTheComplain(model);
        });

        binding.statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                } else {
                    status = parent.getSelectedItem().toString();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.previousBtn.setOnClickListener(v -> finish());

        binding.assaignBtn.setOnClickListener(v -> {

            if (Utils.getRole(getApplicationContext()).equals("regadmin")) {
                Intent p = new Intent(getApplicationContext(), Assiagn_ComplainToOfficer.class);
                p.putExtra("id", model.getPost_id());
                startActivity(p);
            }else if(Utils.getRole(getApplicationContext()).equals("upzadmin")){

                Intent p = new Intent(getApplicationContext(), AssiagnComplainUpzilaAdmin.class);
                p.putExtra("id", model.getPost_id());
                startActivity(p);
            }
            else if(Utils.getRole(getApplicationContext()).equals("sysAdmin")){

                Intent p = new Intent(getApplicationContext(), AssiagnComplainSysAdmin.class);
                p.putExtra("id", model.getPost_id());
                startActivity(p);
            }
            else {
                Toast.makeText(getApplicationContext() , "আপনার এটি অ্যাক্সেস করার ক্ষমতা নেই" , Toast.LENGTH_LONG).show();
            }
            // go to assagin page

        });
    }

    private void loadUserProfile(String user_id) {

        DatabaseReference mref = FirebaseDatabase.getInstance().getReference(Const.USER_PROFILE_REPO).child(user_id);
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    try {
                        UserModel model = snapshot.getValue(UserModel.class);
                        binding.complainName.setText(model.getName() + "");
                        binding.complainerPhone.setText(model.getNumber() + "");
                        userNotificationId = model.getNot_id();
                        binding.gender.setText("" + model.getGender());
                        binding.complainerDiv.setText("" + model.getUser_division());
                        binding.complainerZila.setText("" + model.getUser_district());
                        binding.complainerUpZila.setText("" + model.getUser_thana_upzila());
                        binding.pourosaba.setText("" + model.getUser_union_porusova());
                        if (binding.pourosaba.getText().toString().isEmpty()) {
                            binding.pourosaba.setText("অভিযোগকারী উল্লেখ করেননি");
                        }
                        Log.d("TAG", "onDataChange: " + userNotificationId);
                    } catch (Exception e) {

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Complainer User Not Found!!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateTheComplain(ComplainModel model) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference(Utils.COMPLAIN_REPO);
        database.child(model.getPost_id()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //
                Utils.createNottification(userNotificationId, "Your Complain Was Updated");
                Toast.makeText(getApplicationContext(), "Complain Was Updated !!", Toast.LENGTH_LONG).show();
                finish();

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

            if (model.getComment().contains("--")) {
                String[] s = model.getComment().split("--");
                String by = s[0];
                binding.commentEd.setText(by);
            } else {
                binding.commentEd.setText(model.getComment());
            }

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

        department = SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmp_role();
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
                if (snapshot.exists()) {
                    try {
                        EmpModel model = snapshot.getValue(EmpModel.class);
                        Glide.with(getApplicationContext())
                                .load(model.getEmp_pp())
                                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                .error(R.drawable.ic_baseline_person_24)
                                .placeholder(R.drawable.ic_baseline_person_24)
                                .into(binding.pp);
                        Log.d("TAG", "TAG: " + model.getEmp_pp());
                        binding.cOfficerName.setText(model.getEmp_name());
                        binding.cOfficerNumber.setText(model.getEmp_ph());

                    } catch (Exception e) {

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadAsssginedData() {
        DatabaseReference assBy = FirebaseDatabase.getInstance().getReference(Const.EMPLOYEE_LIST).child(model.getAssignedBy());
        DatabaseReference assTo = FirebaseDatabase.getInstance().getReference(Const.EMPLOYEE_LIST).child(model.getAssignedTo());


        assBy.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    try {
                        EmpModel model = snapshot.getValue(EmpModel.class) ;
                        binding.asaginBy.setText("দায়িত্ব অর্পণ করেছেন : " + model.getEmp_name() + " , " + model.getDepartment_name());
                        if(binding.asaginBy.getText().toString().contains("NULL")){
                            binding.asaginBy.setVisibility(View.GONE);
                            binding.title.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        assTo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    try {
                        EmpModel model = snapshot.getValue(EmpModel.class) ;
                        binding.asaginTo.setText("যার উপরে দায়িত্ব অর্পণ করছে : " + model.getEmp_name() + " , " + model.getDepartment_name());
                        if(binding.asaginTo.getText().toString().contains("NULL")){
                            binding.asaginTo.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void loadComplainerProfile(String uid) {
        DatabaseReference mref = FirebaseDatabase.getInstance().getReference(Const.USER_PROFILE_REPO).child(uid);
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


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