package com.example.gestion_soutenance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.gestion_soutenance.database.DatabaseHelper;
import com.example.gestion_soutenance.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = binding.nom.getText().toString();
                String prenom = binding.prenom.getText().toString();
                String adresse = binding.adresse.getText().toString();
                String tel = binding.tel.getText().toString();
                String email = binding.email.getText().toString();
                String password = binding.password.getText().toString();
                int roleId = 2; // Rôle de l'utilisateur

                if (nom.equals("") || prenom.equals("") || adresse.equals("") || tel.equals("") || email.equals("") || password.equals("")){
                    Toast.makeText(SignUpActivity.this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkUserEmail = databaseHelper.checkEmail(email);
                    if (checkUserEmail == false){
                        Boolean insert = databaseHelper.insertUser(nom, prenom, adresse, tel, email, password, roleId);

                        if (insert == true){
                            Toast.makeText(SignUpActivity.this, "Inscription réussie", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignUpActivity.this, "Inscription échouée", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignUpActivity.this, "Cet utilisateur existe déja, veillez vous connecter", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.loginredirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}