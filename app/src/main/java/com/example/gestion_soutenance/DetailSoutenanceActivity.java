package com.example.gestion_soutenance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gestion_soutenance.database.DatabaseHelper;
import com.example.gestion_soutenance.databinding.ActivityAjoutSoutenanceBinding;
import com.example.gestion_soutenance.databinding.ActivityDepartementBinding;
import com.example.gestion_soutenance.databinding.ActivityDetailDepartementBinding;
import com.example.gestion_soutenance.databinding.ActivityDetailSoutenanceBinding;

public class DetailSoutenanceActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    private int soutenanceId;
    private String soutenanceTheme;

    private EditText editTextSoutenanceTheme;
    private EditText editTextSoutenanceNotePresentation;
    private EditText editTextSoutenanceNoteDocument;
    private EditText editTextSoutenanceCommentaire;



    private AppCompatButton buttonCorriger;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_soutenance);

        databaseHelper = new DatabaseHelper(this);

        // Récupérer l'ID et le nom du département depuis l'intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            soutenanceId = extras.getInt("soutenance_id");
            soutenanceTheme = extras.getString("soutenance_theme");
        }

        editTextSoutenanceTheme = findViewById(R.id.theme);
        editTextSoutenanceNotePresentation = findViewById(R.id.notePresentation);
        editTextSoutenanceNoteDocument = findViewById(R.id.noteDocument);
        editTextSoutenanceCommentaire = findViewById(R.id.commentaire);

        buttonCorriger = findViewById(R.id.correction);

        // Afficher le nom du département actuel
        editTextSoutenanceTheme.setText(soutenanceTheme);

        buttonCorriger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer le nouveau nom du département
                String updatedSoutenanceTheme = editTextSoutenanceTheme.getText().toString();
                String updateSoutenanceNotePresentation = editTextSoutenanceNotePresentation.getText().toString();
                String updateSoutenanceNoteDocument = editTextSoutenanceNoteDocument.getText().toString();
                String updateSoutenanceCommentaire = editTextSoutenanceCommentaire.getText().toString();
                // Mettre à jour le nom du département dans la base de données
                boolean isUpdated = databaseHelper.correctionSoutenance(soutenanceId, updatedSoutenanceTheme, updateSoutenanceNotePresentation, updateSoutenanceNoteDocument, updateSoutenanceCommentaire);

                if (isUpdated) {
                    // Afficher un message de succès
                    Toast.makeText(DetailSoutenanceActivity.this, "Soutenance corrigée", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DetailSoutenanceActivity.this, ListeSoutenanceActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Afficher un message d'erreur
                    Toast.makeText(DetailSoutenanceActivity.this, "Erreur lors de la correction", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}