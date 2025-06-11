package com.example.model;

public class Raidplan{
    public int id;
    public String nom;
    public String url;
    public int idCombat;

    public Raidplan(int id, String nom, String url, int idCombat){
        this.id = id;
        this.nom = nom;
        this.url = url;
        this.idCombat = idCombat;
    }
}