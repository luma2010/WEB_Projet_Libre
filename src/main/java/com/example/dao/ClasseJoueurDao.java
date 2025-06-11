package com.example.dao;

import com.example.database.Database;
import com.example.model.Classe_joueur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClasseJoueurDao {

    public static List<Classe_joueur> getAll() throws SQLException {
        List<Classe_joueur> liste = new ArrayList<>();
        String sql = "SELECT ID, JoueurID, ClasseID, Niveau FROM Classe_Joueur";

        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                liste.add(new Classe_joueur(
                    rs.getInt("ID"),
                    rs.getInt("JoueurID"),
                    rs.getInt("ClasseID"),
                    rs.getInt("Niveau")
                ));
            }
        }
        return liste;
    }

    public static Classe_joueur getById(int id) throws SQLException {
        String sql = "SELECT ID, JoueurID, ClasseID, Niveau FROM Classe_Joueur WHERE ID = ?";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Classe_joueur(
                        rs.getInt("ID"),
                        rs.getInt("JoueurID"),
                        rs.getInt("ClasseID"),
                        rs.getInt("Niveau")
                    );
                }
            }
        }
        return null;
    }

    public static void insert(Classe_joueur cj) throws SQLException {
        String sql = "INSERT INTO Classe_Joueur (JoueurID, ClasseID, Niveau) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, cj.idJoueur);
            stmt.setInt(2, cj.idClasse);
            stmt.setInt(3, cj.niveau);
            stmt.executeUpdate();
        }
    }

    public static void delete(Classe_joueur cj) throws SQLException {
        String sql = "DELETE FROM Classe_Joueur WHERE ID = ?";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, cj.id);
            stmt.executeUpdate();
        }
    }

    public static void update(Classe_joueur cj) throws SQLException {
        String sql = "UPDATE Classe_Joueur SET JoueurID = ?, ClasseID = ?, Niveau = ? WHERE ID = ?";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, cj.idJoueur);
            stmt.setInt(2, cj.idClasse);
            stmt.setInt(3, cj.niveau);
            stmt.setInt(4, cj.id);
            stmt.executeUpdate();
        }
    }

    // Classe imbriquée statique pour envoyer le nom de la classe et le niveau associé
    public static class ClasseAvecNiveau {
    public int idLiaison;    // id de la liaison Classe_Joueur.ID
    public int idClasse;     // id réel de la classe
    public String nomClasse;
    public int niveau;

    public ClasseAvecNiveau(int idLiaison, int idClasse, String nomClasse, int niveau) {
        this.idLiaison = idLiaison;
        this.idClasse = idClasse;
        this.nomClasse = nomClasse;
        this.niveau = niveau;
    }
}


    // Récupère toutes les classes avec leur niveau pour un joueur donné
    public static List<ClasseAvecNiveau> getClassesWithNiveauByJoueurId(int idJoueur) throws SQLException {
    List<ClasseAvecNiveau> result = new ArrayList<>();
    String sql = "SELECT cj.ID, c.ID AS idClasse, c.Nom, cj.Niveau FROM Classe_Joueur cj " +
                 "JOIN Classe c ON cj.ClasseID = c.ID " +
                 "WHERE cj.JoueurID = ?";
    try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
        stmt.setInt(1, idJoueur);
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                result.add(new ClasseAvecNiveau(
                    rs.getInt("ID"),          // id liaison
                    rs.getInt("idClasse"),    // id classe réelle
                    rs.getString("Nom"),
                    rs.getInt("Niveau")
                ));
            }
        }
    }
    return result;
}

}
