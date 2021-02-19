package com.metacoders.e_proshashonadmin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.metacoders.e_proshashonadmin.Acitivity.AssaignAdmin.assaginZilaAdmin;
import com.metacoders.e_proshashonadmin.Acitivity.Fillter_Employee;
import com.metacoders.e_proshashonadmin.Acitivity.Fillter_RegAdmin;
import com.metacoders.e_proshashonadmin.Acitivity.Fillter_SysAdmin;
import com.metacoders.e_proshashonadmin.Acitivity.Fillter_UpzilaAdmin;
import com.metacoders.e_proshashonadmin.Models.EmpModel;
import com.metacoders.e_proshashonadmin.databinding.ActivityAdminDashboardBinding;
import com.metacoders.e_proshashonadmin.utils.SharedPrefManager;
import com.metacoders.e_proshashonadmin.utils.Utils;

import java.util.Arrays;
import java.util.List;

public class AdminDashboardActivity extends AppCompatActivity {

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
            if (ROLESTR.equals("regadmin" ) || ROLESTR.equals("upzadmin")) {
                // regonal admin like -> zila admin
                binding.createZilaAdminCard.setVisibility(View.GONE);
                binding.createEmp.setVisibility(View.GONE);

            } else if (ROLESTR.equals("employee")) {
                // plain employee like ->  employee
                binding.createZilaAdminCard.setVisibility(View.GONE);
                binding.createEmp.setVisibility(View.GONE);
                binding.zilaAdminView.setVisibility(View.GONE);
            }



            else {
                binding.zilaAdminView.setVisibility(View.GONE);
            }

        }


    }


}