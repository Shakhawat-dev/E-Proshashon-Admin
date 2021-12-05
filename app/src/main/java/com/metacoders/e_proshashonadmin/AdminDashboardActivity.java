package com.metacoders.e_proshashonadmin;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.metacoders.e_proshashonadmin.Acitivity.AssaignAdmin.EmpCreateActivity;
import com.metacoders.e_proshashonadmin.Acitivity.AssaignAdmin.assaginZilaAdmin;
import com.metacoders.e_proshashonadmin.Acitivity.AssaignedListForAdminsActivity;
import com.metacoders.e_proshashonadmin.Acitivity.allList.all_officer_list;
import com.metacoders.e_proshashonadmin.Acitivity.fillters.Fillter_Employee;
import com.metacoders.e_proshashonadmin.Acitivity.fillters.Fillter_RegAdmin;
import com.metacoders.e_proshashonadmin.Acitivity.fillters.Fillter_SysAdmin;
import com.metacoders.e_proshashonadmin.Acitivity.fillters.Fillter_UpzilaAdmin;
import com.metacoders.e_proshashonadmin.Acitivity.profile.EmpProfile;
import com.metacoders.e_proshashonadmin.Const.Const;
import com.metacoders.e_proshashonadmin.Models.ComplainModel;
import com.metacoders.e_proshashonadmin.Models.EmpModel;
import com.metacoders.e_proshashonadmin.databinding.ActivityAdminDashboardBinding;
import com.metacoders.e_proshashonadmin.utils.SharedPrefManager;
import com.metacoders.e_proshashonadmin.utils.Utils;
import com.onesignal.OSDeviceState;
import com.onesignal.OneSignal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminDashboardActivity extends AppCompatActivity {

    int rejectCount = 0, acceptCount = 0, completedCount = 0, allCount = 0, inquiryCount = 0, pendingCount = 0;
    private MaterialCardView mAllComplainCard, mCreateAdminCard;
    private ActivityAdminDashboardBinding binding;
    private FirebaseAuth mauth;
    private DatabaseReference mdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        updateYourId();

        binding.assiagnComplainCard.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Fillter_Employee.class);
            startActivity(intent);
        });

        

        binding.pendingBtn.setOnClickListener(v -> {

            goToMethod(1);
        });

        binding.pending1Btn.setOnClickListener(v -> {
            goToMethod(4);
        });

        binding.acceptedBtn.setOnClickListener(v -> {
            goToMethod(2);
        });

        binding.totalBtn.setOnClickListener(v -> {
            goToMethod(0);
        });

        binding.cancelBtn.setOnClickListener(v -> {
            goToMethod(3);
        });
        binding.successBtn.setOnClickListener(v -> {
            goToMethod(5);
        });


        binding.allComplainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMethod(0);
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
                loagOut();

            }
        });
        binding.zilaAdminView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Fillter_RegAdmin.class);
                startActivity(intent);
            }
        });

        binding.assiagnFromComplainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AssaignedListForAdminsActivity.class);
                startActivity(intent);
            }
        });

        binding.offlicerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), all_officer_list.class);
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

        //decideWhatToShow();

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }
                })
                .onSameThread()
                .check();
    }

    private void goToMethod(int post) {
        switch (Utils.getRole(getApplicationContext())) {
            case "regadmin": {
                Intent intent = new Intent(getApplicationContext(), Fillter_RegAdmin.class);
                intent.putExtra("pos", post);
                startActivity(intent);

                break;
            }
            case "employee": {

                Intent intent = new Intent(getApplicationContext(), Fillter_Employee.class);
                intent.putExtra("pos", post);
                startActivity(intent);
                break;
            }
            case "upzadmin": {
                Intent intent = new Intent(getApplicationContext(), Fillter_UpzilaAdmin.class);
                intent.putExtra("pos", post);
                startActivity(intent);
                break;
            }
            default: {

                Intent intent = new Intent(getApplicationContext(), Fillter_SysAdmin.class);
                intent.putExtra("pos", post);
                startActivity(intent);
                break;
            }
        }
    }



    private void updateYourId() {
        OSDeviceState device = OneSignal.getDeviceState();

        String userId = device.getUserId();

        if (userId != null) {

            String id = SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmp_uid();
            mdatabase = FirebaseDatabase.getInstance().getReference(Const.EMPLOYEE_LIST).child(id);
            mdatabase.child("emp_not_uid").setValue(userId);
        }


    }

    private void loagOut() {
        SharedPrefManager.getInstance(getApplicationContext())
                .logout();

        // nbow the get the login

        Intent p = new Intent(getApplicationContext(), LoginActivity.class);
        p.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(p);

    }


    public void decideWhatToShow() {
        rejectCount = 0;
        acceptCount = 0;
        completedCount = 0;
        allCount = 0;
        pendingCount = 0;
        inquiryCount = 0;

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
            // decide the data
            binding.assiagnComplainCard.setVisibility(View.GONE);

            switch (ROLESTR) {

                case "regadmin":
                    // regonal admin like -> zila admin
                    binding.createZilaAdminCard.setVisibility(View.VISIBLE);
                    binding.createEmp.setVisibility(View.VISIBLE);
                    binding.assiagnComplainCard.setVisibility(View.VISIBLE);
                    binding.offlicerView.setVisibility(View.VISIBLE);
                    loadCounterForRegAdmin();

                    break;
                case "upzadmin":
                    binding.createZilaAdminCard.setVisibility(View.GONE);
                    binding.createEmp.setVisibility(View.VISIBLE);
                    binding.offlicerView.setVisibility(View.VISIBLE);
                    binding.assiagnComplainCard.setVisibility(View.VISIBLE);
                    binding.assiagnFromComplainCard.setVisibility(View.VISIBLE);
                    loadCounterForUpzAdmin();
                    break;
                case "employee":
                    // plain employee like ->  employee
                    binding.createZilaAdminCard.setVisibility(View.GONE);
                    binding.createEmp.setVisibility(View.GONE);
                    binding.zilaAdminView.setVisibility(View.GONE);
                    binding.offlicerView.setVisibility(View.GONE);
                    binding.assiagnComplainCard.setVisibility(View.VISIBLE);
                    binding.assiagnFromComplainCard.setVisibility(View.GONE);
                    loadCounterForEmp();
                    break;
                default:
                    binding.zilaAdminView.setVisibility(View.GONE);
                    loadCounterForSysAdmin();
                    break;
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
                    switch (complainModel.getComplain_status()) {
                        case "ACCEPTED":
                            acceptCount++;
                            break;
                        case "REJECTED":
                            rejectCount++;
                            break;
                        case "COMPLETED":
                            completedCount++;
                            break;
                        case "PENDING":
                            pendingCount++;
                            break;
                        case "INQUIRY":
                            inquiryCount++;
                            break;
                    }

                    complainModelList.add(complainModel);


                }

                binding.tottalPostCount.setText(snapshot.getChildrenCount() + "");
                binding.acceptedPostCount.setText(acceptCount + "");
                binding.completedPostCount.setText(completedCount + "");
                binding.rejectedPostCount.setText(rejectCount + "");
                binding.pendingPostCount.setText(pendingCount + "");
                binding.inQuiryPostCount.setText(inquiryCount + "");

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
                        } else if (complainModel.getComplain_status().equals("PENDING")
                                && complainModel.getComplain_officer_department_name().equals(departmentList.get(i))) {
                            pendingCount++;
                            complainModelList.add(complainModel);
                        } else if (complainModel.getComplain_status().equals("INQUIRY")
                                && complainModel.getComplain_officer_department_name().equals(departmentList.get(i))) {
                            inquiryCount++;
                            complainModelList.add(complainModel);
                        }
                    }
                }

                binding.tottalPostCount.setText((acceptCount + inquiryCount + completedCount + rejectCount + pendingCount) + "");
                binding.acceptedPostCount.setText(acceptCount + "");
                binding.completedPostCount.setText(completedCount + "");
                binding.rejectedPostCount.setText(rejectCount + "");
                binding.pendingPostCount.setText(pendingCount + "");
                binding.inQuiryPostCount.setText(inquiryCount + "");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void loadCounterForUpzAdmin() {
        List<ComplainModel> complainModelList = new ArrayList<>();
        String thana = SharedPrefManager.getInstance(getApplicationContext()).getUser().getUpzila();
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
                    } else if (complainModel.getComplain_status().equals("PENDING")
                            && complainModel.getComplain_thana_upzilla().equals(
                            thana)) {
                        pendingCount++;
                    } else if (complainModel.getComplain_status().equals("INQUIRY")
                            && complainModel.getComplain_thana_upzilla().equals(
                            thana)) {
                        inquiryCount++;
                    }

                    complainModelList.add(complainModel);


                }

                binding.tottalPostCount.setText((acceptCount + inquiryCount + completedCount + pendingCount + rejectCount) + "");
                binding.acceptedPostCount.setText(acceptCount + "");
                binding.completedPostCount.setText(completedCount + "");
                binding.rejectedPostCount.setText(rejectCount + "");
                binding.pendingPostCount.setText(pendingCount + "");
                binding.inQuiryPostCount.setText(inquiryCount + "");


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
                    } else if (
                            complainModel.getComplain_status().equals("PENDING")
                                    && complainModel.getEmp_uid().equals(
                                    uid)
                    ) {
                        pendingCount++;
                    } else if (
                            complainModel.getComplain_status().equals("INQUIRY")
                                    && complainModel.getEmp_uid().equals(
                                    uid)
                    ) {
                        inquiryCount++;
                    }
                    complainModelList.add(complainModel);


                }

                binding.tottalPostCount.setText((acceptCount + inquiryCount + completedCount + rejectCount + pendingCount) + "");
                binding.acceptedPostCount.setText(acceptCount + "");
                binding.completedPostCount.setText(completedCount + "");
                binding.rejectedPostCount.setText(rejectCount + "");
                binding.pendingPostCount.setText(pendingCount + "");
                binding.inQuiryPostCount.setText(inquiryCount + "");


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
        decideWhatToShow();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
//        switch (item.getItemId()) {
//            case R.id.logout_menu:
//                    loagOut();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }

        if (item.getItemId() == R.id.logout_menu) {
            loagOut();
            return true;
        } else if (item.getItemId() == R.id.profile_menu) {
            Intent p = new Intent(getApplicationContext(), EmpProfile.class);
            startActivity(p);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            updateProfile();
        } catch (Exception e) {

        }

    }

    private void updateProfile() {
        DatabaseReference mref = FirebaseDatabase.getInstance().getReference(Const.EMPLOYEE_LIST)
                .child(SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmp_uid() + "");


        String uid = OneSignal.getDeviceState().getUserId();
        mref.child("emp_not_uid").setValue(uid + "");
    }
}