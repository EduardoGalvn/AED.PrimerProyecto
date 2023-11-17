package AED.primerProyecto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Visualizar {

	public static void visualizar() {
		
		String consulta = "select productos.*, Denofamilia from productos inner join "
				+"familia on familia.Codfamilia = productos.Codfamilia order by Codproducto";
		try {
			Connection conn = Database.getConnection();
	        Statement sentencia = conn.createStatement();
	        ResultSet resultado = sentencia.executeQuery(consulta);
	        
	        System.out.println("|— — — — — — — — — — — — — — — — — — — — — — — —|");
	        while (resultado.next()) {
	        System.out.println("|  " + resultado.getInt(1) + " | " + resultado.getString(2) + " | " + 
	        					resultado.getDouble(3) + " | " + resultado.getInt(4) + " | " +
	        					resultado.getBoolean(5) + " | " + resultado.getString(6) + " | ");
	    	}
	    	
	        System.out.println("|— — — — — — — — — — — — — — — — — — — — — — — —|");
	        conn.close();
	
	    } catch (SQLException e) {
	        e.printStackTrace();
	        
	    }
	}
}
