package com.example.dao;

import com.example.database.Database;
import com.example.model.Joueur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JoueurDao {

    public static void insertJoueur(Joueur joueur) throws SQLException {
        String sql = "INSERT INTO Joueur (Pseudo, Password, ServeurID) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setString(1, joueur.pseudo);
            stmt.setString(2, joueur.password);
            stmt.setInt(3, joueur.idServer);
            stmt.executeUpdate();
        }catch (SQLException e) {
        e.printStackTrace(); // <--- ce log te montrera l’erreur exacte côté console
        throw new RuntimeException("Erreur SQL lors de l'ajout du joueur", e); // ou re-propager selon ton handler
    }
    }

    public static void deleteJoueur(Joueur joueur) throws SQLException {
        String sql = "DELETE FROM Joueur WHERE ID = ?";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, joueur.id);
            stmt.executeUpdate();
        }
    }

    public static void updateJoueur(Joueur joueur) throws SQLException {
        String sql = "UPDATE Joueur SET Pseudo = ?, Password = ?, ServeurID = ? WHERE ID = ?";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setString(1, joueur.pseudo);
            stmt.setString(2, joueur.password);
            stmt.setInt(3, joueur.idServer);
            stmt.setInt(4, joueur.id);
            stmt.executeUpdate();
        }
    }

    public static List<Joueur> getAll() throws SQLException {
        List<Joueur> joueurs = new ArrayList<>();
        String sql = "SELECT ID, Pseudo, Password, ServeurID FROM Joueur";

        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Joueur j = new Joueur(
                    rs.getInt("ID"),
                    rs.getString("Pseudo"),
                    rs.getString("Password"),
                    rs.getInt("ServeurID")
                );
                joueurs.add(j);
            }
        }

        return joueurs;
    }

    public static Joueur getJoueur(Joueur joueur) throws SQLException {
        String sql = "SELECT ID, Pseudo, Password, ServeurID FROM Joueur WHERE ID = ?";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, joueur.id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Joueur(
                        rs.getInt("ID"),
                        rs.getString("Pseudo"),
                        rs.getString("Password"),
                        rs.getInt("ServeurID")
                    );
                } else {
                    return null;
                }
            }
        }
    }
    public static Joueur getByPseudo(String pseudo) throws SQLException {
    String sql = "SELECT ID, Pseudo, Password, ServeurID FROM Joueur WHERE Pseudo = ?";
    try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
        stmt.setString(1, pseudo);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return new Joueur(
                    rs.getInt("ID"),
                    rs.getString("Pseudo"),
                    rs.getString("Password"),
                    rs.getInt("ServeurID")
                );
            }
        }
    }
    return null;
}

}
