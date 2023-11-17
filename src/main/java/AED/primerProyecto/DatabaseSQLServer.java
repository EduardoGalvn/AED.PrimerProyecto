package AED.primerProyecto;

public class DatabaseSQLServer extends Database {
    
    public DatabaseSQLServer() {
		super(
			"com.microsoft.sqlserver.jdbc.SQLServerDriver",
			"jdbc:sqlserver://localhost:1433;databaseName=proyecto_egc",
			"alu",
			"alu"
		);
	}

}
