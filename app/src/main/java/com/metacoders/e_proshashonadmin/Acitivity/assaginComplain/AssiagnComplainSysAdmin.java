package com.metacoders.e_proshashonadmin.Acitivity.assaginComplain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.metacoders.e_proshashonadmin.Acitivity.fillters.Fillter_SysAdmin;
import com.metacoders.e_proshashonadmin.Adapter.complainListAdapter;
import com.metacoders.e_proshashonadmin.ComplainDetailsActivity;
import com.metacoders.e_proshashonadmin.Const.Const;
import com.metacoders.e_proshashonadmin.Models.ComplainModel;
import com.metacoders.e_proshashonadmin.Models.EmpModel;
import com.metacoders.e_proshashonadmin.databinding.ActivityAssiagnComplainAdminBinding;
import com.metacoders.e_proshashonadmin.databinding.ActivityAssiagnComplainSysAdminBinding;
import com.metacoders.e_proshashonadmin.utils.SharedPrefManager;
import com.metacoders.e_proshashonadmin.utils.Utils;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AssiagnComplainSysAdmin extends AppCompatActivity {
    private ActivityAssiagnComplainSysAdminBinding binding;
    List<ComplainModel> complainModelList = new ArrayList<>();
    List<EmpModel> employeeList = new ArrayList<>();
    String department = "", upzila = "", role = "", uid = "", emp_notification_id = "", departmentName = "", status = "", complain_type = "";
    String id = "";
    String  orginDepartment = "";
    complainListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAssiagnComplainSysAdminBinding.inflate(getLayoutInflater()) ;
        setContentView(binding.getRoot());

        id = getIntent().getStringExtra("id");

        // 1 st flood the dpartment chooser
        loadZoneList();

        loadComplainType();
        loadStatusList();
        // on seletected
        binding.departTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                department = parent.getSelectedItem().toString();
                if (position == 1) {
                    // েলা প্রশাসন
                    upzila = "z";
                    loadDistrictRoleList();
                    loadEmpRole("z");
                    loadEmpListInSpinner();

                } else if (position == 2) {
                    //"উপজেলা প্রশাসন"

                    loadUpzilaList();
                    loadEmpRole("");
                    loadUpzliaRoleList();
                    loadEmpListInSpinner();

                } else {
                    upzila = "";

                }
                search();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.upzilaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // set the upzlia name
                if (position > 0) {
                    // no load the upzila role list
                    loadUpzliaRoleList();
                    upzila = parent.getSelectedItem().toString();

                } else {
                    upzila = "";
                }
                loadEmpListInSpinner();
                search();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                upzila = "";
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
                loadEmpListInSpinner();
                search();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                orginDepartment = "";
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

                loadEmpListInSpinner();
                search();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

//        binding.searchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // uid , upzila , role
//                search();
//            }
//        });

        binding.acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!id.isEmpty()) {
                    // assign this
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("complain_box")
                            .child(id + "");
                    databaseReference.child("assignedTo").setValue(uid);
                    databaseReference.child("assignedBy").setValue(SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmp_uid() + "")
                            .addOnCompleteListener(task -> {
                                createNotification();
                                Toast.makeText(getApplicationContext(), "Complain Was Assigned.", Toast.LENGTH_LONG).show();
                                finish();
                            });


                }

            }
        });

        binding.userListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    EmpModel model = (EmpModel) parent.getItemAtPosition(position);
                    uid = model.getEmp_uid();
                    emp_notification_id = model.getEmp_not_uid();

                } else {
                    uid = "";
                    emp_notification_id = "";
                }
                search();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                uid = "";
            }
        });

        binding.statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    status = parent.getSelectedItem().toString();

                } else {
                    status = "PENDING";
                }
                search();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // search();
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

    private void loadComplainType() {
        ArrayAdapter<String> upzilaRoleListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                Const.complainType());
        upzilaRoleListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.complainType.setAdapter(upzilaRoleListAdapter);

    }

    private void loadEmpRole(String upzila) {
        List<String> divisionS = new ArrayList<String>(Arrays.asList(Const.divisionList()));
        if (upzila.equals("z")) {
            divisionS.remove(2);
        } else {
            divisionS.remove(1);
        }
        ArrayAdapter roleListAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                divisionS);
        roleListAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        binding.roleSpinner.setAdapter(roleListAdapter);


    }

    private void search() {
        Log.d("TAG", "onClick: " + complainModelList.size());
        List<ComplainModel> fillteredList = Utils.FillterComplainModel(uid, upzila, role, complainModelList,
                status, orginDepartment, complain_type);
        //   uid ="" ;upzila = "" ; role = ""  ; ;
      //  binding.complainList.setAdapter(new complainListAdapter(fillteredList, getApplicationContext(), Fillter_SysAdmin.this));
      //  callConter();
    }

    private void loadEmpListInSpinner() {

        Log.d("EMMMP", ": department -> " + department + " upzila ->" + upzila + " role ->" + role);


        if(department.contains("জেলা প্রশাসকের কার্যালয়")){
            upzila = "জেলা প্রশাসন" ;
        }

        String newDepat =  department ;
        try{
            if(department.contains("_")){
                String[] b = department.split("_") ;
                newDepat = b[1] ;
            }
        }catch ( Exception e ){
            newDepat =  department ;
        }
        String newRole =  role ;

        try{
            if(role.contains("_")){
                String[] c = role.split("_") ;
                newRole = c[0] ;
                Log.d("ED", "loadEmpListInSpinner: " + newRole);
            }
        }catch ( Exception e ){
            newRole =  role ;
        }


        List<EmpModel> fillteredEmpList = Utils.FillterEmpModel(
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

    private void loadUpzilaList() {

        List<String> list = Const.upozillaType();
        list.remove(1);
        ArrayAdapter<String> upozillaListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                list);
        upozillaListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.upzilaSpinner.setAdapter(upozillaListAdapter);
    }

    private void loadStatusList() {
        ArrayAdapter<String> complainTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                Const.statusList());
        complainTypeAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        binding.statusSpinner.setAdapter(complainTypeAdapter);
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
        binding.departTypeSpinner.setSelection(1);
    }



    private void callConter() {


    }

    private void createNotification() {
        //   nottification_id
        try {
            OneSignal.postNotification(new JSONObject("{'contents': {'en':'A Complain Was Assigned To You'}, 'include_player_ids': ['"+ emp_notification_id +"']}"),
                    new OneSignal.PostNotificationResponseHandler() {
                        @Override
                        public void onSuccess(JSONObject response) {
                            Log.e("OneSignalExample", "postNotification Success: " + response.toString());
                        }

                        @Override
                        public void onFailure(JSONObject response) {
                            Log.e("OneSignalExample", "postNotification Failure: " + response.toString());
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void loadEmpListFromFirebase() {
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
     //   loadComplainList();
        loadEmpListFromFirebase();
    }


}