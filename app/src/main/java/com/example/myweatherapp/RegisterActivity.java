package com.example.myweatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText addUserName, addPassword, confirmPassword;
    Button btnCreateNew, btnLoginHere;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        addUserName = findViewById(R.id.addUserName);
        addPassword = findViewById(R.id.addPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        btnCreateNew = findViewById(R.id.btnCreateNew);
        btnLoginHere = findViewById(R.id.btnLoginHere);
        progressBar = findViewById(R.id.progressBar);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), WeatherListActivity.class));
            finish();
        }

        btnCreateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String add_user_name = addUserName.getText().toString().trim();
                String add_password = addPassword.getText().toString().trim();
                String confirm_password = confirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(add_user_name)) {
                    Toast.makeText(getApplicationContext(), "Add email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(add_password)) {
                    Toast.makeText(getApplicationContext(), "Add password", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(confirm_password)) {
                    Toast.makeText(getApplicationContext(), "Add password again to verify", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!add_password.equals(confirm_password)) {
                    Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(add_user_name, add_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Error!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        btnLoginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

    }
}