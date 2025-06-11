package com.example.database;

import java.io.*;
import java.nio.file.*;
import java.sql.*;

public class Database {
    private static final String DB_NAME = "raid.db";
    private static final Path LOCAL_DB_PATH = Paths.get(DB_NAME);
    private static Connection connection;

    public static void connect() throws SQLException, IOException {
        if (!Files.exists(LOCAL_DB_PATH)) {
            System.out.println("Base locale introuvable. Copie depuis les ressources...");
            try (InputStream in = Database.class.getResourceAsStream("/" + DB_NAME)) {
                if (in == null) {
                    throw new FileNotFoundException("Le fichier " + DB_NAME + " n'existe pas dans les ressources !");
                }
                Files.copy(in, LOCAL_DB_PATH, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Base copiée depuis les ressources vers " + LOCAL_DB_PATH.toAbsolutePath());
            }
        } else {
            System.out.println("Base déjà présente à : " + LOCAL_DB_PATH.toAbsolutePath());
        }

        connection = DriverManager.getConnection("jdbc:sqlite:" + LOCAL_DB_PATH.toAbsolutePath());
        System.out.println("Connexion SQLite OK !");
    }

    public static Connection getConnection() {
        return connection;
    }
}
