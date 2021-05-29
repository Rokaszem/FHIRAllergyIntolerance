package com.example.fhir_allergyintolerance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class PatientDetail extends AppCompatActivity {

    private static final String LOG_TAG = PatientDetail.class.getName();
    String pEmail;

    private CollectionReference mPatients;

    Patient p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);

        pEmail=getIntent().getStringExtra("email");

        if (pEmail.isEmpty()){
            finish();
        }
        mPatients = FirebaseFirestore.getInstance().collection("Patient");

        mPatients.whereEqualTo("email",pEmail).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                //p = (Patient)queryDocumentSnapshots.toObjects(Patient.class);
                //p.setName(queryDocumentSnapshots.);
            }
        });
        //Log.d(LOG_TAG,p.toString());
    }
}