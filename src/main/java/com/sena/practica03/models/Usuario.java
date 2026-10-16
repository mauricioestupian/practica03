package com.sena.practica03.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity 
public class Usuario {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private  Integer numDoc;
    private String nom;
    public String ape;
    public LocalDate fecNac;


    //constructor general para poder acceder a la clase es un constructor vacio

    public Usuario() {
    }


    // constructor con todo

    public Usuario(Integer numDoc, String nom, String ape, LocalDate fecNac) {
        this.numDoc = numDoc;
        this.nom = nom;
        this.ape = ape;
        this.fecNac = fecNac;
    }


    //getter and setter

    public Integer getNumDoc() {
        return this.numDoc;
    }

    public void setNumDoc(Integer numDoc) {
        this.numDoc = numDoc;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getApe() {
        return this.ape;
    }

    public void setApe(String ape) {
        this.ape = ape;
    }

    public LocalDate getFecNac() {
        return this.fecNac;
    }

    public void setFecNac(LocalDate fecNac) {
        this.fecNac = fecNac;
    }

    
}
