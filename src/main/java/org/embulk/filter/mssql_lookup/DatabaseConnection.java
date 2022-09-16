package org.embulk.filter.mssql_lookup;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection = null;
    private DatabaseConnection(MssqlLookupFilterPlugin.PluginTask task) throws Exception{
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbURL = "jdbc:sqlserver://" +task.getHost() + ";databaseName=" +task.getDatabase();
            connection = DriverManager.getConnection(dbURL, task.getUserName(), task.getPassword());
            if (connection != null){
                DatabaseMetaData dm = null;
                try {
                    dm = connection.getMetaData();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    System.out.println("Driver name: " + dm.getDriverName());
                    System.out.println("Driver version: " + dm.getDriverVersion());
                    System.out.println("Product name: " + dm.getDatabaseProductName());
                    System.out.println("Product version: " + dm.getDatabaseProductVersion());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }

        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
    }
    public static Connection getConnection(MssqlLookupFilterPlugin.PluginTask task) throws SQLException {
        if(connection==null || connection.isClosed()){
            try {
                new DatabaseConnection(task);
                return connection;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
        return connection;
    }
}
