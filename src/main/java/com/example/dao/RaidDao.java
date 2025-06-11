package com.example.dao;

import com.example.model.Raid;
import com.example.database.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RaidDao {

    public static List<Raid> getAllRaids() throws SQLException {
        List<Raid> raids = new ArrayList<>();
        String sql = "SELECT ID, Heure, OrganisateurID, CombatID, RaidplanID FROM Raid";

        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                raids.add(new Raid(
                    rs.getInt("ID"),
                    rs.getString("Heure"),
                    rs.getInt("CombatID"),
                    rs.getInt("RaidplanID"),
                    rs.getInt("OrganisateurID")  // Leader = OrganisateurID
                ));
            }
        }

        return raids;
    }

    public static Raid getById(int id) throws SQLException {
        String sql = "SELECT ID, Heure, OrganisateurID, CombatID, RaidplanID FROM Raid WHERE ID = ?";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Raid(
                        rs.getInt("ID"),
                        rs.getString("Heure"),
                        rs.getInt("CombatID"),
                        rs.getInt("RaidplanID"),
                        rs.getInt("OrganisateurID")  // Leader = OrganisateurID
                    );
                }
            }
        }
        return null;
    }

    public static int insert(Raid raid) throws SQLException {
    String sql = "INSERT INTO Raid (Heure, CombatID, RaidplanID, OrganisateurID) VALUES (?, ?, ?, ?)";
    try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
        stmt.setString(1, raid.heure);
        stmt.setInt(2, raid.idCombat);
        stmt.setInt(3, raid.idRaidplan);
        stmt.setInt(4, raid.idLeader);
        stmt.executeUpdate();
    }
    // Récupérer le dernier ID inséré
    String sqlLastId = "SELECT last_insert_rowid() AS id";
    try (PreparedStatement stmt = Database.getConnection().prepareStatement(sqlLastId);
         ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
            return rs.getInt("id");
        } else {
            throw new SQLException("Impossible de récupérer l'ID inséré.");
        }
    }
}



    public static void update(Raid raid) throws SQLException {
        String sql = "UPDATE Raid SET Heure = ?, CombatID = ?, RaidplanID = ?, OrganisateurID = ? WHERE ID = ?";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setString(1, raid.heure);
            stmt.setInt(2, raid.idCombat);
            stmt.setInt(3, raid.idRaidplan);
            stmt.setInt(4, raid.idLeader);
            stmt.setInt(5, raid.id);
            stmt.executeUpdate();
        }
    }

    public static void delete(int id) throws SQLException {
        String sql = "DELETE FROM Raid WHERE ID = ?";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public static int getLastInsertedId() throws SQLException {
    String sql = "SELECT last_insert_rowid() AS id";
    try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
            return rs.getInt("id");
        }
    }
    return -1;
}

}
