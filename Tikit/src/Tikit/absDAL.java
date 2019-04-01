package Tikit;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

//<editor-fold defaultstate="collapsed" desc="abstract Data Access Layer">//</editor-fold>

/**
 * Intended to provide access to the Database. all class shul inherited this
 * abstract
 * <p>
 * absDAL abstract Data Access Layer
 */
public abstract class absDAL {

    String host;
    String DBUsername;
    String DBpassword;

    public absDAL() {
        this.host = "jdbc:derby://localhost:1527/DB_Tikit";
        this.DBUsername = "root";
        this.DBpassword = "root";
    }

    //<editor-fold defaultstate="collapsed" desc="Data Manipulation Language (DML) Statements">//</editor-fold>
    int ExeDML(String query) {
        System.out.println("ExeDML will run SQL Querry:\n\t\t" + query);
        try (Connection Connection = DriverManager.getConnection(host, DBUsername, DBpassword)) {
            PreparedStatement stmt = Connection.prepareStatement(query);
            System.out.println(stmt.executeUpdate() + " row Was modifyed");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return 1;
        }
        return 0;
    }

    int ExeBulkPreparedSTMT(String query, Map<String, List<Integer>> Hall, int seatCoaunter) {
        System.out.println("ExeBatcPreparedSTMT will run prepared statement:\n\t\t" + query);

        try (Connection Connection = DriverManager.getConnection(host, DBUsername, DBpassword)) {
            Connection.setAutoCommit(false);
            PreparedStatement pstmt = Connection.prepareStatement(query);
            for (int i = 0; i < seatCoaunter; i++) {
                pstmt.setInt(1, Hall.get("MovieList").get(i));
                pstmt.setInt(2, Hall.get("TandaList").get(i));
                pstmt.setInt(3, Hall.get("SeatList").get(i));
//                System.out.println("run prepared statement " + Hall.get("MovieList").get(i) + Hall.get("TandaList").get(i) + Hall.get("SeatList").get(i));
                pstmt.addBatch();
            }
            int[] count = pstmt.executeBatch();
            int sum = 0;
            for (int i : count) {
                sum += i;
            }
            System.out.println(sum + " statement was rand in bulk");
            Connection.commit();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return 1;
        }
        return 0;
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Data Definition Language (DDL) Statements">//</editor-fold>

    /**
     * methods to create a Data Definition Language (DDL) Statements using the a
     * try cache
     */
    /**
     * will return a String ArrayList, as a column.
     */
    Map<String, List<Object>> getmapQuerry(String query) {
        System.out.println("intQuerry will run SQL Querry:\n\t\t" + query);

        Map<String, List<Object>> result = new HashMap<>();
        String[] keys = query.replace(" ", "").replace("SELECT", "").split("FROM")[0].split(",");
        for (String Key : keys) {
            result.put(Key, new ArrayList<>());
        }
        try (Connection Connection = DriverManager.getConnection(host, DBUsername, DBpassword)) {
            PreparedStatement stmt = Connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                for (int i = 1; i < keys.length; i++) {
                    result.get(keys[i]).add(resultSet.getInt(keys[i]));
                }
            }
            PrintResultSet(resultSet);
        } catch (SQLException ex) {
            Logger.getLogger(absDAL.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Query failed to run");
        }
        return result;
    }

    ArrayList<Integer> getintListQuerry(String query) {
        System.out.println("intQuerry will run SQL Querry:\n\t\t" + query);

        ArrayList<Integer> result = new ArrayList<>();

        try (Connection Connection = DriverManager.getConnection(host, DBUsername, DBpassword)) {
            PreparedStatement stmt = Connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getInt(1));
            }
            PrintResultSet(resultSet);
        } catch (SQLException ex) {
            Logger.getLogger(absDAL.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Query failed to run");
        }
        return result;
    }

