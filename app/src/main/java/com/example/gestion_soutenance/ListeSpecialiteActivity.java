package com.example.gestion_soutenance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.gestion_soutenance.database.DatabaseHelper;

import java.util.List;

public class ListeSpecialiteActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private ListView listViewSpecialites;
    private SpecialiteAdapter specialiteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_specialite);

        databaseHelper = new DatabaseHelper(this);
        listViewSpecialites = findViewById(R.id.listview_specialites);

        // Obtenir la liste des spécialités depuis la base de données
        List<Specialite> specialites = databaseHelper.getAllSpecialites();

        // Créer et configurer l'adaptateur pour le ListView
        specialiteAdapter = new SpecialiteAdapter(this, specialites);
        listViewSpecialites.setAdapter(specialiteAdapter);
    }

    public class SpecialiteAdapter extends ArrayAdapter<Specialite> {
        private Context context;
        private List<Specialite> specialites;

        public SpecialiteAdapter(Context context, List<Specialite> specialites) {
            super(context, 0, specialites);
            this.context = context;
            this.specialites = specialites;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(context).inflate(R.layout.list_item_specialite, parent, false);
            }

            Specialite currentSpecialite = specialites.get(position);

            AppCompatButton nomSpecialite = listItemView.findViewById(R.id.nom_specialite);
            nomSpecialite.setText(currentSpecialite.getNom());

            // Ajoutez un OnClickListener pour le bouton de détail de la spécialité
            nomSpecialite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Lancer l'activité DetailSpecialiteActivity avec les détails de la spécialité
                    Intent intent = new Intent(context, DetailSpecialiteActivity.class);
                    intent.putExtra("specialite_id", currentSpecialite.getId());
                    intent.putExtra("specialite_nom", currentSpecialite.getNom());
                    // Ajoutez d'autres données si nécessaire
                    context.startActivity(intent);
                }
            });

            return listItemView;
        }
    }
}
