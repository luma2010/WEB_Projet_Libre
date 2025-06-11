package com.example.model;

public class Classe_joueur {
    public int id;
    public int idJoueur;  // cohérent avec DAO
    public int idClasse;
    public int niveau;

    public Classe_joueur(int id, int idJoueur, int idClasse, int niveau) {
        this.id = id;
        this.idJoueur = idJoueur;
        this.idClasse = idClasse;
        this.niveau = niveau;
    }
    public Classe_joueur() {}
}
