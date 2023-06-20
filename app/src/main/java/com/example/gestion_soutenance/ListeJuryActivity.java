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

public class ListeJuryActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private ListView listViewUsers;
    private ListeJuryActivity.UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_jury);

        databaseHelper = new DatabaseHelper(this);
        listViewUsers = findViewById(R.id.listViewUsers);

        // Obtenir la liste des départements depuis la base de données
        List<ListeJuryActivity.User> users = getAllUsers();

        // Créer et configurer l'adaptateur pour le ListView
        userAdapter = new ListeJuryActivity.UserAdapter(this, users);
        listViewUsers.setAdapter(userAdapter);
    }

    private List<ListeJuryActivity.User> getAllUsers() {
        List<ListeJuryActivity.User> users = new ArrayList<>();

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query("user", null, "role_id = ?", new String[]{"3"}, null, null, null);

        int idColumnIndex = cursor.getColumnIndex("id");
        int nomColumnIndex = cursor.getColumnIndex("nom");
        int prenomColumnIndex = cursor.getColumnIndex("prenom");
        int adresseColumnIndex = cursor.getColumnIndex("adresse");
        while (cursor.moveToNext()) {
            int id = cursor.getInt(idColumnIndex);
            String nom = cursor.getString(nomColumnIndex);
            String prenom = cursor.getString(prenomColumnIndex);
            String adresse = cursor.getString(adresseColumnIndex);

            ListeJuryActivity.User user = new ListeJuryActivity.User(id, nom, prenom, adresse);
            users.add(user);
        }

        cursor.close();
        db.close();

        return users;
    }

    public class User {
        private int id;
        private String nom;

        private String prenom;

        private String adresse;

        public User(int id, String nom, String prenom, String adresse) {
            this.id = id;
            this.nom = nom;
            this.prenom = prenom;
            this.adresse = adresse;
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

        public String getPrenom() {
            return prenom;
        }

        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }

        public String getAdresse() {
            return adresse;
        }

        public void setAdresse(String adresse) {
            this.adresse = adresse;
        }


    }

    public class UserAdapter extends ArrayAdapter<ListeJuryActivity.User> {
        private Context context;
        private List<ListeJuryActivity.User> users;

        public UserAdapter(Context context, List<ListeJuryActivity.User> users) {
            super(context, 0, users);
            this.context = context;
            this.users = users;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(context).inflate(R.layout.list_item_jury, parent, false);
            }

            ListeJuryActivity.User currentUser = users.get(position);

            AppCompatButton nomUser = listItemView.findViewById(R.id.nom_jury);

            nomUser.setText(currentUser.getNom());

            nomUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailJuryActivity.class);
                    intent.putExtra("jury_id", currentUser.getId());
                    intent.putExtra("jury_nom", currentUser.getNom());
                    intent.putExtra("jury_prenom", currentUser.getPrenom());
                    intent.putExtra("jury_adresse", currentUser.getAdresse());
                    // Ajoutez d'autres données si nécessaire
                    context.startActivity(intent);
                }
            });

            return listItemView;
        }
    }
}