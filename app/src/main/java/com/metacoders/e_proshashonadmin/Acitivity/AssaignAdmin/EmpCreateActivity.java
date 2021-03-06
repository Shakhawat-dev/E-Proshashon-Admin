package com.metacoders.e_proshashonadmin.Acitivity.AssaignAdmin;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.metacoders.e_proshashonadmin.Adapter.Check_box_adapter;
import com.metacoders.e_proshashonadmin.Adapter.complainListAdapter;
import com.metacoders.e_proshashonadmin.Const.Const;
import com.metacoders.e_proshashonadmin.Models.ComplainModel;
import com.metacoders.e_proshashonadmin.Models.EmpModel;
import com.metacoders.e_proshashonadmin.R;
import com.metacoders.e_proshashonadmin.databinding.ActivityAdminCreateBinding;
import com.metacoders.e_proshashonadmin.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmpCreateActivity extends AppCompatActivity implements Check_box_adapter.ItemClickListener
        , complainListAdapter.ItemClickListener {

    Uri imageUri;
    String imagePath, imageUrl = "NULL";
    StorageReference imageRef;
    List<EmpModel> allEmpList = new ArrayList<>();
    List<String> adminType = new ArrayList<>();
    List<String> upozillaType = new ArrayList<>();
    Check_box_adapter adapter;
    String emp_role = "";
    String department = "";
    String upzila = "";
    String department_name = "";
    // private String[] admin_Type_list = {"???????????? ?????????????????????", "?????????????????? ?????????????????????"};

    private ActivityAdminCreateBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminCreateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        imageRef = FirebaseStorage.getInstance().getReference("emp_profile_image");

        loadAdminTypes();
        loadDepartmentTypes();

        binding.positionList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                department_name = parent.getSelectedItem().toString();

                LoadRoleList(2);

           //     Toast.makeText(getApplicationContext(), "" + department_name, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTheData();
            }
        });

        binding.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        binding.profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission(EmpCreateActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(EmpCreateActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                        BringImagePicker();


                    } else {

                        BringImagePicker();

                    }

                } else {

                    BringImagePicker();

                }

            }
        });

        binding.adminTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {

                    /*
                        upzilaa flood
                     */
                    loadPermissionList(1);
                    LoadRoleList(1);

                }
                department = adapterView.getSelectedItem().toString();
                Log.d("TAG", "department -> : " + department);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String mainRole =  parent.getSelectedItem().toString();
                if (!mainRole.contains("?????????????????? ???????????????????????? ??????????????????")) {
                    // not a admin
                    emp_role = "employee_";

                } else {
                    // admin
                    emp_role = "upzadmin_";
                }

                emp_role = emp_role +  mainRole  ;

                Log.d("TAG", "emp role -> : " + emp_role);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.upozillaSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        upzila = parent.getSelectedItem().toString();
                        Log.d("TAG", "upzila -> : " + upzila);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );


    }

    private void loadPermissionList(int i) {
        List<String> dataset = new ArrayList<>();
        if (i == 1) {
            // upzilla load
            dataset = Arrays.asList(Utils.upzillaDesignationList);
        }
//        adapter = new Check_box_adapter(dataset, getApplicationContext(), this, checkBoxModelList);
//        binding.rcv.setLayoutManager(new LinearLayoutManager(this));
//        binding.rcv.setAdapter(adapter);
    }

    private void LoadRoleList(int i) {
        List<String> roleSet = new ArrayList<>();
        List<String> dataset = new ArrayList<>();
            //  upzila role  load
            roleSet = Arrays.asList(Utils.upzillaDesignationList);
            for (String item : roleSet) {
                if (item.contains(department_name)) {
                    /*
                     split the admin

                     */
                    String[] strings = item.split("_") ;
                    dataset.add(strings[0]);
                }
            }


        ArrayAdapter<String> roleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataset);
        roleAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        binding.roleSpinner.setAdapter(roleAdapter);

    }

    private void loadAdminTypes() {
        adminType = Const.adminType();
        adminType.remove(0);
        adminType.remove(0);
        ArrayAdapter<String> employeeTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, adminType);
        employeeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.adminTypeSpinner.setAdapter(employeeTypeAdapter);


        upozillaType = Const.upozillaType();
        ArrayAdapter<String> upozillaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, upozillaType);
        upozillaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.upozillaSpinner.setAdapter(upozillaAdapter);
    }


    private void loadDepartmentTypes() {
        List<String> deptList = Arrays.asList(Const.divisionList());
        List<String> newDeptList = new ArrayList<>(deptList);
        newDeptList.remove(1) ;
        ArrayAdapter<String> departmentTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, newDeptList);
        departmentTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.positionList.setAdapter(departmentTypeAdapter);

    }

    private void BringImagePicker() {
        ImagePicker.Companion.with(EmpCreateActivity.this)
                .galleryOnly()
                //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .start();
    }

    private void getTheData() {
        String emp_name = binding.name.getText().toString();
        String mobile = binding.mobile.getText().toString();
        String pass = binding.password.getText().toString();
        String email = binding.email.getText().toString();
        String com_pass = binding.passwordAgain.getText().toString();

        if (emp_name.isEmpty() || pass.isEmpty() || email.isEmpty() || mobile.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Fill The Form Accurately !!", Toast.LENGTH_SHORT).show();
        } else {
            if (com_pass.equals(pass)) {

                EmpModel model = new EmpModel();
                model.setEmp_mail(email);
                model.setEmp_ph(mobile);
                model.setEmp_role(emp_role);
                model.setEmp_pp(imageUrl);
                model.setEmp_name(emp_name);
                model.setEmp_password(Utils.convertItTohash(pass));
                model.setEmp_not_uid("NULL");
                model.setDepartment_name(department_name);
                model.setDepartment(department);
                model.setUpzila(upzila);


                uploadTheData(model);

            } else {
                Toast.makeText(getApplicationContext(), "Password Didn't matched !!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void uploadTheData(EmpModel model) {
        ProgressDialog dialog1 = new ProgressDialog(EmpCreateActivity.this);
        dialog1.setMessage("Creating Profile...");
        dialog1.show();
        DatabaseReference m = FirebaseDatabase.getInstance().getReference().child("emp_list");
        String key = m.push().getKey();
        model.setEmp_uid(key);
        m.child(key).setValue(model)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        dialog1.dismiss();
                        triggerNextPage();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog1.dismiss();
                Toast.makeText(getApplicationContext(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void triggerNextPage() {
        new AwesomeSuccessDialog(EmpCreateActivity.this)
                .setTitle("????????????????????????")
                .setMessage(R.string.app_name)
                .setColoredCircle(R.color.dialogSuccessBackgroundColor)
                .setDialogIconAndColor(R.drawable.ic_dialog_info, R.color.white)
                .setCancelable(false)
                .setPositiveButtonText(getString(R.string.dialog_yes_button))
                .setPositiveButtonbackgroundColor(R.color.dialogSuccessBackgroundColor)
                .setPositiveButtonTextColor(R.color.white)
                .setPositiveButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        //click
                        finish();
                    }
                })
                .setNegativeButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        //click
                    }
                })
                .show();
        //   Toast.makeText(applicationContext, "Success !!!", Toast.LENGTH_LONG).show()

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            imageUri = data.getData();
            imagePath = ImagePicker.Companion.getFilePath(data);
            uploadItToStorage(imageUri, imagePath);

        } else {
            Toast.makeText(getApplicationContext(), "Please Pick An Image !!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadItToStorage(Uri imageUri, String imagePath) {

        ProgressDialog dialog = new ProgressDialog(EmpCreateActivity.this);
        dialog.setMessage("Uploading The Image...");

        if (imageUri != null) {
            binding.profilePic.setImageURI(imageUri);
        }
        try {
            File imageFile = new File(imagePath);
            Uri image = Uri.fromFile(imageFile);
            if (!imagePath.isEmpty()) {
                dialog.show();
                UploadTask fileUploadTask = imageRef.child(System.currentTimeMillis() + ".jpg").putFile(image);
                fileUploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        dialog.dismiss();
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful()) ;
                        Uri download_Uri = uriTask.getResult();
                        imageUrl = download_Uri.toString();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(),
                                "Try again : " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Pick Image Again !!",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error : " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemClick(String roleName, boolean isSelected) {
        /*
         here isSelected will come true with the role name selected
         if its true then user selected
         else user is not selected
         */
        Log.d("TAG", "onItemClick: " + roleName + " " + isSelected);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //   decideWhatToLoad("sort('     bubble    ')->[12,2,3,5]");

    }

    @Override
    public void onItemClick(ComplainModel model) {

    }
}
