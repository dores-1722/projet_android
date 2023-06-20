package com.example.gestion_soutenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.gestion_soutenance.databinding.ActivityGestionJuryBinding;
import com.example.gestion_soutenance.databinding.ActivityGestionUserBinding;

public class GestionJuryActivity extends AppCompatActivity {

    private ActivityGestionJuryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGestionJuryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.correction.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ListeSoutenanceActivity.class);
            startActivity(intent);
        });
    }
}