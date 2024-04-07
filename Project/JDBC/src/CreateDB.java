import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDB {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/";
        
        String username = "root";
        String password = "Kamal@2002";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected to the MySQL server.");

            
            String createDatabaseSql = "CREATE DATABASE IF NOT EXISTS ipl_database";
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(createDatabaseSql);
                System.out.println("Database created successfully.");
            }

            
            url += "ipl_database";
            conn.setCatalog("ipl_database");

            String createTableSql = "CREATE TABLE IF NOT EXISTS ipl_points ("
                    + "team_name VARCHAR(255) NOT NULL,"
                    + "matches_played INT NOT NULL,"
                    + "matches_won INT NOT NULL,"
                    + "matches_lost INT NOT NULL,"
                    + "points INT NOT NULL,"
                    + "PRIMARY KEY (team_name)"
                    + ")";
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(createTableSql);
                System.out.println("Table created successfully.");
            }

            
            String insertSql = "INSERT INTO ipl_points (team_name, matches_played, matches_won, matches_lost, points) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                insertTeam(pstmt, "Mumbai Indians", 14, 9, 5, 18);
                insertTeam(pstmt, "Chennai Super Kings", 14, 8, 6, 16);
                insertTeam(pstmt, "Delhi Capitals", 14, 8, 6, 16);
                insertTeam(pstmt, "Kolkata Knight Riders", 14, 7, 7, 14);
                insertTeam(pstmt, "Royal Challengers Bangalore", 14, 7, 7, 14);
                insertTeam(pstmt, "Punjab Kings", 14, 6, 8, 12);
                insertTeam(pstmt, "Rajasthan Royals", 14, 5, 9, 10);
                insertTeam(pstmt, "Sunrisers Hyderabad", 14, 3, 11, 6);
                insertTeam(pstmt, "Team A", 10, 6, 4, 12);
                insertTeam(pstmt, "Team B", 10, 5, 5, 10);
                insertTeam(pstmt, "Team C", 10, 4, 6, 8);
                insertTeam(pstmt, "Team D", 10, 3, 7, 6);
                insertTeam(pstmt, "Team E", 10, 7, 3, 14);
                insertTeam(pstmt, "Team F", 10, 8, 2, 16);
                insertTeam(pstmt, "Team G", 10, 5, 5, 10);
                insertTeam(pstmt, "Team H", 10, 4, 6, 8);
                insertTeam(pstmt, "Team I", 10, 6, 4, 12);
                insertTeam(pstmt, "Team J", 10, 7, 3, 14);
            }

            System.out.println("Data inserted successfully!");
        } catch (SQLException ex) {
            System.out.println("An error occurred.");
            ex.printStackTrace();
        }
    }

    private static void insertTeam(PreparedStatement stmt, String teamName, int matchesPlayed, int matchesWon, int matchesLost, int points) throws SQLException {
        stmt.setString(1, teamName);
        stmt.setInt(2, matchesPlayed);
        stmt.setInt(3, matchesWon);
        stmt.setInt(4, matchesLost);
        stmt.setInt(5, points);
        stmt.addBatch();
    }
}