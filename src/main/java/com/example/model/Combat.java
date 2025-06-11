package com.example.model;

public class Combat{
    public int id;
    public String nom;
    public int niveau;
    public int ilvl;
    public int idDifficulte;

    public Combat(int id, String nom, int niveau, int ilvl, int idDifficulte){
        this.id = id;
        this.nom = nom;
        this.niveau = niveau;
        this.ilvl = ilvl;
        this.idDifficulte = idDifficulte;
    }
}