    ArrayList<String> ExeDDL(String query) {
        System.out.println("ExeDDL will run SQL Querry:\n\t\t" + query);
        ArrayList<String> querryResults = new ArrayList<>();

        try (Connection Connection = DriverManager.getConnection(host, DBUsername, DBpassword)) {
            PreparedStatement stmt = Connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = stmt.executeQuery();
            ResultSetMetaData resultSetMD = resultSet.getMetaData();
            int columnCount;

            System.out.println("\t\t\t---- START OF QUERY RESULTS ----");
            columnCount = resultSetMD.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String name = resultSetMD.getColumnTypeName(i);
                System.out.print("  \t");
                System.out.print(name);
            }
            System.out.println("");

            for (int i = 1; i <= columnCount; i++) {
                System.out.print("  \t");
                String columnName = resultSetMD.getColumnName(i);
                System.out.print(columnName);
            }
            System.out.println("");

            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    querryResults.add(resultSet.getString(i));
                    System.out.print("  \t");
                    System.out.print(resultSet.getString(i));
                }
                System.out.println("");
            }

        } catch (SQLException ex) {
            Logger.getLogger(absDAL.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Query failed to run");
        }
        System.out.println("\t\t\t---- END OF QUERY RESULTS ----\n");
        return querryResults;
    }

    /**
     * will return a single int value.
     */
    int intQuerry(String query) {
        System.out.println("intQuerry will run SQL Querry:\n\t\t" + query);
        int result = 0;
        try (Connection Connection = DriverManager.getConnection(host, DBUsername, DBpassword)) {
            PreparedStatement stmt = Connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
            PrintResultSet(resultSet);
        } catch (SQLException ex) {
            Logger.getLogger(absDAL.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Query failed to run");
        }
        return result;
    }

    /**
     * will return a single boolean value.
     */
    boolean booleanQuerry(String query) {
        System.out.println("booleanDDL will run SQL Querry:\n\t\t" + query);
        boolean result = false;
        try (Connection Connection = DriverManager.getConnection(host, DBUsername, DBpassword)) {
            PreparedStatement stmt = Connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getBoolean(1);
            }
            PrintResultSet(resultSet);
        } catch (SQLException ex) {
            Logger.getLogger(absDAL.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Query failed to run");
        }
        return result;
    }

    /**
     * will return a single string value.
     */
    String stringQuerry(String query) {
        System.out.println("stringDDL will run SQL Querry:\n\t\t" + query);
        String result = null;
        try (Connection Connection = DriverManager.getConnection(host, DBUsername, DBpassword)) {
            PreparedStatement stmt = Connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getString(1);
            }
            PrintResultSet(resultSet);
        } catch (SQLException ex) {
            Logger.getLogger(absDAL.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Query failed to run");
        }
        return result;
    }

    /**
     * will pint out the result set to the console.
     */
    void PrintResultSet(ResultSet resultSet) {
        try {
            if (resultSet.first()) {
                ResultSetMetaData resultSetMD = resultSet.getMetaData();
                int columnCount = resultSetMD.getColumnCount();
                System.out.println("\t\t\t---- START OF QUERY RESULTS ----\n");

                for (int i = 1; i <= columnCount; i++) {
                    System.out.print("  \t" + resultSetMD.getColumnTypeName(i));
                }
                System.out.print("\n");

                for (int i = 1; i <= columnCount; i++) {
                    System.out.print("  \t" + resultSetMD.getColumnName(i));
                }
                System.out.print("\n");
                for (int i = 1; i <= columnCount; i++) {
                    if (i == 1) {
                        resultSet.first();
                        System.out.print("  \t" + resultSet.getString(i));
                    }
                    while (resultSet.next()) {
                        System.out.print("  \t" + resultSet.getString(i));
                    }
                    System.out.print("\n");
                }
                System.out.println("\t\t\t---- END OF QUERY RESULTS ----\n");
            }

        } catch (SQLException ex) {
            Logger.getLogger(absDAL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//</editor-fold>
}//</editor-fold>
