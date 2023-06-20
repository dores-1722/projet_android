package com.example.gestion_soutenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.gestion_soutenance.databinding.ActivityGestionUserBinding;

public class GestionUserActivity extends AppCompatActivity {

    private ActivityGestionUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGestionUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Récupérer les informations de l'administrateur depuis SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String nom = sharedPreferences.getString("name", "");
        String email = sharedPreferences.getString("email", "");

        // Mettre à jour les TextViews avec les informations de l'administrateur
        String welcomeMessage = "Bonjour, " + nom + " !";
        binding.textView14.setText(welcomeMessage);
        binding.textView15.setText(email);

        binding.soutenanceButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AjoutSoutenanceActivity.class);
            startActivity(intent);
        });

    }
}