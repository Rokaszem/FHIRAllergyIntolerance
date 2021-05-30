package com.example.fhir_allergyintolerance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PatientDetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = PatientDetailActivity.class.getName();
    String pEmail;

    private CollectionReference mPatients;
    private DocumentReference documentReference;


    Patient p;
    String pKey;

    EditText pNameET;
    EditText pEmailET;
    EditText pAgeET;
    EditText pAllergiesListET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);

        pEmail = getIntent().getStringExtra("email");

        if (pEmail.isEmpty()) {
            finish();
        }

        pNameET = findViewById(R.id.PatientNameET);
        pEmailET = findViewById(R.id.PatientEmailET);
        pAgeET = findViewById(R.id.PatientAgeET);
        pAllergiesListET = findViewById(R.id.PatientAllergiesList);

        mPatients = FirebaseFirestore.getInstance().collection("Patient");


        mPatients.whereEqualTo("email", pEmail).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    pKey = documentSnapshot.getId();
                    p = documentSnapshot.toObject(Patient.class);

                    pNameET.setText(p.getName());
                    pEmailET.setText(p.getEmail());
                    pAgeET.setText(String.valueOf(p.getAge()));

                    Log.d(LOG_TAG, p.allergiesToString());
                    pAllergiesListET.setText(p.allergiesToString());
                    Log.d(LOG_TAG, p.allergiesToString());
                    Log.d(LOG_TAG, p.toString());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                finish();
            }
        });

    }

    public void UpdatePatient(View view) {
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("name", pNameET.getText().toString());
        childUpdates.put("email", pEmailET.getText().toString());
        childUpdates.put("age", Integer.parseInt(pAgeET.getText().toString()));
        childUpdates.put("allergies", p.toAllergiesList(pAllergiesListET.getText().toString()));
        Log.d(LOG_TAG, "childUpdates: " + childUpdates.toString());

        documentReference = FirebaseFirestore.getInstance().collection("Patient").document(pKey);

        documentReference.update(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(LOG_TAG, "docref: good");
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.e(LOG_TAG, "docref: " + e.toString());
            }
        });
    }

    public void DeletePatient(View view) {
        documentReference = FirebaseFirestore.getInstance().collection("Patient").document(pKey);
        documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(LOG_TAG,"Patient deleted");
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.e(LOG_TAG,"Failed delete patient");
            }
        });
    }
}