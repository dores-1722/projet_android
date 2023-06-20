package com.example.gestion_soutenance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestion_soutenance.database.DatabaseHelper;

import java.util.List;

public class AjoutSpecialiteActivity extends AppCompatActivity {

    private Spinner spinnerDepartement;
    private EditText editTextNomSpecialite;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_specialite);

        spinnerDepartement = findViewById(R.id.spinnerDepartement);
        editTextNomSpecialite = findViewById(R.id.nomSpecialite);

        // Initialise le DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Récupère la liste des départements depuis la base de données
        List<Departement> departements = databaseHelper.getAllDepartements();

        // Crée un adaptateur pour le Spinner avec la liste des départements
        ArrayAdapter<Departement> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departements);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Associe l'adaptateur au Spinner
        spinnerDepartement.setAdapter(adapter);
    }



    public void onClickAjoutSpecialite(View view) {
        Departement departement = (Departement) spinnerDepartement.getSelectedItem();

        String nomSpecialite = editTextNomSpecialite.getText().toString();

        if (nomSpecialite.isEmpty() || departement == null) {
            Toast.makeText(AjoutSpecialiteActivity.this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
        } else {
            boolean checkSpecialite = databaseHelper.checkSpecialite(nomSpecialite, departement.getId());

            if (!checkSpecialite) {
                boolean insert = databaseHelper.insertSpecialite(nomSpecialite, departement.getId());

                if (insert) {
                    Toast.makeText(AjoutSpecialiteActivity.this, "Ajout réussi", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AjoutSpecialiteActivity.this, ListeSpecialiteActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AjoutSpecialiteActivity.this, "Ajout échoué", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(AjoutSpecialiteActivity.this, "Cette spécialité existe déjà dans ce département. Veuillez la modifier ou en créer une autre", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
