package com.example.gestion_soutenance;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.gestion_soutenance.DepartementActivity;
import com.example.gestion_soutenance.SpecialiteActivity;
import com.example.gestion_soutenance.databinding.ActivityGestionAdminBinding;

public class GestionAdminActivity extends AppCompatActivity {

    private ActivityGestionAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGestionAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Récupérer les informations de l'administrateur depuis SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String nom = sharedPreferences.getString("name", "");
        String email = sharedPreferences.getString("email", "");

        // Mettre à jour les TextViews avec les informations de l'administrateur
        String welcomeMessage = "Bonjour, " + nom + " !";
        binding.textView14.setText(welcomeMessage);
        binding.textView15.setText(email);

        // Gérer le clic sur le bouton Specialite
        binding.specialiteButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SpecialiteActivity.class);
            startActivity(intent);
        });

        // Gérer le clic sur le bouton Departement
        binding.departementButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), DepartementActivity.class);
            startActivity(intent);
        });
        binding.juryButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), JuryActivity.class);
            startActivity(intent);
        });
    }
}
