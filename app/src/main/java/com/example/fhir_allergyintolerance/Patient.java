package com.example.fhir_allergyintolerance;

import java.util.ArrayList;
import java.util.Map;

public class Patient {
    private String Name = "Name";
    private String Email = "Email";
    private int Age = 999;
    private ArrayList<String> Allergies;

    public Patient() {
    }

    public Patient(String name, String email, int age) {
        Name = name;
        Email = email;
        Age = age;
    }

    public Patient(String name, String email, int age, ArrayList<String> allergies) {
        Name = name;
        Email = email;
        Age = age;
        Allergies = allergies;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public ArrayList<String> getAllergies() {
        return Allergies;
    }

    public void setAllergies(ArrayList<String> allergies) {
        Allergies = allergies;
    }

    public void setAllergiesFromMap(Map m) {
        Allergies = new ArrayList<String>(m.values());
    }

    public String allergiesToString() {
        String ret = "";
        if (Allergies != null) {
            for (String st : Allergies) {
                ret += st + ", ";
            }
        }
        return ret;
    }

    public ArrayList<String> toAllergiesList(String st) {
        ArrayList<String> ret = new ArrayList<String>();
        for (String ss : st.split(",")) {
            if (!ss.equals(" ")) {
                ret.add(ss.trim().toLowerCase());
            }
        }
        return ret;
    }

    @Override
    public String toString() {
        return "Patient{" +
                ", Name='" + Name + '\'' +
                ", Email='" + Email + '\'' +
                ", Age=" + Age +
                ", Allergies=" + Allergies +
                '}';
    }
}
