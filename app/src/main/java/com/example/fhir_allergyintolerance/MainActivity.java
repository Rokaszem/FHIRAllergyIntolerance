package com.example.fhir_allergyintolerance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText userNameET;
    EditText userPasswordET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();

        userNameET = findViewById(R.id.loginUsername);
        userPasswordET = findViewById(R.id.loginPassword);
    }

    public void Login(View view) {
        String userName = userNameET.getText().toString();
        String userPassword = userPasswordET.getText().toString();
        Intent intent = new Intent(this, PatientListActivity.class);

        try {
            mAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        startActivity(intent);
                    }
                }
            }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Toast.makeText(MainActivity.this, "Failed login!", Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(MainActivity.this, "User email and password required!", Toast.LENGTH_LONG).show();
        }


    }


    public void Register(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}