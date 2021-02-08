package com.metacoders.e_proshashonadmin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.metacoders.e_proshashonadmin.Models.ComplainModel;
import com.metacoders.e_proshashonadmin.databinding.ActivityComplainDetailsBinding;

public class ComplainDetailsActivity extends AppCompatActivity {

    private ActivityComplainDetailsBinding binding ;
    ComplainModel model ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityComplainDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // recive the model
        model =(ComplainModel) getIntent().getSerializableExtra("COMPLAIN_MODEL") ;

        // this model has all the data


    }
}