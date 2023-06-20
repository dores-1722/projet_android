package com.example.gestion_soutenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.gestion_soutenance.databinding.ActivityDepartementBinding;
import com.example.gestion_soutenance.databinding.ActivityJuryBinding;

public class JuryActivity extends AppCompatActivity {
    ActivityJuryBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJuryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ajoutJuryButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AjoutJuryActivity.class);
            startActivity(intent);
        });

        binding.listJuryButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ListeJuryActivity.class);
            startActivity(intent);
        });
    }
}