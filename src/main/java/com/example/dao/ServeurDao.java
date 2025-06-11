package com.example.dao;

import com.example.database.Database;
import com.example.model.Serveur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServeurDao {

    public static List<Serveur> getAll() throws SQLException {
        List<Serveur> serveurs = new ArrayList<>();
        String sql = "SELECT ID, Nom, RegionID FROM Serveur";

        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                serveurs.add(new Serveur(
                    rs.getInt("ID"),
                    rs.getString("Nom"),
                    rs.getInt("RegionID")  
                ));
            }
        }
        return serveurs;
    }
    public static Serveur getById(int id) throws SQLException {
    String sql = "SELECT ID, Nom, RegionID FROM Serveur WHERE ID = ?";
    try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
        stmt.setInt(1, id);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return new Serveur(
                    rs.getInt("ID"),
                    rs.getString("Nom"),
                    rs.getInt("RegionID")
                );
            }
        }
    }
    return null;
}

}
