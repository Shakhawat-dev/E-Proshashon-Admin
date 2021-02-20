package com.metacoders.e_proshashonadmin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.metacoders.e_proshashonadmin.Acitivity.AssaignAdmin.assaginZilaAdmin;
import com.metacoders.e_proshashonadmin.Acitivity.Fillter_Employee;
import com.metacoders.e_proshashonadmin.Acitivity.Fillter_RegAdmin;
import com.metacoders.e_proshashonadmin.Acitivity.Fillter_SysAdmin;
import com.metacoders.e_proshashonadmin.Acitivity.Fillter_UpzilaAdmin;
import com.metacoders.e_proshashonadmin.Const.Const;
import com.metacoders.e_proshashonadmin.Models.ComplainModel;
import com.metacoders.e_proshashonadmin.Models.EmpModel;
import com.metacoders.e_proshashonadmin.databinding.ActivityAdminDashboardBinding;
import com.metacoders.e_proshashonadmin.utils.SharedPrefManager;
import com.metacoders.e_proshashonadmin.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminDashboardActivity extends AppCompatActivity {

    int rejectCount = 0, acceptCount = 0, completedCount = 0, allCount = 0;
    private MaterialCardView mAllComplainCard, mCreateAdminCard;
    private ActivityAdminDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.allComplainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.getRole(getApplicationContext()).equals("regadmin")) {
                    Intent intent = new Intent(getApplicationContext(), Fillter_RegAdmin.class);
                    startActivity(intent);

                } else if (Utils.getRole(getApplicationContext()).equals("employee")) {

                    Intent intent = new Intent(getApplicationContext(), Fillter_Employee.class);
                    startActivity(intent);
                } else if (Utils.getRole(getApplicationContext()).equals("upzadmin")) {
                    Intent intent = new Intent(getApplicationContext(), Fillter_UpzilaAdmin.class);
                    startActivity(intent);
                } else {

                    Intent intent = new Intent(getApplicationContext(), Fillter_SysAdmin.class);
                    startActivity(intent);
                }
            }
        });

        binding.createZilaAdminCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), assaginZilaAdmin.class);
                startActivity(intent);
            }
        });

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //now what to do is
                // clear the shared preferences

                SharedPrefManager.getInstance(getApplicationContext())
                        .logout();

                // nbow the get the login

                Intent p = new Intent(getApplicationContext(), LoginActivity.class);
                p.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(p);


            }
        });
        binding.zilaAdminView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Fillter_RegAdmin.class);
                startActivity(intent);
            }
        });

        binding.createEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), EmpCreateActivity.class);
                startActivity(intent);
            }
        });

        decideWhatToShow();
    }


    public void decideWhatToShow() {
        /*
         * load the profile data
         * then decide what to show what not
         *
         * 1st hide all
         *
         */
//        binding.allComplainCard.setVisibility(View.GONE);
//        binding.allCreateAdminCard.setVisibility(View.GONE);
//        binding.createEmp.setVisibility(View.GONE);
//        binding.zilaAdminView.setVisibility(View.GONE);

        EmpModel model = SharedPrefManager.getInstance(getApplicationContext())
                .getUser();

        if (model != null) {
            String data = model.getEmp_role();
            List<String> role = Arrays.asList(data.split("_"));
            String ROLESTR = role.get(0);
            Log.d("TAG", "decideWhatToShow: " + role.get(0));

            /*
                role.get(0) has the actual role  emp or reg_admin , sys_admin

             */
            // decide thte data
            if (ROLESTR.equals("regadmin")) {
                // regonal admin like -> zila admin
                binding.createZilaAdminCard.setVisibility(View.GONE);
                binding.createEmp.setVisibility(View.GONE);
                loadCounterForRegAdmin();

            } else if (ROLESTR.equals("upzadmin")) {
                binding.createZilaAdminCard.setVisibility(View.GONE);
                binding.createEmp.setVisibility(View.GONE);
                loadCounterForUpzAdmin();
            } else if (ROLESTR.equals("employee")) {
                // plain employee like ->  employee
                binding.createZilaAdminCard.setVisibility(View.GONE);
                binding.createEmp.setVisibility(View.GONE);
                binding.zilaAdminView.setVisibility(View.GONE);
                loadCounterForEmp();
            } else {
                binding.zilaAdminView.setVisibility(View.GONE);
                loadCounterForSysAdmin();
            }

        }


    }


    public void loadCounterForSysAdmin() {
        List<ComplainModel> complainModelList = new ArrayList<>();

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(Const.COMPLAIN_REPO);
        complainModelList.clear();
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                /*
                loop the data  for the certain number and pass
                 */
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    ComplainModel complainModel = postSnapshot.getValue(ComplainModel.class);
                    // replce with real uid
                    //* counter
                    if (complainModel.getComplain_status().equals("ACCEPTED")) {
                        acceptCount++;
                    } else if (complainModel.getComplain_status().equals("REJECTED")) {
                        rejectCount++;
                    } else if (complainModel.getComplain_status().equals("COMPLETED")) {
                        completedCount++;
                    }

                    complainModelList.add(complainModel);


                }

                binding.tottalPostCount.setText(snapshot.getChildrenCount() + "");
                binding.acceptedPostCount.setText(acceptCount + "");
                binding.completedPostCount.setText(completedCount + "");
                binding.rejectedPostCount.setText(rejectCount + "");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void loadCounterForRegAdmin() { //AKA ZIlA ADMIN
        List<ComplainModel> complainModelList = new ArrayList<>();
        String roleListStr = SharedPrefManager.getInstance(getApplicationContext()).getUser().getRole_list();
        List<String> departmentList = Arrays.asList(roleListStr.split(","));


        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(Const.COMPLAIN_REPO);
        complainModelList.clear();
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                /*
                loop the data  for the certain number and pass
                 */
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    ComplainModel complainModel = postSnapshot.getValue(ComplainModel.class);
                    // replce with real uid
                    //* counter
                    for (int i = 1; i < departmentList.size(); i++) {

                        if (complainModel.getComplain_status().equals("ACCEPTED")
                                && complainModel.getComplain_officer_department_name().equals(departmentList.get(i))) {
                            acceptCount++;
                            complainModelList.add(complainModel);
                        } else if (complainModel.getComplain_status().equals("REJECTED")
                                && complainModel.getComplain_officer_department_name().equals(departmentList.get(i))) {
                            rejectCount++;
                            complainModelList.add(complainModel);
                        } else if (complainModel.getComplain_status().equals("COMPLETED")
                                && complainModel.getComplain_officer_department_name().equals(departmentList.get(i))) {
                            completedCount++;
                            complainModelList.add(complainModel);
                        }
                    }
                }

                binding.tottalPostCount.setText(complainModelList.size() + "");
                binding.acceptedPostCount.setText(acceptCount + "");
                binding.completedPostCount.setText(completedCount + "");
                binding.rejectedPostCount.setText(rejectCount + "");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void loadCounterForUpzAdmin() {
        List<ComplainModel> complainModelList = new ArrayList<>();
        String thana = Utils.getRole(getApplicationContext());
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(Const.COMPLAIN_REPO);
        complainModelList.clear();
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                /*
                loop the data  for the certain number and pass
                 */
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    ComplainModel complainModel = postSnapshot.getValue(ComplainModel.class);
                    // replce with real uid
                    //* counter
                    if (complainModel.getComplain_status().equals("ACCEPTED") && complainModel.getComplain_thana_upzilla().equals(
                            thana)) {
                        acceptCount++;
                    } else if (complainModel.getComplain_status().equals("REJECTED")
                            && complainModel.getComplain_thana_upzilla().equals(
                            thana)) {
                        rejectCount++;
                    } else if (complainModel.getComplain_status().equals("COMPLETED")
                            && complainModel.getComplain_thana_upzilla().equals(
                            thana)) {
                        completedCount++;
                    }

                    complainModelList.add(complainModel);


                }

                binding.tottalPostCount.setText((acceptCount + completedCount + rejectCount) + "");
                binding.acceptedPostCount.setText(acceptCount + "");
                binding.completedPostCount.setText(completedCount + "");
                binding.rejectedPostCount.setText(rejectCount + "");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void loadCounterForEmp() {
        List<ComplainModel> complainModelList = new ArrayList<>();
        String uid = SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmp_uid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(Const.COMPLAIN_REPO);
        complainModelList.clear();
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                /*
                loop the data  for the certain number and pass
                 */
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    ComplainModel complainModel = postSnapshot.getValue(ComplainModel.class);
                    // replce with real uid
                    //* counter
                    if (complainModel.getComplain_status().equals("ACCEPTED") && complainModel.getEmp_uid().equals(
                            uid)) {
                        acceptCount++;
                    } else if (complainModel.getComplain_status().equals("REJECTED")
                            && complainModel.getEmp_uid().equals(
                            uid)) {
                        rejectCount++;
                    } else if (complainModel.getComplain_status().equals("COMPLETED")
                            && complainModel.getEmp_uid().equals(
                            uid)) {
                        completedCount++;
                    }

                    complainModelList.add(complainModel);


                }

                binding.tottalPostCount.setText((acceptCount + completedCount + rejectCount) + "");
                binding.acceptedPostCount.setText(acceptCount + "");
                binding.completedPostCount.setText(completedCount + "");
                binding.rejectedPostCount.setText(rejectCount + "");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


}