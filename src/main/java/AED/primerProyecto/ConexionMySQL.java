package AED.primerProyecto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {
    
    
    public static Connection conectarMySQL() {
        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto_egc", "root", "");
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            
        }

        return conn;
    }
    
    public Connection conn = ConexionMySQL.conectarMySQL();
	
	public String sentencia = "Select * from productos";

}
