package com.example.fhir_allergyintolerance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

import lombok.NonNull;

public class RegisterActivity extends AppCompatActivity {

    private static final String LOG_TAG=RegisterActivity.class.getName();

    EditText registerNameET;
    EditText registerPasswordET;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        registerNameET = findViewById(R.id.registerUsername);
        registerPasswordET = findViewById(R.id.registerPassword);

    }

    public void NewRegister(View view) {
        String registerName = registerNameET.getText().toString();
        String registerPassword = registerPasswordET.getText().toString();
        Log.i(LOG_TAG,registerName);


        mAuth.createUserWithEmailAndPassword(registerName,registerPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i(LOG_TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i(LOG_TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    public void updateUI(FirebaseUser account){
        if(account != null){
            Toast.makeText(this,"Signed in as"+account.getDisplayName(),Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,MainActivity.class));
        }else {
            Toast.makeText(this,"Error! Pleas try again.",Toast.LENGTH_LONG).show();
        }

    }

}