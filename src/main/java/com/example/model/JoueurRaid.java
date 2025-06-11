package com.example.model;

public class JoueurRaid {
    public int id;
    public int idJoueur;
    public int idRaid;
    public int idClasse;

    public JoueurRaid(int id, int idJoueur, int idRaid, int idClasse) {
        this.id = id;
        this.idJoueur = idJoueur;
        this.idRaid = idRaid;
        this.idClasse = idClasse;
    }

    public JoueurRaid(){}
}
