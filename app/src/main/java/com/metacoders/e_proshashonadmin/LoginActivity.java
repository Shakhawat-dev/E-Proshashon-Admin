package com.metacoders.e_proshashonadmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.metacoders.e_proshashonadmin.Models.EmpModel;
import com.metacoders.e_proshashonadmin.databinding.ActivityLoginBinding;
import com.metacoders.e_proshashonadmin.utils.SharedPrefManager;
import com.metacoders.e_proshashonadmin.utils.Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    Boolean isUserFound = false;
    private DatabaseReference mref;
    private ActivityLoginBinding binding;
    List<EmpModel> empModelList = new ArrayList<>();
    List<EmpModel> TestempModelList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        //testMethod();
        mref = FirebaseDatabase.getInstance().getReference("emp_list");
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = binding.loginPassword.getText().toString();
                String number = binding.loginNumber.getText().toString();

                if (number.length() ==0  || pass.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Check The Form", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        login(number, Utils.convertItTohash(pass));
                        Log.d("TAG", "onClick: " +Utils.convertItTohash(pass)   );

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    private void login(String number, String pass) {
        // we will download the list and check for the pass
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                /*
                loop the data  for the certain number and pass
                 */
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    EmpModel user = postSnapshot.getValue(EmpModel.class);
                    empModelList.add(user) ;
                    if (user.getEmp_ph().equals(number) && user.getEmp_password().equals(pass)) {
                        isUserFound = true;
                        Toast.makeText(getApplicationContext(), "User Found !!", Toast.LENGTH_SHORT).show();
                        Gson gson = new Gson();
                        String data  = gson.toJson(user) ;
                        SharedPrefManager.getInstance(getApplicationContext())
                                .userLogin(data) ;
                        SharedPrefManager.getInstance(getApplicationContext())
                                .saveUser(user.getEmp_mail());
                        sendTodashboard() ;
                        break;
                    }

                }

                if (!isUserFound) {
                    Toast.makeText(getApplicationContext(), "User Not Found !!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendTodashboard() {
        // TODO to the dashboard
        startActivity(new Intent(getApplicationContext() , AdminDashboardActivity.class));
        finish();
    }

    void testMethod(){
        List<String> test1 = new ArrayList<>();

        List<String> test = Arrays.asList(Utils.disrictDesignationList) ;
        for ( String item : test ){
            if(item.contains("") && item.contains("???????????????????????? ???????????? ?????????????????????(?????????????????????)") ){
               test1.add(item) ;

            }
        }
        Log.d("TAG", "testMethod: " + test.size()+" "  +test1.size() );
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(SharedPrefManager.getInstance(getApplicationContext())
        .isUserLoggedIn()){
            startActivity(new Intent(getApplicationContext() , AdminDashboardActivity.class));
            finish();
        }
    }
}