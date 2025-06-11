package com.example.dao;

import com.example.database.Database;
import com.example.model.Lien_Externe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LienExterneDao {

    public static List<Lien_Externe> getByJoueurId(int idJoueur) throws SQLException {
        List<Lien_Externe> liens = new ArrayList<>();
        String sql = "SELECT ID, URL, JoueurID FROM LienExterne WHERE JoueurID = ?";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, idJoueur);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    liens.add(new Lien_Externe(
                        rs.getInt("ID"),
                        rs.getString("URL"),
                        rs.getInt("JoueurID")
                    ));
                }
            }
        }
        return liens;
    }

    public static void insert(Lien_Externe lien) throws SQLException {
        String sql = "INSERT INTO LienExterne (URL, JoueurID) VALUES (?, ?)";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setString(1, lien.url);
            stmt.setInt(2, lien.idJoueur);
            stmt.executeUpdate();
        }
    }

    public static void delete(int id) throws SQLException {
        String sql = "DELETE FROM LienExterne WHERE ID = ?";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
