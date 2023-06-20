package com.example.gestion_soutenance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gestion_soutenance.database.DatabaseHelper;

public class DetailSpecialiteActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private int specialiteId;
    private String specialiteNom;

    private EditText editTextSpecialiteNom;
    private AppCompatButton buttonModifier;
    private AppCompatButton buttonSupprimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_specialite);

        databaseHelper = new DatabaseHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            specialiteId = extras.getInt("specialite_id");
            specialiteNom = extras.getString("specialite_nom");
        }

        editTextSpecialiteNom = findViewById(R.id.editText_specialite_nom);

        buttonModifier = findViewById(R.id.button_modifier);
        buttonSupprimer = findViewById(R.id.button_supprimer);

        editTextSpecialiteNom.setText(specialiteNom);

        buttonModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer le nouveau nom de la spécialité
                String updatedSpecialiteNom = editTextSpecialiteNom.getText().toString();

                // Mettre à jour le nom de la spécialité dans la base de données
                boolean isUpdated = databaseHelper.updateSpecialite(specialiteId, updatedSpecialiteNom);

                if (isUpdated) {
                    // Afficher un message de succès
                    Toast.makeText(DetailSpecialiteActivity.this, "La spécialité a été mise à jour avec succès", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DetailSpecialiteActivity.this, ListeSpecialiteActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Afficher un message d'erreur
                    Toast.makeText(DetailSpecialiteActivity.this, "Erreur lors de la mise à jour de la spécialité", Toast.LENGTH_SHORT).show();
                }

                // Mettre à jour le nom affiché
                specialiteNom = updatedSpecialiteNom;

                // Réafficher le nom mis à jur
                editTextSpecialiteNom.setText(specialiteNom);
            }
        });

        buttonSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Supprimer la spécialité de la base de données
                boolean isDeleted = databaseHelper.deleteSpecialite(specialiteId);

                if (isDeleted) {
                    // Afficher un message de succès
                    Toast.makeText(DetailSpecialiteActivity.this, "La spécialité a été supprimée avec succès", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DetailSpecialiteActivity.this, ListeSpecialiteActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Afficher un message d'erreur
                    Toast.makeText(DetailSpecialiteActivity.this, "Erreur lors de la suppression de la spécialité", Toast.LENGTH_SHORT).show();
                }

                // Terminer l'activité et retourner à l'activité précédente
                finish();
            }
        });
    }
}
