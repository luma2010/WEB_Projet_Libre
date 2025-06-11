package com.example.model;

public class Joueur {
    public int id;
    public String pseudo;
    public String password;
    public int idServer;

    // Constructeur complet (utile en interne)
    public Joueur(int id, String pseudo, String password, int idServer) {
        this.id = id;
        this.pseudo = pseudo;
        this.password = password;
        this.idServer = idServer;
    }

    // ✅ Constructeur vide (obligatoire pour Jackson)
    public Joueur() {
    }
}
