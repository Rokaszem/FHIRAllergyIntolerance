package com.example.fhir_allergyintolerance;

import java.util.ArrayList;
import java.util.List;

public class Patient {
    private String Name;
    private String Email;
    private int Age;
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
