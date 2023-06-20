package com.example.gestion_soutenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.gestion_soutenance.database.DatabaseHelper;
import com.example.gestion_soutenance.databinding.ActivityAjoutJuryBinding;

public class AjoutJuryActivity extends AppCompatActivity {
    ActivityAjoutJuryBinding binding;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAjoutJuryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.ajoutJury.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = binding.nom.getText().toString();
                String prenom = binding.prenom.getText().toString();
                String adresse = binding.adresse.getText().toString();
                String tel = binding.tel.getText().toString();
                String email = binding.email.getText().toString();
                String password = binding.password.getText().toString();
                int roleId = 3; // Rôle de l'utilisateur

                if (nom.equals("") || prenom.equals("") || adresse.equals("") || tel.equals("") || email.equals("") || password.equals("")){
                    Toast.makeText(AjoutJuryActivity.this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkUserEmail = databaseHelper.checkEmail(email);
                    if (checkUserEmail == false){
                        Boolean insert = databaseHelper.insertUser(nom, prenom, adresse, tel, email, password, roleId);

                        if (insert == true){
                            Toast.makeText(AjoutJuryActivity.this, "Ajout réussie", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), ListeJuryActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(AjoutJuryActivity.this, "Ajout échouée", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AjoutJuryActivity.this, "Cet jury existe déja", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}