package com.example.fhir_allergyintolerance;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.ViewHolder> implements Filterable {

    private static final String LOG_TAG=PatientAdapter.class.getName();
    private ArrayList<Patient> mPatientsData;
    private ArrayList<Patient> mPatientsDataAll;
    private Context mContext;

    private int lastPosition=-1;

    PatientAdapter(Context context, ArrayList<Patient> patientsData){
        this.mPatientsData=patientsData;
        this.mPatientsDataAll=patientsData;
        this.mContext=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.patient_item, parent, false)); //Blindolás az adapterhez
    }

    @Override
    public void onBindViewHolder(PatientAdapter.ViewHolder holder, int position) {
        Patient cPatient=mPatientsData.get(position);

        holder.bindTo(cPatient);

        if(holder.getAdapterPosition()>lastPosition){
            lastPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return mPatientsData.size();
    }

    @Override
    public Filter getFilter() {
        return patientFilter;
    }

    private Filter patientFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Patient> filteredPatientList=new ArrayList<>();
            FilterResults result=new FilterResults();

            if (constraint.equals(null) || constraint.length() == 0){
                result.count =mPatientsDataAll.size();
                result.values=mPatientsDataAll;
            }else{
                String searchFilter = constraint.toString().toLowerCase().trim();
                for (Patient p : mPatientsDataAll){
                    if (p.getName().toLowerCase().contains(searchFilter)){
                        filteredPatientList.add(p);
                    }
                }
                result.count=filteredPatientList.size();
                result.values=filteredPatientList;
            }
            return result;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mPatientsData=(ArrayList)results.values;
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mNameText;
        private TextView mAgeText;
        private TextView mEmailText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mNameText=itemView.findViewById(R.id.Name);
            mAgeText=itemView.findViewById(R.id.Age);
            mEmailText= itemView.findViewById(R.id.Email);

            itemView.findViewById(R.id.OpenPatient).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(v.getContext(),PatientDetail.class);
                    intent.putExtra("email",mEmailText.getText());
                    mContext.startActivity(intent);
                }
            });


        }

        public void bindTo(@NotNull Patient cPatient) {
            try {
                mNameText.setText(cPatient.getName());
                mAgeText.setText(String.valueOf(cPatient.getAge()));
                mEmailText.setText(cPatient.getEmail());
            }catch (Exception e){
                Log.e(LOG_TAG,"Hiba: "+e.toString());
            }
        }
    }
    /*
    public void OpenPatientDetail(View view) {
        Intent intent = new Intent(this, PatientDetail.class);
        startActivity(intent);
    }
     */
}


