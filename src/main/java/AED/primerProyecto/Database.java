package AED.primerProyecto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class Database {
	
	private static Database database;
	
	private String driver;
	private String connectionUrl;
	private String user;
	private String pass;
	private Connection conn;
	
    public Database(String driver, String connectionUrl, String user, String pass) {
		super();
		this.driver = driver;
		this.connectionUrl = connectionUrl;
		this.user = user;
		this.pass = pass;
	}

	private Connection conectar() {
        try {
    		if (conn == null || conn.isClosed()) {
    			Class.forName(driver);
    			conn = DriverManager.getConnection(connectionUrl, user, pass);
    		}
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            conn = null;
        }
        return conn;
    }
	
	public static void setDatabase(Database database) {
		Database.database = database; 
	}

    public static Connection getConnection() {
    	return database.conectar();
    }
	
}
