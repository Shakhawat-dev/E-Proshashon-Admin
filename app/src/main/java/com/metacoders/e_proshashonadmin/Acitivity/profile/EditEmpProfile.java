package com.metacoders.e_proshashonadmin.Acitivity.profile;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
import com.metacoders.e_proshashonadmin.Acitivity.AssaignAdmin.EmpCreateActivity;
import com.metacoders.e_proshashonadmin.Models.EmpModel;
import com.metacoders.e_proshashonadmin.R;
import com.metacoders.e_proshashonadmin.databinding.ActivityEditEmpProfileBinding;
import com.metacoders.e_proshashonadmin.utils.Utils;

import java.io.File;

public class EditEmpProfile extends AppCompatActivity {
    EmpModel model;
    String imagePath, imageUrl = "NULL";
    StorageReference imageRef;
    Uri imageUri = null;
    private ActivityEditEmpProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageRef = FirebaseStorage.getInstance().getReference("emp_profile_image");
        binding = ActivityEditEmpProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model = (EmpModel) getIntent().getSerializableExtra("MODEL");
        imageUrl = model.getEmp_pp();

        setView(model);

        binding.profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission(EditEmpProfile.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(EditEmpProfile.this,
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

        binding.acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = binding.name.getText().toString();
                String mail = binding.email.getText().toString();
                String ph = binding.mobile.getText().toString();
                if(binding.password.getText().toString().length() == 0 ){

                }else {
                    model.setEmp_password(Utils.convertItTohash(binding.password.getText().toString()));
                }

                if(newName.isEmpty() || mail.isEmpty() || ph.isEmpty() ){
                    Toast.makeText(getApplicationContext() , "Error!! Please Fill The Form" , Toast.LENGTH_SHORT).show();
                }else {
                    model.setEmp_name(newName);
                    model.setEmp_mail(mail);
                    model.setEmp_ph(ph);

                    // upload the data
                    uploadTheData(model);

                }



            }
        });

        binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference m = FirebaseDatabase.getInstance().getReference().child("emp_list");
                String key = model.getEmp_uid();
                m.child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        triggerNextPage();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext() , "Operation Failed " , Toast.LENGTH_SHORT).show();
                    }
                }) ;
            }
        });


    }
    private void uploadTheData(EmpModel model) {
        ProgressDialog dialog1 = new ProgressDialog(EditEmpProfile.this);
        dialog1.setMessage("Updating Profile...");
        dialog1.show();
        DatabaseReference m = FirebaseDatabase.getInstance().getReference().child("emp_list");
        String key = model.getEmp_uid();
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
    private void uploadItToStorage(Uri imageUri, String imagePath) {
        ProgressDialog dialog = new ProgressDialog(EditEmpProfile.this);
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

    private void setView(EmpModel model) {
        binding.email.setText(model.getEmp_mail());
        binding.mobile.setText(model.getEmp_ph());
        binding.name.setText(model.getEmp_name());
        Glide.with(getApplicationContext())
                .load(model.getEmp_pp())
                .error(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.profilePic);

        binding.adminTypeSpinner.setText(model.getDepartment());
        if (model.getUpzila().contains("z")) {
            binding.upozillaSpinner.setText("জিলা");
        } else {
            binding.upozillaSpinner.setText(model.getUpzila());
        }
        binding.positionList.setText(model.getDepartment_name());
        if (model.getEmp_role().contains("_")) {
            String[] a = model.getEmp_role().split("_");
            binding.roleSpinner.setText(a[1]);
        }


    }
    private void triggerNextPage() {
        new AwesomeSuccessDialog(EditEmpProfile.this)
                .setTitle("অভিনন্দন")
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
    private void BringImagePicker() {
        ImagePicker.Companion.with(EditEmpProfile.this)
                .galleryOnly()
                //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .start();
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
}