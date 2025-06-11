package com.example.dao;

import com.example.database.Database;
import com.example.model.Combat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CombatDao {

    public static List<Combat> getAll() throws SQLException {
        List<Combat> combats = new ArrayList<>();
        String sql = "SELECT ID, Nom, Niveau, ILVL, DifficultéID FROM Combat";

        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                combats.add(new Combat(
                    rs.getInt("ID"),
                    rs.getString("Nom"),
                    rs.getInt("Niveau"),
                    rs.getInt("Ilvl"),
                    rs.getInt("DifficultéID")
                ));
            }
        }
        return combats;
    }
    public static Combat getById(int id) throws SQLException {
        String sql = "SELECT ID, Nom, Niveau, ILVL, DifficultéID FROM Combat WHERE ID = ?";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Combat(
                        rs.getInt("ID"),
                        rs.getString("Nom"),
                        rs.getInt("Niveau"),
                        rs.getInt("ILVL"),
                        rs.getInt("DifficultéID")
                    );
                }
            }
        }
        return null;
    }
}
