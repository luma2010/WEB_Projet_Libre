package com.example.dao;

import com.example.database.Database;
import com.example.model.Classe;
import java.util.List;
import java.util.ArrayList;


import java.sql.*;

public class ClasseDao {
    public static void insertClasse(Classe classe) throws SQLException {
        String sql = "INSERT INTO Classe (Nom, Image, RoleID) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setString(1, classe.nom);
            stmt.setString(2, classe.image);
            stmt.setInt(3, classe.roleId);
            stmt.executeUpdate();
        }
    }

    public static void deleteClasse(Classe classe) throws SQLException {
        String sql = "DELETE FROM Classe WHERE ID = ?";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)){
            stmt.setInt(1,classe.id);
            stmt.executeUpdate();
        }
    }
    public static void updateClasse(Classe classe) throws SQLException {
    String sql = "UPDATE Classe SET Nom = ?, Image = ?, RoleID = ? WHERE ID = ?";
    try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
        stmt.setString(1, classe.nom);
        stmt.setString(2, classe.image);
        stmt.setInt(3, classe.roleId);
        stmt.setInt(4, classe.id);  
        stmt.executeUpdate();
    }
}
    public static List<Classe> getAll() throws SQLException {
    List<Classe> classes = new ArrayList<>();
    String sql = "SELECT ID, Nom, Image, RoleID FROM Classe";

    try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Classe c = new Classe(
                rs.getInt("ID"),
                rs.getString("Nom"),
                rs.getString("Image"),
                rs.getInt("RoleID")
            );
            classes.add(c);
        }
    }

    return classes;
}
public static Classe getClasse(Classe classe) throws SQLException {
    String sql = "SELECT ID, Nom, Image, RoleID FROM Classe WHERE ID = ?";
    try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
        stmt.setInt(1, classe.id);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return new Classe(
                    rs.getInt("ID"),
                    rs.getString("Nom"),
                    rs.getString("Image"),
                    rs.getInt("RoleID")
                );
            } else {
                return null;
            }
        }
    }
}
}


