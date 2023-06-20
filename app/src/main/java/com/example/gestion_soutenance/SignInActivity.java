package com.example.gestion_soutenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.gestion_soutenance.database.DatabaseHelper;
import com.example.gestion_soutenance.databinding.ActivitySignInBinding;

public class SignInActivity extends AppCompatActivity {

    ActivitySignInBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.email.getText().toString();
                String password = binding.password.getText().toString();

                if (email.equals("") || password.equals("")) {
                    Toast.makeText(SignInActivity.this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
                } else {
                    Cursor cursor = databaseHelper.checkEmailPassword(email, password);
                    int roleIdIndex = cursor.getColumnIndex("role_id");
                    int nomIndex = cursor.getColumnIndex("nom");
                    int prenomIndex = cursor.getColumnIndex("prenom");
                    int emailIndex = cursor.getColumnIndex("email");

                    if (roleIdIndex != -1 && nomIndex != -1 && emailIndex != -1) {
                        if (cursor != null && cursor.moveToFirst()) {
                            int roleId = cursor.getInt(roleIdIndex);
                            String nom = cursor.getString(nomIndex);
                            String prenom = cursor.getString(prenomIndex);
                            String userEmail = cursor.getString(emailIndex);

                            if (roleId == 1) {
                                Toast.makeText(SignInActivity.this, "Connexion réussie en tant qu'administrateur", Toast.LENGTH_SHORT).show();

                                // Stockez les informations de l'administrateur dans SharedPreferences
                                SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("nom", nom);
                                editor.putString("prenom", prenom);
                                editor.putString("email", userEmail);
                                editor.apply();

                                Intent intent = new Intent(getApplicationContext(), GestionAdminActivity.class);
                                startActivity(intent);
                            } else if (roleId == 2) {
                                // Stockez les informations de l'administrateur dans SharedPreferences
                                SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("nom", nom);
                                editor.putString("prenom", prenom);
                                editor.putString("email", userEmail);
                                editor.apply();

                                Toast.makeText(SignInActivity.this, "Connexion réussie en tant qu'utilisateur", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), GestionUserActivity.class);
                                startActivity(intent);
                            } else {
                                // Stockez les informations de l'administrateur dans SharedPreferences
                                SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("nom", nom);
                                editor.putString("prenom", prenom);
                                editor.putString("email", userEmail);
                                editor.apply();
                                Toast.makeText(SignInActivity.this, "Connexion réussie en tant que jury", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), GestionJuryActivity.class);
                                startActivity(intent);
                            }
                            cursor.close();
                        } else {
                            Toast.makeText(SignInActivity.this, "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignInActivity.this, "Indices de colonnes non trouvés", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.signupredirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
