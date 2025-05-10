package com.example;

import java.sql.*;

public class App {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:my_database.db"; // Le fichier doit être à la racine du projet

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Connexion réussie à la base de données.");

                // Vérifie si la table users existe
                DatabaseMetaData meta = conn.getMetaData();
                ResultSet tables = meta.getTables(null, null, "users", null);
                if (!tables.next()) {
                    System.out.println("Table 'users' non trouvée. Assure-toi d'avoir initialisé la base.");
                    return;
                }

                // Lire les données
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM users");

                System.out.println("Liste des utilisateurs :");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    System.out.println("ID: " + id + ", Nom: " + name);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}
