package AED.primerProyecto;

public class DatabaseMySQL extends Database {
    
	public DatabaseMySQL() {
		super(
			"com.mysql.cj.jdbc.Driver",
			"jdbc:mysql://localhost:3306/proyecto_egc", 
			"root", 
			""
		);	
	}
    
}
