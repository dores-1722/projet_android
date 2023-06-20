package com.example.gestion_soutenance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gestion_soutenance.database.DatabaseHelper;

public class DetailDepartementActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private int departementId;
    private String departementNom;
    private EditText editTextDepartementNom;
    private AppCompatButton buttonModifier;
    private AppCompatButton buttonSupprimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_departement);

        databaseHelper = new DatabaseHelper(this);

        // Récupérer l'ID et le nom du département depuis l'intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            departementId = extras.getInt("departement_id");
            departementNom = extras.getString("departement_nom");
        }

        editTextDepartementNom = findViewById(R.id.editText_departement_nom);
        buttonModifier = findViewById(R.id.button_modifier);
        buttonSupprimer = findViewById(R.id.button_supprimer);

        // Afficher le nom du département actuel
        editTextDepartementNom.setText(departementNom);

        buttonModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer le nouveau nom du département
                String updatedDepartementNom = editTextDepartementNom.getText().toString();

                // Mettre à jour le nom du département dans la base de données
                boolean isUpdated = databaseHelper.updateDepartement(departementId, updatedDepartementNom);

                if (isUpdated) {
                    // Afficher un message de succès
                    Toast.makeText(DetailDepartementActivity.this, "Nom du département mis à jour avec succès", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DetailDepartementActivity.this, ListeDepartementActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Afficher un message d'erreur
                    Toast.makeText(DetailDepartementActivity.this, "Erreur lors de la mise à jour du nom du département", Toast.LENGTH_SHORT).show();
                }

                // Mettre à jour le nom affiché
                departementNom = updatedDepartementNom;

                // Réafficher le nom mis à jour
                editTextDepartementNom.setText(departementNom);
            }
        });

        buttonSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Supprimer le département de la base de données
                boolean isDeleted = databaseHelper.deleteDepartement(departementId);

                if (isDeleted) {
                    // Afficher un message de succès
                    Toast.makeText(DetailDepartementActivity.this, "Département supprimé avec succès", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DetailDepartementActivity.this, ListeDepartementActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Afficher un message d'erreur
                    Toast.makeText(DetailDepartementActivity.this, "Erreur lors de la suppression du département", Toast.LENGTH_SHORT).show();
                }

                // Terminer l'activité et retourner à l'activité précédente
                finish();
            }
        });
    }
}
