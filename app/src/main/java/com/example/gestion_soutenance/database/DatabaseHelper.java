package com.example.gestion_soutenance.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gestion_soutenance.Departement;
import com.example.gestion_soutenance.Specialite;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "gestion_soutenance.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Créez vos tables ici en appelant les méthodes "createTable"
        createTableRole(db);
        createTableUser(db);
        createTableDepartement(db);
        createTableSpecialite(db);
        createTableJury(db);
        createTableSoutenance(db);

        insertAdminUser(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Si vous mettez à jour votre application avec une nouvelle version de la base de données,
        // vous pouvez implémenter des modifications de schéma ici.
    }

    private void createTableRole(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE role ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nom TEXT"
                +")";
        db.execSQL(createTableQuery);

        // Insérez les trois rôles : "administrateur", "utilisateur", "jury"
        ContentValues values = new ContentValues();
        values.put("nom", "administrateur");
        db.insert("role", null, values);

        values.clear();
        values.put("nom", "utilisateur");
        db.insert("role", null, values);

        values.clear();
        values.put("nom", "jury");
        db.insert("role", null, values);
    }
    private void createTableUser(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE user ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nom TEXT,"
                + "prenom TEXT,"
                + "adresse TEXT,"
                + "tel TEXT,"
                + "email TEXT,"
                + "password TEXT,"
                + "role_id INTEGER,"
                + "FOREIGN KEY (role_id) REFERENCES role(id)"
                + ")";
        db.execSQL(createTableQuery);
    }



    private void createTableDepartement(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE departement ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nom TEXT"
                + ")";
        db.execSQL(createTableQuery);
    }
    private void createTableSpecialite(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE specialite ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nom TEXT,"
                + "departement_id INTEGER,"
                + "FOREIGN KEY (departement_id) REFERENCES departement(id)"
                + ")";
        db.execSQL(createTableQuery);
    }

    private void createTableSoutenance(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE soutenance ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "candidat1 TEXT,"
                + "candidat2 TEXT,"
                + "theme TEXT,"
                + "grade TEXT,"
                + "date DATE,"
                + "note_presentation TEXT,"
                + "note_document TEXT,"
                + "commentaire TEXT,"
                + "departement_id INTEGER,"
                + "specialite_id INTEGER,"
                + "FOREIGN KEY (departement_id) REFERENCES departement(id),"
                + "FOREIGN KEY (specialite_id) REFERENCES specialite(id)"
                + ")";
        db.execSQL(createTableQuery);
    }


    private void createTableJury(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE jury ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "user_id INTEGER,"
                + "role_id INTEGER,"
                + "grade REAL,"
                + "FOREIGN KEY (user_id) REFERENCES user(id),"
                + "FOREIGN KEY (role_id) REFERENCES role(id)"
                + ")";
        db.execSQL(createTableQuery);
    }

    private void insertAdminUser(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("nom", "Ekala");
        values.put("prenom", "Dores");
        values.put("adresse", "Liberté extension");
        values.put("tel", "772696364");
        values.put("email", "admin@admin.com");
        values.put("password", "admin123");
        values.put("role_id", 1); // ID du rôle d'administrateur

        long newRowId = db.insert("user", null, values);
    }

    public Boolean insertUser(String nom, String prenom, String adresse, String tel, String email, String password, int roleId){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nom", nom);
        values.put("prenom", prenom);
        values.put("adresse", adresse);
        values.put("tel", tel);
        values.put("email", email);
        values.put("password", password);
        values.put("role_id", roleId);  // Spécifiez le rôle de l'utilisateur

        long newRowId = db.insert("user", null, values);

// Vérifiez si l'insertion a réussi
        if (newRowId == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkEmail(String email){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user where email = ?",new String[]{email});
        if (cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }
    }

    /*
    public int getUserRoleId(String email) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT role_id FROM user WHERE email = ?", new String[]{email});
        int roleId = -1;
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("role_id");
            if (columnIndex >= 0) {
                roleId = cursor.getInt(columnIndex);
            }
        }
        cursor.close();
        return roleId;
    }
*/
    public Cursor checkEmailPassword(String email, String password){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM user WHERE email = ? AND password = ?";
        String[] selectionArgs = { email, password };
        return db.rawQuery(query, selectionArgs);
    }

    public Boolean checknomDepartement(String nom){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from departement where nom = ?",new String[]{nom});
        if (cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }
    }

    public Boolean insertDepartement(String nom){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nom", nom);
        long newRowId = db.insert("departement", null, values);

        // Vérifiez si l'insertion a réussi
        if (newRowId == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateDepartement(int departementId, String newNom) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nom", newNom);

        int result = db.update("departement", values, "id = ?", new String[]{String.valueOf(departementId)});
        db.close();

        return result != -1;
    }

    public boolean deleteDepartement(int departementId) {
        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete("departement", "id = ?", new String[]{String.valueOf(departementId)});
        db.close();

        return result != -1;
    }

    public boolean updateJury(int juryId, String newNom, String newPrenom, String newAdresse) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nom", newNom);
        values.put("prenom", newPrenom);
        values.put("adresse", newAdresse);


        int result = db.update("user", values, "id = ?", new String[]{String.valueOf(juryId)});
        db.close();

        return result != -1;
    }

    public boolean deleteJury(int juryId) {
        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete("user", "id = ?", new String[]{String.valueOf(juryId)});
        db.close();

        return result != -1;
    }


    public boolean insertSpecialite(String nom, int departementId) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nom", nom);
        values.put("departement_id", departementId);

        long newRowId = db.insert("specialite", null, values);

        // Vérifiez si l'insertion a réussi
        if (newRowId == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkSpecialite(String nom, int idDepartement) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM specialite WHERE nom = ? AND departement_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{nom, String.valueOf(idDepartement)});

        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }



    public List<Departement> getAllDepartements() {
        List<Departement> departements = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM departement";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex("id");
            int nomColumnIndex = cursor.getColumnIndex("nom");

            do {
                int id = cursor.getInt(idColumnIndex);
                String nom = cursor.getString(nomColumnIndex);

                Departement departement = new Departement(id, nom);
                departements.add(departement);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return departements;
    }

    public List<Specialite> getAllSpecialites() {
        List<Specialite> specialites = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM specialite";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex("id");
            int nomColumnIndex = cursor.getColumnIndex("nom");

            do {
                int id = cursor.getInt(idColumnIndex);
                String nom = cursor.getString(nomColumnIndex);

                Specialite specialite = new Specialite(id, nom);
                specialites.add(specialite);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return specialites;
    }

    public boolean updateSpecialite(int specialiteId, String newNom) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nom", newNom);

        int result = db.update("specialite", values, "id = ?", new String[]{String.valueOf(specialiteId)});
        db.close();

        return result != -1;
    }

    public boolean deleteSpecialite(int specialiteId) {
        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete("specialite", "id = ?", new String[]{String.valueOf(specialiteId)});
        db.close();

        return result != -1;
    }

    public Boolean insertSoutenance(String theme, String candidat1, String candidat2, int departementId, int specialiteId){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("theme", theme);
        values.put("candidat1", candidat1);
        values.put("candidat2", candidat2);
        values.put("departement_id", departementId);
        values.put("specialite_id", specialiteId);

        long newRowId = db.insert("soutenance", null, values);

// Vérifiez si l'insertion a réussi
        if (newRowId == -1) {
            return false;
        } else {
            return true;
        }
    }


    public boolean correctionSoutenance(int soutenanceId, String newTheme, String newNotePresentation, String newNoteDocument, String newCommentaire) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("theme", newTheme);
        values.put("note_presentation", newNotePresentation);
        values.put("note_document", newNoteDocument);
        values.put("commentaire", newCommentaire);

        int result = db.update("soutenance", values, "id = ?", new String[]{String.valueOf(soutenanceId)});
        db.close();

        return result != -1;
    }


}


