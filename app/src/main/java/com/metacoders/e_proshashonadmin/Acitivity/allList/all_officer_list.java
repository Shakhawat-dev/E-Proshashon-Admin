package com.metacoders.e_proshashonadmin.Acitivity.allList;

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
import com.metacoders.e_proshashonadmin.Acitivity.profile.EditEmpProfile;
import com.metacoders.e_proshashonadmin.Adapter.EmployeeListAdapter;
import com.metacoders.e_proshashonadmin.Const.Const;
import com.metacoders.e_proshashonadmin.Models.EmpModel;
import com.metacoders.e_proshashonadmin.databinding.ActivityAllOfficerListBinding;
import com.metacoders.e_proshashonadmin.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class all_officer_list extends AppCompatActivity implements EmployeeListAdapter.ItemClickListener {

    List<EmpModel> employeeList = new ArrayList<>();
    String upzila = "", department = "", orginDepartment = "", role = "";
    private ActivityAllOfficerListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllOfficerListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.userList.setLayoutManager(new LinearLayoutManager(this));
        loadZoneList();
        binding.departTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                department = parent.getSelectedItem().toString();
                if (position == 1) {
                    // েলা প্রশাসন
                    upzila = "z";
                    loadDistrictRoleList();
                    binding.upzilaSpinner.setAdapter(null);
                    loadEmpRole("z");
                    //   loadEmpListInSpinner();

                } else if (position == 2) {
                    //"উপজেলা প্রশাসন"

                    loadUpzilaList();
                    loadEmpRole("");
                    loadUpzliaRoleList();
                    //  loadEmpListInSpinner();

                } else {
                    upzila = "";

                }
                 search();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {

                    orginDepartment = parent.getSelectedItem().toString();
                    String[] a = orginDepartment.split("_");

                    //orginDepartment = a[0];
                } else {
                    orginDepartment = "";
                }
                Log.d("UPZILA", "onItemSelected: " + upzila);
                if (upzila.equals("z")) {
                    loadDistrictRoleList();
                } else {
                    loadUpzliaRoleList();
                }
                //  loadEmpListInSpinner();
                 search();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                orginDepartment = "";
            }
        });
        binding.upzilaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // set the upzlia name
                if (position > 0) {

                    loadUpzliaRoleList();
                    upzila = parent.getSelectedItem().toString();

                } else {
                    upzila = "";
                }
                //   loadEmpListInSpinner();
                 search();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                upzila = "";
            }
        });
        binding.designationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                role = parent.getSelectedItem().toString();
                if (role.equals("নির্বাচন করুন")) {
                    //  set the designationSpinner
                    role = "";

                }

                    search();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

    }

    private void loadUpzliaRoleList() {
        //  Toast.makeText(getApplicationContext(), orginDepartment + " ", Toast.LENGTH_SHORT).show();
        List<String> newlist = new ArrayList<>();
        List<String> upzilaList = Arrays.asList(Utils.upzillaDesignationList);

        for (String roleNmae : upzilaList) {
            if (roleNmae.contains(orginDepartment)) {
                newlist.add(roleNmae);
            }
        }

        ArrayAdapter<String> upzilaRoleListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item
                , newlist
        );
        upzilaRoleListAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        binding.designationSpinner.setAdapter(upzilaRoleListAdapter);

    }

    private void loadDistrictRoleList() {

        List<String> newlist = new ArrayList<>();

        List<String> zill_list = Arrays.asList(Utils.disrictDesignationList);

        for (String roleNmae : zill_list) {
            //   Log.d("TAGROLE", "loadDistrictRoleList: " + roleNmae);
            if (roleNmae.contains(orginDepartment)) {
                newlist.add(roleNmae);
                //  Log.d("TAGROLE", "MATCHED: " + roleNmae);
            }
        }
        ArrayAdapter<String> districtRoleListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                newlist);
        districtRoleListAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        binding.designationSpinner.setAdapter(districtRoleListAdapter);

    }

    private void loadZoneList() {
        ArrayAdapter<String> employeeTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                Const.adminType());
        employeeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.departTypeSpinner.setAdapter(employeeTypeAdapter);
    }

    private void loadUpzilaList() {

        List<String> list = Const.upozillaType();
        list.remove(1);
        ArrayAdapter<String> upozillaListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                list);
        upozillaListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.upzilaSpinner.setAdapter(upozillaListAdapter);
    }

    public void loadUserList() {
        employeeList.clear();
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

                binding.userList.setAdapter(new EmployeeListAdapter(employeeList, getApplicationContext(), all_officer_list.this));
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadEmpRole(String upzila) {
        List<String> divisionS = new ArrayList<String>(Arrays.asList(Const.divisionList()));
        if (upzila.equals("z")) {
            divisionS.remove(2);
        } else {
            divisionS.remove(1);
        }
        ArrayAdapter<String> roleListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                divisionS);
        roleListAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        binding.roleSpinner.setAdapter(roleListAdapter);


    }

    private void search() {

        Log.d("EMP", ": " + department + " ->" + upzila + " " + role);

        List<EmpModel> fillteredEmpList = Utils.FillterEmpModel(
                department,
                upzila,
                role,
                employeeList
        );
        fillteredEmpList.remove(0);

        binding.userList.setAdapter(new EmployeeListAdapter(fillteredEmpList, getApplicationContext(), all_officer_list.this));

    }


    @Override
    protected void onStart() {
        super.onStart();
        loadUserList();
    }

    @Override
    public void onItemClick(EmpModel model) {
            Intent p =new Intent(getApplicationContext() , EditEmpProfile.class);
            p.putExtra("MODEL" , model) ;
            startActivity(p);
    }
}