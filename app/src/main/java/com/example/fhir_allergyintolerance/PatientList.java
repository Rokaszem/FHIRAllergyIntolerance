package com.example.fhir_allergyintolerance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PatientList extends AppCompatActivity {

    private static final String LOG_TAG = PatientList.class.getName();
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    private RecyclerView mRecyclerView;
    private ArrayList<Patient> mPatientsData;
    private PatientAdapter mAdapter;

    private CollectionReference mPatients;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        user = FirebaseAuth.getInstance().getCurrentUser();

        mRecyclerView=findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,1));
        mPatientsData=new ArrayList<>();

        mAdapter=new PatientAdapter(this, mPatientsData);
        mRecyclerView.setAdapter(mAdapter);

        mPatients = FirebaseFirestore.getInstance().collection("Patient");



        Log.d(LOG_TAG,"mPatients log "+ String.valueOf(mPatients));
        QueryData();

    }

    private void QueryData() {
        mPatientsData.clear();

        mPatients.orderBy("name").get().addOnSuccessListener(queryDocumentSnapshots -> {
            Log.d(LOG_TAG,"queryDocumentSnapshots");
           for (QueryDocumentSnapshot document: queryDocumentSnapshots){
               Patient p = new Patient(document.getString("name"),document.getString("email"), (int)Math.round(document.getDouble("age")));
               mPatientsData.add(p);
           }
           if (mPatientsData.size()==0){
               QueryData();
           }
           mAdapter.notifyDataSetChanged();
        });
        mPatients.orderBy("Name").get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.d(LOG_TAG, "Hiba oka: "+e.toString());
            }
        });
    }

    public void AddPatient(View view) {
        Intent intent = new Intent(this, AddPatientActivity.class);
        startActivity(intent);
    }


}