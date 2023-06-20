package com.example.gestion_soutenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gestion_soutenance.database.DatabaseHelper;
import com.example.gestion_soutenance.databinding.ActivityAjoutSoutenanceBinding;
import com.example.gestion_soutenance.databinding.ActivitySignUpBinding;

import java.util.List;

public class AjoutSoutenanceActivity extends AppCompatActivity {

    ActivityAjoutSoutenanceBinding binding;

    private Spinner spinnerDepartement;

    private Spinner spinnerSpecialite;

    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAjoutSoutenanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        spinnerDepartement = findViewById(R.id.spinnerDepartement);

        spinnerSpecialite = findViewById(R.id.spinnerSpecialite);

        List<Departement> departements = databaseHelper.getAllDepartements();

        // Crée un adaptateur pour le Spinner avec la liste des départements
        ArrayAdapter<Departement> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departements);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Associe l'adaptateur au Spinner
        spinnerDepartement.setAdapter(adapter1);

        List<Specialite> specialites = databaseHelper.getAllSpecialites();

        ArrayAdapter<Specialite> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, specialites);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Associe l'adaptateur au Spinner
        spinnerSpecialite.setAdapter(adapter2);


        binding.ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String theme = binding.theme.getText().toString();
                String candidat1 = binding.candidat1.getText().toString();
                String candidat2 = binding.candidat2.getText().toString();
                Departement departement = (Departement) spinnerDepartement.getSelectedItem();
                Specialite specialite = (Specialite) spinnerSpecialite.getSelectedItem();


                if (theme.equals("") || candidat1.equals("") || candidat2.equals("") || departement == null || specialite == null){
                    Toast.makeText(AjoutSoutenanceActivity.this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
                } else {
                        Boolean insert = databaseHelper.insertSoutenance(theme, candidat1, candidat2, departement.getId(), specialite.getId());

                        if (insert == true){
                            Toast.makeText(AjoutSoutenanceActivity.this, "Ajout réussi", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(AjoutSoutenanceActivity.this, "Ajout échoué", Toast.LENGTH_SHORT).show();
                        }

                }
            }
        });

    }
}