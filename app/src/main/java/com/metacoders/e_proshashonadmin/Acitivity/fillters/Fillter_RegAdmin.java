package com.metacoders.e_proshashonadmin.Acitivity.fillters;

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

public class Fillter_RegAdmin extends AppCompatActivity implements complainListAdapter.ItemClickListener {

    ValueEventListener valueEventListener = null;
    List<ComplainModel> complainModelList = new ArrayList<>();
    List<EmpModel> employeeList = new ArrayList<>();
    String department = "", upzila = "", role = "", uid = "", departmentName = "", status = "", complain_type = "";
    complainListAdapter adapter;

    private ActivityFillterRegAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFillterRegAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("জেলা অ্যাডমিন অনুসন্ধান");


    }


    private void setView() {
        adapter = new complainListAdapter(complainModelList, getApplicationContext(), this);
        LinearLayoutManager manager = new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , true);
        manager.setStackFromEnd(true);
        binding.complainList.setLayoutManager(manager);

        loadStatusList();
        loadComplainType();
        // 1 st flood the dpartment chooser


        // on seletected
        binding.departRoleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                department = parent.getSelectedItem().toString();
                departmentName = parent.getSelectedItem().toString();
                if (position == 1) {
                    // েলা প্রশাসন
                    upzila = "z";
                    //  departmentName = "জেলা প্রশাসন";
                    departmentName = parent.getSelectedItem().toString();
                    search();
                } else if (position == 0) {
                    String selectedTExt = parent.getSelectedItem().toString();
                    if (selectedTExt.equals("নির্বাচন করুন")) {
                        departmentName = "";
                        department = "";
                        search();
                    } else {
                        departmentName = selectedTExt;
                    }

                }
                loadUpzilaList();
                loadUpzliaRoleList();

                //clear
                role = "";


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                departmentName = "";
                search();
            }
        });

        binding.upzilaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // set the upzlia name
                if (position != 0) {
                    // no load the upzila role list
                    loadUpzliaRoleList();
                    upzila = parent.getSelectedItem().toString();

                } else {
                    upzila = "";
                }
                search();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                upzila = "";

            }
        });

        binding.designationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                //  set the designationSpinner
                role = parent.getSelectedItem().toString();
                if (role.isEmpty()) {
                    role = "";
                } else {
                    loadEmpListInSpinner();
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

        binding.statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    status = parent.getSelectedItem().toString();
                } else {
                    status = "";
                }
                search();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // search();
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

        binding.complainType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {

                    complain_type = parent.getSelectedItem().toString();
                } else {
                    complain_type = "";
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
        if (departmentName.contains("জেলা প্রশাসকের কার্যালয়")) {
            upzila = "জেলা প্রশাসন";
        }

        List<ComplainModel> fillteredList = Utils.FillterRegAdminComplainModel(departmentName, uid, upzila, role, complainModelList
                , complain_type, status);
        //   uid ="" ;upzila = "" ; role = ""  ; ;
        Log.d("TAG", "onClick: " + fillteredList.size());
        binding.complainList.setAdapter(new complainListAdapter(fillteredList, getApplicationContext(), Fillter_RegAdmin.this));

    }

    private void loadEmpListInSpinner() {

        if (department.contains("জেলা প্রশাসকের কার্যালয়")) {
            upzila = "জেলা প্রশাসন";
        }

        String newDepat = departmentName;
        try {
            if (departmentName.contains("_")) {
                String[] b = departmentName.split("_");
                newDepat = b[1];
            }
        } catch (Exception e) {
            newDepat = departmentName;
        }
        String newRole = role;
        Log.d("ED", "loadEmpListInSpinner: " + role);
        try {
            if (role.contains("_")) {
                String[] c = role.split("_");
                newRole = c[0];
            }
        } catch (Exception e) {
            newRole = role;
        }


        List<EmpModel> fillteredEmpList = Utils.FillterEmpModelForREGAdmin(
                newDepat,
                upzila,
                newRole,
                employeeList

        );

        ArrayAdapter userListAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                fillteredEmpList);
        userListAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        binding.userListSpinner.setAdapter(userListAdapter);

    }


    private void loadStatusList() {
        ArrayAdapter<String> complainTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                Const.statusList());
        complainTypeAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        binding.statusSpinner.setAdapter(complainTypeAdapter);
        binding.statusSpinner.setSelection(getIntent().getIntExtra("pos", 0));

    }

    private void loadUpzilaList() {

        List<String> list = Const.upozillaType();
        list.remove(1);
        ArrayAdapter<String> upozillaListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                list);
        upozillaListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.upzilaSpinner.setAdapter(upozillaListAdapter);
    }

    private void loadComplainType() {
        ArrayAdapter<String> upzilaRoleListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                Const.complainType());
        upzilaRoleListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.complainType.setAdapter(upzilaRoleListAdapter);

    }

    private void loadUpzliaRoleList() {

        //.makeText(getApplicationContext(), departmentName, Toast.LENGTH_SHORT).show();
        List<String> newlist = new ArrayList<>();
        List<String> upzilaList = Arrays.asList(Utils.upzillaDesignationList);

        String newDepat = departmentName;
        try {
            if (departmentName.contains("_")) {
                String[] b = departmentName.split("_");
                newDepat = b[1];
            }
        } catch (Exception e) {
            newDepat = departmentName;
        }


        for (String roleNmae : upzilaList) {
            Log.d("TAG", "loadUpzliaRoleList: role ->  " + roleNmae + "s-> " + newDepat);
            if (roleNmae.contains(newDepat)) {
                newlist.add(roleNmae);
            }
        }
        ArrayAdapter<String> upzilaRoleListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                newlist);
        upzilaRoleListAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);


        binding.designationSpinner.setAdapter(upzilaRoleListAdapter);

    }

