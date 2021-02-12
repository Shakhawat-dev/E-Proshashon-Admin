package com.metacoders.e_proshashonadmin;

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
import com.metacoders.e_proshashonadmin.Const.Const;
import com.metacoders.e_proshashonadmin.Models.ComplainModel;
import com.metacoders.e_proshashonadmin.Models.EmpModel;
import com.metacoders.e_proshashonadmin.databinding.ActivityFillterSysAdminBinding;
import com.metacoders.e_proshashonadmin.databinding.RowEmployeeTypeBinding;
import com.metacoders.e_proshashonadmin.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Fillter_SysAdmin extends AppCompatActivity implements  complainListAdapter.ItemClickListener {
    ValueEventListener valueEventListener = null;
    List<ComplainModel> complainModelList = new ArrayList<>();
    List<EmpModel> employeeList = new ArrayList<>();
    String department = "", upzila = "", role = "" , uid ="";
    complainListAdapter adapter ;

    private ActivityFillterSysAdminBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFillterSysAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new complainListAdapter(complainModelList , getApplicationContext() , this) ;
        binding.complainList.setLayoutManager(new LinearLayoutManager(this));


        // 1 st flood the dpartment chooser

        loadZoneList();

        // on seletected
        binding.departTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                department = parent.getSelectedItem().toString();
                if (position == 1) {
                    // েলা প্রশাসন
                    upzila = "z";
                    loadDistrictRoleList();
                } else if (position == 2) {
                    //"উপজেলা প্রশাসন"

                    loadUpzilaList();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.designationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    //  set the designationSpinner
                    role = parent.getSelectedItem().toString();

                    loadEmpListInSpinner();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // uid , upzila , role
                Log.d("TAG", "onClick: " + complainModelList.size());
                List<ComplainModel> fillteredList  =Utils.FillterComplainModel( uid , upzila , role  , complainModelList) ;
                binding.complainList.setAdapter(new complainListAdapter(fillteredList , getApplicationContext() , Fillter_SysAdmin.this )) ;

            }
        });

        binding.userListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               EmpModel model   = (EmpModel)parent.getItemAtPosition(position)  ;
               uid = model.getEmp_uid();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadEmpListInSpinner() {

        List<EmpModel>fillteredEmpList = Utils.FillterEmpModel(
                department ,
                upzila,
                role,
                employeeList

        ) ;

        ArrayAdapter userListAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                fillteredEmpList);
        userListAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        binding.userListSpinner.setAdapter(userListAdapter);

    }

    private void loadUpzilaList() {

        List<String> list = Const.upozillaType();
        list.remove(1);
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

    private void loadZoneList() {
        ArrayAdapter<String> employeeTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                Const.adminType());
        employeeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.departTypeSpinner.setAdapter(employeeTypeAdapter);
    }

    public void loadComplainList() {

        DatabaseReference mref = FirebaseDatabase.getInstance().getReference("complain_box");

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    ComplainModel complainModel = postSnapshot.getValue(ComplainModel.class);
                    complainModelList.add(complainModel);
                }
                binding.complainList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.complainList.setAdapter(new complainListAdapter(complainModelList , getApplicationContext() , Fillter_SysAdmin.this));
                binding.complainList.setAdapter(adapter);
                mref.removeEventListener(valueEventListener);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };


        mref.addValueEventListener(valueEventListener);


    }

    void loadEmpListData() {
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
        loadComplainList();
        loadEmpListData();
    }

    @Override
    public void onItemClick(ComplainModel model) {

    }
}

