package com.example.model;

public class JoueurRaidInfo {
    public int id;
    public int idJoueur;
    public String pseudoJoueur;
    public int idClasse;
    public String nomClasse;

    public JoueurRaidInfo(int id, int idJoueur, String pseudoJoueur, int idClasse, String nomClasse) {
        this.id = id;
        this.idJoueur = idJoueur;
        this.pseudoJoueur = pseudoJoueur;
        this.idClasse = idClasse;
        this.nomClasse = nomClasse;
    }
}
