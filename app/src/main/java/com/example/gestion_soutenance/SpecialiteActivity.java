package com.example.gestion_soutenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.gestion_soutenance.databinding.ActivityDepartementBinding;
import com.example.gestion_soutenance.databinding.ActivitySpecialiteBinding;

public class SpecialiteActivity extends AppCompatActivity {

    ActivitySpecialiteBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpecialiteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ajoutSpecialiteButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AjoutSpecialiteActivity.class);
            startActivity(intent);
        });

        binding.listSpecialiteButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ListeSpecialiteActivity.class);
            startActivity(intent);
        });
    }
}