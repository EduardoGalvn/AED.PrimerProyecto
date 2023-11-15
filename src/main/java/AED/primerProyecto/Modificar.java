package AED.primerProyecto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Modificar {

	public static void modificar() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String consulta2 = "Select * from familia";
		boolean entradaValida = false;
		ArrayList<Integer> productos = new ArrayList<Integer>();
		ArrayList<Integer> familias = new ArrayList<Integer>();
		int v2, cambio, modifica = 0;
		int cambioInt = 0;
		Double cambioDouble = 0.0;
		String cambioNombre = "";

		String consulta = "select productos.*, Denofamilia from productos inner join "
				+ "familia on familia.Codfamilia = productos.Codfamilia order by Codproducto";
		try {
			Connection conn = ConexionMySQL.conectarMySQL();
			Statement sentencia = conn.createStatement();
			ResultSet resultado = sentencia.executeQuery(consulta);

			System.out.println("|— — — — — — — — — — — — — — — — — — — — — — — —|");
			while (resultado.next()) {
				System.out.println("|  " + resultado.getInt(1) + " | " + resultado.getString(2) + " | "
						+ resultado.getDouble(3) + " | " + resultado.getInt(4) + " | " + resultado.getInt(5) + " | "
						+ resultado.getString(6) + " | ");
				productos.add(resultado.getInt(1));
			}

			System.out.println("|— — — — — — — — — — — — — — — — — — — — — — — —|");

		} catch (SQLException e) {
			e.printStackTrace();

		}

		do {
			try {
				System.out.print("\n¿Qué producto desea eliminar? Inserte el código del mismo: ");
				modifica = Integer.parseInt(scanner.nextLine().trim());

				if (productos.contains(modifica)) {
					entradaValida = true;
				} else {
					System.out.print("\nIntroduzca un codigo que se encuentre en la lista: ");
				}
			} catch (InputMismatchException e) {
				System.out.print("\nEntrada inválida. Introduzca un número válido: ");
			}
		} while (!entradaValida);

		String muestraModificar = "select productos.*, Denofamilia from productos inner join "
				+ "familia on familia.Codfamilia = productos.Codfamilia where Codproducto = " + modifica;

		try {
			Connection conn = ConexionMySQL.conectarMySQL();
			Statement sentencia = conn.createStatement();
			ResultSet resultado = sentencia.executeQuery(muestraModificar);

			while (resultado.next()) {
				System.out.println("\n"+resultado.getInt(1) + " | " + resultado.getString(2) + " | " + resultado.getDouble(3)
						+ " | " + resultado.getInt(4) + " | " + resultado.getInt(5) + " | " + resultado.getString(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		entradaValida = false;
		while(entradaValida==false) {
			System.out.println("\n¿Qué desea modificar del producto?"
					+ "\n1. El nombre."
					+ "\n2. El precio."
					+ "\n3. El estado de congelado."
					+ "\n4. La familia."
					+ "\n5. Nada.");
			v2 = scanner.nextInt();
			switch(v2) {
			case 1:
				scanner.nextLine();
				System.out.println("Introduzca el nombre nuevo que desee: ");
				cambioNombre = scanner.nextLine();
				if (cambioNombre != "") {
				try {
					Connection con = ConexionMySQL.conectarMySQL();
					PreparedStatement sent = con.prepareStatement("update productos set Denoproducto = ? where Codproducto = ? ");
					sent.setString(1, cambioNombre);
					sent.setInt(2, modifica);
					sent.executeUpdate();
					System.out.println("Nombre modificado correctamente.");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				}
				break;
			case 2:
				System.out.println("Introduzca el nuevo valor del precio: ");
				cambioDouble = scanner.nextDouble();
				scanner.nextLine();
				try {
					Connection con = ConexionMySQL.conectarMySQL();
					PreparedStatement sent = con.prepareStatement("update productos set PrecioBase = ? where Codproducto = ? ");
					sent.setDouble(1, cambioDouble);
					sent.setInt(2, modifica);
					sent.executeUpdate();
					System.out.println("Precio modificado correctamente.");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			case 3:
				try {
					Connection conn = ConexionMySQL.conectarMySQL();
					Statement sentencia = conn.createStatement();
					ResultSet resultado = sentencia.executeQuery("select Congelado from productos where Codproducto = "+modifica);

					if(resultado.getBoolean(1)==true) {
						PreparedStatement sent = conn.prepareStatement("update productos set Congelado = ? where Codproducto = ? ");
						sent.setBoolean(1, false);
						sent.setInt(2, modifica);
						sent.executeUpdate();
					} else {
						PreparedStatement sent = conn.prepareStatement("update productos set Congelado = ? where Codproducto = ? ");
						sent.setBoolean(1, true);
						sent.setInt(2, modifica);
						sent.executeUpdate();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("El estado ha cambiado.");
				break;
			case 4:
				try {
					Connection conn = ConexionMySQL.conectarMySQL();
					Statement sentencia = conn.createStatement();
					ResultSet resultado = sentencia.executeQuery(consulta2);
					
					System.out.println("|— — — — — — — — — — — — — — — — — — — — — — — —|");
					while (resultado.next()) {
						System.out.println("| "+ resultado.getInt(1) + " | "+ resultado.getString(2)+" |");
						familias.add(resultado.getInt(1));
				    	}
					System.out.println("|— — — — — — — — — — — — — — — — — — — — — — — —|");
				} catch (SQLException e) {
					e.printStackTrace();
				  }
				scanner.nextLine();
				System.out.println("Introduzca el código de la familia que desee: ");
				cambio = scanner.nextInt();
				
				do {
		            try {
		                cambio = scanner.nextInt();

		                if (familias.contains(cambio)) {
		                    entradaValida = true;
		                } else {
		                    System.out.print("\nIntroduzca una familia que se encuentre en la lista: ");
		                }
		            } catch (Exception e) {
		                System.out.print("\nEntrada inválida. Introduzca un número válido: ");
		                scanner.next();
		                
		            }
		        } while (!entradaValida);
				try {
					Connection con = ConexionMySQL.conectarMySQL();
					PreparedStatement sent = con.prepareStatement("update productos set Codfamilia = ? where Codproducto = ? ");
					sent.setInt(1, cambioInt);
					sent.setInt(2, modifica);
					sent.executeUpdate();
					System.out.println("Familia modificado correctamente.");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			case 5:
				entradaValida = true;
				break;
				
			default:
				System.out.println("Opción no válida. Por favor, ingrese un número del 1 al 5.");
			}
		}
	}
}
