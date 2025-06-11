package com.example.dao;

import com.example.database.Database;
import com.example.model.Raidplan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RaidplanDao {

    public static List<Raidplan> getByCombatId(int idCombat) throws SQLException {
        List<Raidplan> raidplans = new ArrayList<>();
        String sql = "SELECT ID, Nom, URL, CombatID FROM Raidplan WHERE CombatID = ?";

        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, idCombat);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    raidplans.add(new Raidplan(
                        rs.getInt("ID"),
                        rs.getString("Nom"),
                        rs.getString("Url"),
                        rs.getInt("CombatID")
                    ));
                }
            }
        }
        return raidplans;
    }

    public static List<Raidplan> getAll() throws SQLException {
    List<Raidplan> raidplans = new ArrayList<>();
    String sql = "SELECT ID, Nom, Url, CombatID FROM Raidplan";

    try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            raidplans.add(new Raidplan(
                rs.getInt("ID"),
                rs.getString("Nom"),
                rs.getString("Url"),
                rs.getInt("CombatID")
            ));
        }
    }
    return raidplans;
}
public static Raidplan getById(int id) throws SQLException {
        String sql = "SELECT ID, Nom, URL, CombatID FROM Raidplan WHERE ID = ?";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Raidplan(
                        rs.getInt("ID"),
                        rs.getString("Nom"),
                        rs.getString("URL"),
                        rs.getInt("CombatID")
                    );
                }
            }
        }
        return null;
    }




}
