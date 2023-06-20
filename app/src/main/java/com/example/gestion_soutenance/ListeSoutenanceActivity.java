package com.example.gestion_soutenance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
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

import com.example.gestion_soutenance.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ListeSoutenanceActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ListView listViewSoutenances;
    private ListeSoutenanceActivity.SoutenanceAdapter soutenanceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_soutenance);

        databaseHelper = new DatabaseHelper(this);
        listViewSoutenances = findViewById(R.id.listview_soutenances);

        List<ListeSoutenanceActivity.Soutenance> soutenances = getAllSoutenances();

        // Créer et configurer l'adaptateur pour le ListView
        soutenanceAdapter = new ListeSoutenanceActivity.SoutenanceAdapter(this, soutenances);
        listViewSoutenances.setAdapter(soutenanceAdapter);
    }

    private List<ListeSoutenanceActivity.Soutenance> getAllSoutenances() {
        List<ListeSoutenanceActivity.Soutenance> soutenances = new ArrayList<>();

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query("soutenance", null, null, null, null, null, null);

        int idColumnIndex = cursor.getColumnIndex("id");
        int themeColumnIndex = cursor.getColumnIndex("theme");

        while (cursor.moveToNext()) {
            int id = cursor.getInt(idColumnIndex);
            String theme = cursor.getString(themeColumnIndex);

            ListeSoutenanceActivity.Soutenance soutenance = new ListeSoutenanceActivity.Soutenance(id, theme);
            soutenances.add(soutenance);
        }

        cursor.close();
        db.close();

        return soutenances;
    }

    public class Soutenance {
        private int id;
        private String theme;

        public Soutenance(int id, String theme) {
            this.id = id;
            this.theme = theme;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTheme() {
            return theme;
        }

        public void setNom(String nom) {
            this.theme = theme;
        }
    }

    public class SoutenanceAdapter extends ArrayAdapter<ListeSoutenanceActivity.Soutenance> {
        private Context context;
        private List<ListeSoutenanceActivity.Soutenance> soutenances;

        public SoutenanceAdapter(Context context, List<ListeSoutenanceActivity.Soutenance> soutenances) {
            super(context, 0, soutenances);
            this.context = context;
            this.soutenances = soutenances;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(context).inflate(R.layout.list_item_soutenance, parent, false);
            }

            ListeSoutenanceActivity.Soutenance currentSoutenance = soutenances.get(position);

            AppCompatButton themeSoutenance = listItemView.findViewById(R.id.theme_soutenance);

            themeSoutenance.setText(currentSoutenance.getTheme());

            // Ajoutez un OnClickListener pour le bouton de détail du département
            themeSoutenance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Lancer l'activité DetailDepartementActivity avec les détails du département
                    Intent intent = new Intent(context, DetailSoutenanceActivity.class);
                    intent.putExtra("soutenance_id", currentSoutenance.getId());
                    intent.putExtra("soutenance_theme", currentSoutenance.getTheme());
                    // Ajoutez d'autres données si nécessaire
                    context.startActivity(intent);
                }
            });

            return listItemView;
        }
    }
}