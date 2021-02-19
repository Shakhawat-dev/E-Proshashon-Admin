package com.metacoders.e_proshashonadmin.Acitivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.metacoders.e_proshashonadmin.Adapter.complainListAdapter;
import com.metacoders.e_proshashonadmin.ComplainDetailsActivity;
import com.metacoders.e_proshashonadmin.Const.Const;
import com.metacoders.e_proshashonadmin.Models.ComplainModel;
import com.metacoders.e_proshashonadmin.Models.EmpModel;
import com.metacoders.e_proshashonadmin.databinding.ActivityFillterRegAdminBinding;
import com.metacoders.e_proshashonadmin.utils.SharedPrefManager;
import com.metacoders.e_proshashonadmin.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fillter_UpzilaAdmin extends AppCompatActivity implements complainListAdapter.ItemClickListener {

    ValueEventListener valueEventListener = null;
    List<ComplainModel> complainModelList = new ArrayList<>();
    List<EmpModel> employeeList = new ArrayList<>();
    String department = "", upzila = "", role = "", uid = "" , departmentName ="";
    complainListAdapter adapter;

    private ActivityFillterRegAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFillterRegAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("অ্যাডমিন অনুসন্ধান");

        adapter = new complainListAdapter(complainModelList, getApplicationContext(), this);
        binding.complainList.setLayoutManager(new LinearLayoutManager(this));
        loadUpzilaList();

        // 1 st flood the dpartment chooser


        // on seletected
        binding.departRoleSpinner.setVisibility(View.GONE);
        binding.departRoleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                department = parent.getSelectedItem().toString();
                departmentName = parent.getSelectedItem().toString() ;
                if (position == 1) {
                    // েলা প্রশাসন
                    upzila = "z";
                    departmentName = "জেলা প্রশাসন" ;


                }
                else if(position == 0 ){
                    departmentName = "" ;
                }

                search();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                departmentName = "" ;
                search();
            }
        });

        binding.upzilaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                loadUpzliaRoleList();
                upzila  = SharedPrefManager.getInstance(getApplicationContext())
                        .getUser().getUpzila() ;;
                search();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                loadUpzliaRoleList();
                upzila  = SharedPrefManager.getInstance(getApplicationContext())
                        .getUser().getUpzila() ;;
                search();

            }
        });

        binding.designationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    //  set the designationSpinner
                    role = parent.getSelectedItem().toString();
                    loadEmpListInSpinner();
                } else {
                    role = "";

                }

                search();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                role = "";

            }
        });

        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // uid , upzila , role
                search();
            }
        });

        binding.userListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    EmpModel model = (EmpModel) parent.getItemAtPosition(position);
                    uid = model.getEmp_uid();

                } else {
                    uid = "";
                }
                search();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                uid = "";
            }
        });

    }

    private void search() {
        Log.d("TAG", "onClick: " + complainModelList.size());
        List<ComplainModel> fillteredList = Utils.FillterRegAdminComplainModel(departmentName , uid, upzila, role, complainModelList);
        //   uid ="" ;upzila = "" ; role = ""  ; ;
        binding.complainList.setAdapter(new complainListAdapter(fillteredList, getApplicationContext(), Fillter_UpzilaAdmin.this));

    }

    private void loadEmpListInSpinner() {

        List<EmpModel> fillteredEmpList = Utils.FillterEmpModel(
                department,
                upzila,
                role,
                employeeList

        );

        ArrayAdapter userListAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                fillteredEmpList);
        userListAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        binding.userListSpinner.setAdapter(userListAdapter);

    }

    private void loadUpzilaList() {

        List<String> list = new ArrayList<>();
        String roled = SharedPrefManager.getInstance(getApplicationContext())
                .getUser().getUpzila() ;
        list.add(roled) ;

        ArrayAdapter<String> upozillaListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                list);
        upozillaListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.upzilaSpinner.setAdapter(upozillaListAdapter);
    }

    private void loadUpzliaRoleList() {

        ArrayAdapter<String> upzilaRoleListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                Utils.upzillaDesignationList);
        upzilaRoleListAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        binding.designationSpinner.setAdapter(upzilaRoleListAdapter);

    }

    private void loadDistrictRoleList() {
        ArrayAdapter<String> districtRoleListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                Utils.disrictDesignationList);
        districtRoleListAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        binding.designationSpinner.setAdapter(districtRoleListAdapter);

    }

    private void loadZoneList(List<String> RoleList) {
        ArrayAdapter<String> departmentRoleList = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                RoleList);
        departmentRoleList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.departRoleSpinner.setAdapter(departmentRoleList);

    }

    public void loadComplainList() {

        DatabaseReference mref = FirebaseDatabase.getInstance().getReference("complain_box");

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    ComplainModel complainModel = postSnapshot.getValue(ComplainModel.class);
                   if(complainModel.getComplain_thana_upzilla().equals(SharedPrefManager.getInstance(getApplicationContext())
                            .getUser().getUpzila())){
                       complainModelList.add(complainModel);
                   }
                }
                binding.complainList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.complainList.setAdapter(new complainListAdapter(complainModelList, getApplicationContext(), Fillter_UpzilaAdmin.this));
                binding.complainList.setAdapter(adapter);
                mref.removeEventListener(valueEventListener);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };


        mref.addValueEventListener(valueEventListener);


    }

    public void loadEmpListData() {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("emp_list");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                /*
                loop the data  for the certain number and pass
                 */
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    EmpModel user = postSnapshot.getValue(EmpModel.class);
                    employeeList.add(user);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProfileData();
        loadComplainList();
        loadEmpListData();
    }

    private void loadProfileData() {
                EmpModel model = SharedPrefManager.getInstance(getApplicationContext()).getUser();
                /*
                 we got the String
                 */
                String roleListString = model.getRole_list();
                //roleListString = roleListString.substring(1);
                List<String> roleList = Arrays.asList(roleListString.split(","));
                loadZoneList(roleList);
            }


    @Override
    public void onItemClick(ComplainModel model) {
        Intent p = new Intent(getApplicationContext() , ComplainDetailsActivity.class);
        p.putExtra("COMPLAIN_MODEL" , model) ;
        startActivity(p);
    }
}

