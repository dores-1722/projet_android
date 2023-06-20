package com.example.gestion_soutenance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gestion_soutenance.database.DatabaseHelper;

public class DetailJuryActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private int juryId;
    private String juryNom;

    private String juryPrenom;

    private String juryAdresse;


    private EditText editTextJuryNom;

    private EditText editTextJuryPrenom;

    private EditText editTextJuryadresse;
    private AppCompatButton buttonModifier;
    private AppCompatButton buttonSupprimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_jury);

        databaseHelper = new DatabaseHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            juryId = extras.getInt("jury_id");
            juryNom = extras.getString("jury_nom");
            juryPrenom = extras.getString("jury_prenom");
            juryAdresse = extras.getString("jury_adresse");
        }

        editTextJuryNom = findViewById(R.id.editText_jury_nom);
        editTextJuryPrenom = findViewById(R.id.editText_jury_prenom);
        editTextJuryadresse = findViewById(R.id.editText_jury_adresse);

        buttonModifier = findViewById(R.id.button_modifier);
        buttonSupprimer = findViewById(R.id.button_supprimer);

        editTextJuryNom.setText(juryNom);
        editTextJuryPrenom.setText(juryPrenom);
        editTextJuryadresse.setText(juryAdresse);


        buttonModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer le nouveau nom du département
                String updatedJuryNom = editTextJuryNom.getText().toString();
                String updatedJuryPrenom = editTextJuryPrenom.getText().toString();
                String updatedJuryAdress = editTextJuryadresse.getText().toString();

                // Mettre à jour le nom du département dans la base de données
                boolean isUpdated = databaseHelper.updateJury(juryId, updatedJuryNom, updatedJuryPrenom, updatedJuryAdress);

                if (isUpdated) {
                    // Afficher un message de succès
                    Toast.makeText(DetailJuryActivity.this, "le jury mis à jour avec succès", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DetailJuryActivity.this, ListeJuryActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Afficher un message d'erreur
                    Toast.makeText(DetailJuryActivity.this, "Erreur lors de la mise à jour du jury", Toast.LENGTH_SHORT).show();
                }

                // Mettre à jour le nom affiché
                juryNom = updatedJuryNom;
                juryPrenom = updatedJuryPrenom;
                juryAdresse = updatedJuryAdress;

                // Réafficher le nom mis à jour
                editTextJuryNom.setText(juryNom);
                editTextJuryPrenom.setText(juryPrenom);
                editTextJuryadresse.setText(juryAdresse);
            }
        });

        buttonSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Supprimer le département de la base de données
                boolean isDeleted = databaseHelper.deleteJury(juryId);

                if (isDeleted) {
                    // Afficher un message de succès
                    Toast.makeText(DetailJuryActivity.this, "Département supprimé avec succès", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DetailJuryActivity.this, ListeJuryActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Afficher un message d'erreur
                    Toast.makeText(DetailJuryActivity.this, "Erreur lors de la suppression du département", Toast.LENGTH_SHORT).show();
                }

                // Terminer l'activité et retourner à l'activité précédente
                finish();
            }
        });
    }
}