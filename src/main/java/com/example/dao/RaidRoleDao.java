package com.example.dao;

import com.example.database.Database;
import com.example.model.RaidRole;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RaidRoleDao {
    public static List<RaidRole> getByRaidId(int idRaid) throws SQLException {
        List<RaidRole> roles = new ArrayList<>();
        String sql = "SELECT ID, RaidID, RoleID, NombreJoueur FROM Raid_Role WHERE RaidID = ?";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, idRaid);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    roles.add(new RaidRole(
                        rs.getInt("ID"),
                        rs.getInt("RaidID"),
                        rs.getInt("RoleID"),
                        rs.getInt("NombreJoueur")
                    ));
                }
            }
        }
        return roles;
    }

    public static void insert(RaidRole role) throws SQLException {
        String sql = "INSERT INTO Raid_Role (RaidID, RoleID, NombreJoueur) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, role.idRaid);
            stmt.setInt(2, role.idRole);
            stmt.setInt(3, role.nombreJoueur);
            stmt.executeUpdate();
        }
    }

    public static void updateComposition(int raidId, List<RaidRole> roles) throws SQLException {
    Connection conn = Database.getConnection();
    try {
        conn.setAutoCommit(false);

        // Suppression des anciens rôles du raid
        String deleteSql = "DELETE FROM Raid_Role WHERE RaidID = ?";
        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
            deleteStmt.setInt(1, raidId);
            deleteStmt.executeUpdate();
        }

        // Insertion des nouveaux rôles
        String insertSql = "INSERT INTO Raid_Role (RaidID, RoleID, NombreJoueur) VALUES (?, ?, ?)";
        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
            for (RaidRole role : roles) {
                insertStmt.setInt(1, raidId);
                insertStmt.setInt(2, role.idRole);
                insertStmt.setInt(3, role.nombreJoueur);
                insertStmt.addBatch();
            }
            insertStmt.executeBatch();
        }

        conn.commit();
    } catch (SQLException e) {
        conn.rollback();
        throw e;
    } finally {
        conn.setAutoCommit(true);
    }
}


    public static void delete(int id) throws SQLException {
        String sql = "DELETE FROM Raid_Role WHERE ID = ?";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    
}
