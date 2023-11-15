package AED.primerProyecto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Insertar {

	public static void insertar() {
		String consulta = "Select * from familia";
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		ArrayList<Integer> familias = new ArrayList<Integer>();
		
		System.out.println("El código de producto es autonumérico, no debe introducirlo.");
		System.out.print("\nIntroduzca el nombre del producto: ");
		String nombre = scanner.nextLine();
		
		while(true) {
			nombre.toCharArray();

			if(nombre.length() > 20) {
				System.out.print("Error, introduzca un nombre de producto menor a 20 caracteres: ");
				nombre = scanner.nextLine();
			} else {
				break;
			}	
		}
		
		System.out.print("\nIntroduzca el precio del producto: ");
		Double precio = 0.0;

		while(true) {
			try {
				precio = scanner.nextDouble();
				while(precio <= 0.0) {
					System.out.println("Por favor, inserte un precio mayor que cero. ");
					precio = scanner.nextDouble();
				}
				break;
			}catch(Exception e) {
				System.out.print("Error, introduzca una precio válido: ");
				scanner.next();
			}
		}
		
// Visualizamos las familias y sus codigos		
		try {
			Connection conn = ConexionMySQL.conectarMySQL();
			Statement sentencia = conn.createStatement();
			ResultSet resultado = sentencia.executeQuery(consulta);
			
			System.out.println("|— — — — — — — — — — — — — — — — — — — — — — — —|");
			while (resultado.next()) {
				System.out.println("| "+ resultado.getInt(1) + " | "+ resultado.getString(2)+" |");
				familias.add(resultado.getInt(1));
		    	}
			System.out.println("|— — — — — — — — — — — — — — — — — — — — — — — —|");
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		  }
		
		int familia = 0;
		boolean entradaValida = false;

        System.out.print("Introduzca la familia del producto (el código): ");

        do {
            try {
                familia = scanner.nextInt();

                if (familias.contains(familia)) {
                    entradaValida = true;
                } else {
                    System.out.print("\nIntroduzca una familia que se encuentre en la lista: ");
                }
            } catch (Exception e) {
                System.out.print("\nEntrada inválida. Introduzca un número válido: ");
                scanner.next();
                
            }
        } while (!entradaValida);
		
		
		System.out.print("\n¿Está congelado el producto?( 0=No | 1=Si ): ");
		int congelado = 0;
		
		while (true) {
            try {
                congelado = scanner.nextInt();

                if (congelado != 1 && congelado != 0) {
                    System.out.print("\nError, introduzca un valor válido: ");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("\nPor favor, introduzca 0 o 1 únicamente.");
                scanner.next();
            }
        }
			
		
		
		try {
			Connection conn = ConexionMySQL.conectarMySQL();
			PreparedStatement sentencia = conn.prepareStatement("INSERT INTO productos (Denoproducto, PrecioBase, Codfamilia, Congelado)"
																+" VALUES (?, ?, ?, ?)");
			
			sentencia.setString(1, nombre);
			sentencia.setDouble(2, precio);
			sentencia.setInt(3, familia);
			sentencia.setInt(4, congelado);
			
			sentencia.executeUpdate();
			
	    } catch (SQLException e) {
	        e.printStackTrace();
	        
	    }
		Visualizar.visualizar();
		
	}
}
