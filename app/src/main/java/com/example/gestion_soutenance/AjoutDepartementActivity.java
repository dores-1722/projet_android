package com.example.gestion_soutenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.gestion_soutenance.database.DatabaseHelper;
import com.example.gestion_soutenance.databinding.ActivityAjoutDepartementBinding;
import com.example.gestion_soutenance.databinding.ActivityDepartementBinding;

public class AjoutDepartementActivity extends AppCompatActivity {

    ActivityAjoutDepartementBinding binding;

    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAjoutDepartementBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.ajoutDepartement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = binding.nomDepartement.getText().toString();

                if(nom.equals("")){
                    Toast.makeText(AjoutDepartementActivity.this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkDepartementNom = databaseHelper.checknomDepartement(nom);
                    if (checkDepartementNom == false){
                        Boolean insert = databaseHelper.insertDepartement(nom);

                        if (insert == true){
                            Toast.makeText(AjoutDepartementActivity.this, "Ajout réussi", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), ListeDepartementActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(AjoutDepartementActivity.this, "Ajout échoué", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AjoutDepartementActivity.this, "Ce departement existe déja, veillez le modifier ou creer un autre", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}