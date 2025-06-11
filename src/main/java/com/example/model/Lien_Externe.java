package com.example.model;

public class Lien_Externe {
    public int id;
    public String url;
    public int idJoueur;

    public Lien_Externe(int id, String url, int idJoueur) {
        this.id = id;
        this.url = url;
        this.idJoueur = idJoueur;
    }

    public Lien_Externe() {}
}
