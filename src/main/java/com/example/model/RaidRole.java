package com.example.model;

public class RaidRole {
    public int id;
    public int idRaid;
    public int idRole;
    public int nombreJoueur;

    public RaidRole(int id, int idRaid, int idRole, int nombreJoueur) {
        this.id = id;
        this.idRaid = idRaid;
        this.idRole = idRole;
        this.nombreJoueur = nombreJoueur;
    }

    public RaidRole() {}
}
