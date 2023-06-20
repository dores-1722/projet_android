package com.example.gestion_soutenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.gestion_soutenance.database.DatabaseHelper;
import com.example.gestion_soutenance.databinding.ActivityDepartementBinding;
import com.example.gestion_soutenance.databinding.ActivityMainBinding;

public class DepartementActivity extends AppCompatActivity {

    ActivityDepartementBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDepartementBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ajoutDepartementButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AjoutDepartementActivity.class);
            startActivity(intent);
        });

        binding.listDepartementButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ListeDepartementActivity.class);
            startActivity(intent);
        });
    }
}