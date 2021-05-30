package com.example.fhir_allergyintolerance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AllergyListActivity extends AppCompatActivity {

    private static final String LOG_TAG = AllergyListActivity.class.getName();

    private RecyclerView aRecyclerView;
    private AllergyAdapter aAdapter;

    private ArrayList<String> allergiesData;

    private CollectionReference allergiesCR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allergy_list);

        aRecyclerView = findViewById(R.id.AllergyRecyclerView);
        aRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        allergiesData = new ArrayList<>();

        aAdapter = new AllergyAdapter(this, allergiesData);
        aRecyclerView.setAdapter(aAdapter);

        allergiesCR = FirebaseFirestore.getInstance().collection("Allergies");
        QueryData();

    }

    private void QueryData() {
        allergiesData.clear();

        allergiesCR.orderBy("name").get().addOnSuccessListener(queryDocumentSnapshots -> {
            Log.d(LOG_TAG, "queryDocumentSnapshots");
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                allergiesData.add(document.getString("name"));
            }
            if (allergiesData.size() == 0) {
                QueryData();
            }
            aAdapter.notifyDataSetChanged();
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.d(LOG_TAG, "Hiba oka: " + e.toString());
            }
        });
    }
}