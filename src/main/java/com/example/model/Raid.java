package com.example.model;

public class Raid {
    public int id;
    public String heure;
    public int idCombat;
    public int idRaidplan;
    public int idLeader;

    public Raid(int id, String heure, int idCombat, int idRaidplan, int idLeader) {
        this.id = id;
        this.heure = heure;
        this.idCombat = idCombat;
        this.idRaidplan = idRaidplan;
        this.idLeader = idLeader;
    }

    public Raid() {}
}
