package com.example.fhir_allergyintolerance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private CollectionReference aAllergies;
    private DocumentReference documentReference;

    private RecyclerView aRecyclerView;

    Patient p;
    String pKey;

    EditText pNameET;
    EditText pEmailET;
    EditText pAgeET;
    TextView aNameET;
    CheckBox aNameCB;

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
        aNameET = findViewById(R.id.AllergyNameET);
        aNameCB = findViewById(R.id.AllergyNameCB);


        aRecyclerView = findViewById(R.id.AllergyRecyclerView);
        //aRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        aAllergies = FirebaseFirestore.getInstance().collection("Allergies");
        mPatients = FirebaseFirestore.getInstance().collection("Patient");


        mPatients.whereEqualTo("email", pEmail).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    pKey = documentSnapshot.getId();
                    p = new Patient(documentSnapshot.getString("name"), documentSnapshot.getString("email"), (int) Math.round(documentSnapshot.getDouble("age")));

                    pNameET.setText(p.getName());
                    pEmailET.setText(p.getEmail());
                    pAgeET.setText(String.valueOf(p.getAge()));
                    ArrayList<String> list= (ArrayList<String>) documentSnapshot.get("allergies");
                    if (list != null) {
                        aAllergies.whereIn("name", Arrays.asList(list)).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot aQueryDocumentSnapshots) {
                                try {
                                    for (DocumentSnapshot aDocumentSnapshot : aQueryDocumentSnapshots) {
                                        Log.d(LOG_TAG, "hello: " + aDocumentSnapshot.getString("name"));
                                    }
                                }catch (Exception e){
                                    Log.e(LOG_TAG,"Hiba: "+e.toString());
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Log.e(LOG_TAG,"Hiba: "+e.toString());
                            }
                        });
                    }


                    Log.d(LOG_TAG, "asdasd: " + String.valueOf(p.getAllergies()));
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
        Log.d(LOG_TAG, "childUpdates: " + childUpdates.toString());

        documentReference = FirebaseFirestore.getInstance().collection("Patient").document(pKey);

        documentReference.update(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(LOG_TAG, "docref: good");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.e(LOG_TAG, "docref: " + e.toString());
            }
        });
    }

}