package com.example.fhir_allergyintolerance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class AddPatientActivity extends AppCompatActivity {

    private static final String LOG_TAG = AddPatientActivity.class.getName();

    private CollectionReference mPatients;

    EditText pName;
    EditText pAge;
    EditText pEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        mPatients = FirebaseFirestore.getInstance().collection("Patient");

        pName=findViewById(R.id.PatientNameET);
        pAge=findViewById(R.id.PatientAgeET);
        pEmail=findViewById(R.id.PatientEmailET);

    }

    public void AddPatient(View view) {
        Log.d(LOG_TAG,"Páciens "+pName.toString()+" hozzáadása");
        mPatients.add(new Patient(
            pName.getText().toString(),
            pEmail.getText().toString(),
            Integer.parseInt(pAge.getText().toString())
        )).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(LOG_TAG, "Sikeres hozzáadás");
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.d(LOG_TAG, "Hiba: "+e.toString());
            }
        });
    }
}