package com.example.fhir_allergyintolerance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AllergyAdapter extends RecyclerView.Adapter<AllergyAdapter.ViewHolder>  {


    private static final String LOG_TAG=AllergyAdapter.class.getName();
    private ArrayList<String> mAllergies;
    private Context mContext;

    private int lastPosition=-1;

    AllergyAdapter(Context context, ArrayList<String> allergies){
        this.mAllergies=allergies;
        this.mContext=context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new AllergyAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.allergy_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        String allergy=mAllergies.get(position);

        holder.bindTo(allergy);

        if(holder.getAdapterPosition()>lastPosition){
            lastPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return mAllergies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mNameTV;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            mNameTV=itemView.findViewById(R.id.AllergyNameET);
        }

        public void bindTo(@NotNull String allergy){
            mNameTV.setText(allergy);
        }
    }
}
