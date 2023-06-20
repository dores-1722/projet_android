package com.example.gestion_soutenance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.gestion_soutenance.database.DatabaseHelper;
import java.util.ArrayList;
import java.util.List;

public class ListeDepartementActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private ListView listViewDepartements;
    private DepartementAdapter departementAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_departement);

        databaseHelper = new DatabaseHelper(this);
        listViewDepartements = findViewById(R.id.listview_departements);

        // Obtenir la liste des départements depuis la base de données
        List<Departement> departements = getAllDepartements();

        // Créer et configurer l'adaptateur pour le ListView
        departementAdapter = new DepartementAdapter(this, departements);
        listViewDepartements.setAdapter(departementAdapter);
    }

    private List<Departement> getAllDepartements() {
        List<Departement> departements = new ArrayList<>();

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query("departement", null, null, null, null, null, null);

        int idColumnIndex = cursor.getColumnIndex("id");
        int nomColumnIndex = cursor.getColumnIndex("nom");

        while (cursor.moveToNext()) {
            int id = cursor.getInt(idColumnIndex);
            String nom = cursor.getString(nomColumnIndex);

            Departement departement = new Departement(id, nom);
            departements.add(departement);
        }

        cursor.close();
        db.close();

        return departements;
    }

    public class Departement {
        private int id;
        private String nom;

        public Departement(int id, String nom) {
            this.id = id;
            this.nom = nom;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }
    }

    public class DepartementAdapter extends ArrayAdapter<Departement> {
        private Context context;
        private List<Departement> departements;

        public DepartementAdapter(Context context, List<Departement> departements) {
            super(context, 0, departements);
            this.context = context;
            this.departements = departements;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(context).inflate(R.layout.list_item_departement, parent, false);
            }

            Departement currentDepartement = departements.get(position);

            AppCompatButton nomDepartement = listItemView.findViewById(R.id.nom_departement);

            nomDepartement.setText(currentDepartement.getNom());

            // Ajoutez un OnClickListener pour le bouton de détail du département
            nomDepartement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Lancer l'activité DetailDepartementActivity avec les détails du département
                    Intent intent = new Intent(context, DetailDepartementActivity.class);
                    intent.putExtra("departement_id", currentDepartement.getId());
                    intent.putExtra("departement_nom", currentDepartement.getNom());
                    // Ajoutez d'autres données si nécessaire
                    context.startActivity(intent);
                }
            });

            return listItemView;
        }
    }
}