//    private void loadDistrictRoleList() {
//        ArrayAdapter<String> districtRoleListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
//                Utils.disrictDesignationList);
//        districtRoleListAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
//        binding.designationSpinner.setAdapter(districtRoleListAdapter);
//
//    }

    private void loadZoneList(List<String> RoleList) {
        List<String> newList = new ArrayList<String>(RoleList);
        for (String item : RoleList) {
            if (item.isEmpty()) {
                newList.remove(item);
            }
        }


        ArrayAdapter<String> departmentRoleList = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                newList);
        departmentRoleList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.departRoleSpinner.setAdapter(departmentRoleList);

        if (newList.size() > 1) {
            binding.departRoleSpinner.setSelection(1);
        }
    }

    public void loadComplainList() {

        DatabaseReference mref = FirebaseDatabase.getInstance().getReference("complain_box");
        complainModelList.clear();
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    ComplainModel complainModel = postSnapshot.getValue(ComplainModel.class);
                    complainModelList.add(complainModel);
                }
            //    binding.complainList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                //  binding.complainList.setAdapter(new complainListAdapter(complainModelList, getApplicationContext(), Fillter_RegAdmin.this));
                //  binding.complainList.setAdapter(adapter);

                mref.removeEventListener(valueEventListener);
                search();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };


        mref.addValueEventListener(valueEventListener);


    }

    public void loadEmpListData() {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("emp_list");
        employeeList.clear();
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


        setView();
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
        if (!roleListString.contains("জেলা প্রশাসকের কার্যালয়")) {
            List<String> roleList = Arrays.asList(roleListString.split(","));
            loadZoneList(roleList);
        } else {
            loadZoneList(Arrays.asList(Utils.disrictDesignationList));
        }


    }


    @Override
    public void onItemClick(ComplainModel model) {
        Intent p = new Intent(getApplicationContext(), ComplainDetailsActivity.class);
        p.putExtra("COMPLAIN_MODEL", model);
        startActivity(p);
    }
}

