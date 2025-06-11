package com.example.dao;

import com.example.database.Database;
import com.example.model.JoueurRaid;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.example.model.JoueurRaidInfo;


public class JoueurRaidDao {
    public static List<JoueurRaid> getByRaidId(int idRaid) throws SQLException {
        List<JoueurRaid> joueurs = new ArrayList<>();
        String sql = "SELECT ID, JoueurID, RaidID, ClasseID FROM Joueur_Raid WHERE RaidID = ?";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, idRaid);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    joueurs.add(new JoueurRaid(
                        rs.getInt("ID"),
                        rs.getInt("JoueurID"),
                        rs.getInt("RaidID"),
                        rs.getInt("ClasseID")
                    ));
                }
            }
        }
        return joueurs;
    }

    public static void insert(JoueurRaid jr) throws SQLException {
        String sql = "INSERT INTO Joueur_Raid (JoueurID, RaidID, ClasseID) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, jr.idJoueur);
            stmt.setInt(2, jr.idRaid);
            stmt.setInt(3, jr.idClasse);
            stmt.executeUpdate();
        }
    }

    public static void delete(int id) throws SQLException {
        String sql = "DELETE FROM Joueur_Raid WHERE ID = ?";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
     public static List<JoueurRaidInfo> getByRaidIdWithNames(int raidId) throws SQLException {
        List<JoueurRaidInfo> list = new ArrayList<>();
        String sql = "SELECT jr.ID, jr.JoueurID AS idJoueur, j.Pseudo AS pseudoJoueur, jr.ClasseID AS idClasse, c.Nom AS nomClasse " +
                     "FROM Joueur_Raid jr " +
                     "JOIN Joueur j ON jr.JoueurID = j.ID " +
                     "JOIN Classe c ON jr.ClasseID = c.ID " +
                     "WHERE jr.RaidID = ?";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, raidId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new JoueurRaidInfo(
                        rs.getInt("ID"),
                        rs.getInt("idJoueur"),
                        rs.getString("pseudoJoueur"),
                        rs.getInt("idClasse"),
                        rs.getString("nomClasse")
                    ));
                }
            }
        }
        return list;
    }
}
