package AED.primerProyecto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSQLServer {
    
    
    public static Connection conectarSQLServer() {
        Connection conn = null;

        String jdbcUrl = "jdbc:sqlserver://localhost:1433;databaseName=proyecto_egc";
        String username = "alu";
        String password = "alu";
        try {
        	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Conexión exitosa a la base de datos SQL Server.");
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            
        }

        return conn;
    }
    
    public Connection conn = ConexionMySQL.conectarMySQL();
	
	public String sentencia = "Select * from productos";

}
