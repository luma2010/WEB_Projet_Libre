package com.example.dao;

import com.example.database.Database;
import com.example.model.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDao {

    public static void insertRole(Role role) throws SQLException {
        String sql = "INSERT INTO Role (Nom) VALUES (?)";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setString(1, role.nom);
            stmt.executeUpdate();
        }
    }

    public static void deleteRole(Role role) throws SQLException {
        String sql = "DELETE FROM Role WHERE ID = ?";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, role.id);
            stmt.executeUpdate();
        }
    }

    public static void updateRole(Role role) throws SQLException {
        String sql = "UPDATE Role SET Nom = ? WHERE ID = ?";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setString(1, role.nom);
            stmt.setInt(2, role.id);
            stmt.executeUpdate();
        }
    }

    public static List<Role> getAll() throws SQLException {
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT ID, Nom FROM Role";

        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Role r = new Role(
                    rs.getInt("ID"),
                    rs.getString("Nom")
                );
                roles.add(r);
            }
        }

        return roles;
    }

    public static Role getRole(Role role) throws SQLException {
        String sql = "SELECT ID, Nom FROM Role WHERE ID = ?";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, role.id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Role(
                        rs.getInt("ID"),
                        rs.getString("Nom")
                    );
                } else {
                    return null;
                }
            }
        }
    }
}
