package com.metacoders.e_proshashonadmin.Acitivity.AssaginedList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.metacoders.e_proshashonadmin.databinding.ActivityAssaginedListEmployeeBinding;
import com.metacoders.e_proshashonadmin.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class AssaginedListEmployee extends AppCompatActivity implements complainListAdapter.ItemClickListener {
    ValueEventListener valueEventListener = null;
    List<ComplainModel> complainModelList = new ArrayList<>();
    List<EmpModel> employeeList = new ArrayList<>();
    String department = "", upzila = "", role = "", uid = "";
    complainListAdapter adapter;
    private ActivityAssaginedListEmployeeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAssaginedListEmployeeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        uid = SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmp_uid();
        // as it is employee hide necessary views


        binding.clist.setLayoutManager(new LinearLayoutManager(this));

        loadEmpListData();
    }

    void loadEmpListData() {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(Const.COMPLAIN_REPO);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                /*
                loop the data  for the certain number and pass
                 */
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    ComplainModel model = postSnapshot.getValue(ComplainModel.class);
                    // replce with real uid
                    Log.d("TAG", "onDataChange: " + model.getEmp_uid());
                    try {
                        if (model.getAssignedTo().equals(uid)) {
                            complainModelList.add(model);
                        }
                    } catch (Exception e) {

                    }

                }


                if (complainModelList.size() == 0) {
                    Toast.makeText(getApplicationContext(),
                            "কোন অভিযোগ নেই", Toast.LENGTH_LONG).show();
                }

                binding.clist.setAdapter(new complainListAdapter(complainModelList, getApplicationContext(), AssaginedListEmployee.this));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(ComplainModel model) {
        Intent p = new Intent(getApplicationContext(), ComplainDetailsActivity.class);
        p.putExtra("COMPLAIN_MODEL", model);
        startActivity(p);
    }
}