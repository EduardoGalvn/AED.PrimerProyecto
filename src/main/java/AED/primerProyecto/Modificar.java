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

		Scanner scanner = new Scanner(System.in);
		String consulta2 = "Select * from familia";
		boolean entradaValida = false;
		ArrayList<Integer> productos = new ArrayList<Integer>();
		ArrayList<Integer> familias = new ArrayList<Integer>();
		int v2, codProducto = 0;
		int codFamilia = 0;
		Double cambioDouble = 0.0;
		String cambioNombre = "";

		String consulta = "select productos.*, Denofamilia from productos inner join "
				+ "familia on familia.Codfamilia = productos.Codfamilia order by Codproducto";
		try {
			Connection conn = Database.getConnection();
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

		codProducto = pedirProducto(scanner, productos);

		String muestraModificar = "select productos.*, Denofamilia from productos inner join "
				+ "familia on familia.Codfamilia = productos.Codfamilia where Codproducto = " + codProducto;

		try {
			Connection conn = Database.getConnection();
			Statement sentencia = conn.createStatement();
			ResultSet resultado = sentencia.executeQuery(muestraModificar);

			while (resultado.next()) {
				System.out.println("\n" + resultado.getInt(1) + " | " + resultado.getString(2) + " | "
						+ resultado.getDouble(3) + " | " + resultado.getInt(4) + " | " + resultado.getInt(5) + " | "
						+ resultado.getString(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		entradaValida = false;
		while (entradaValida == false) {
			System.out.println("\n¿Qué desea modificar del producto?" + "\n1. El nombre." + "\n2. El precio."
					+ "\n3. El estado de congelado." + "\n4. La familia." + "\n5. Nada.");
			v2 = scanner.nextInt();
			switch (v2) {
			case 1:
				scanner.nextLine();
				System.out.println("Introduzca el nombre nuevo que desee: ");
				cambioNombre = scanner.nextLine();
				if (cambioNombre != "") {
					try {
						Connection con = Database.getConnection();
						PreparedStatement sent = con
								.prepareStatement("update productos set Denoproducto = ? where Codproducto = ? ");
						sent.setString(1, cambioNombre);
						sent.setInt(2, codProducto);
						sent.executeUpdate();
						System.out.println("Nombre modificado correctamente.");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				break;
			case 2:
				try {
					System.out.println("Introduzca el nuevo valor del precio: ");
					cambioDouble = scanner.nextDouble();
					scanner.nextLine();
				} catch (InputMismatchException e) {
					System.out.println("Entrada inválida, introduzca un número válido.");
				}
				try {
					Connection con = Database.getConnection();
					PreparedStatement sent = con
							.prepareStatement("update productos set PrecioBase = ? where Codproducto = ? ");
					sent.setDouble(1, cambioDouble);
					sent.setInt(2, codProducto);
					sent.executeUpdate();
					System.out.println("Precio modificado correctamente.");
				} catch (SQLException e) {
					e.printStackTrace();
				}

				break;
			case 3:
				try {
					Connection conn = Database.getConnection();
					
					Statement sentencia = conn.createStatement();
					ResultSet resultado = sentencia.executeQuery("select Congelado from productos where Codproducto = " + codProducto);
					resultado.next();
					boolean congelado = resultado.getBoolean(1);
					
					PreparedStatement sent = conn.prepareStatement("update productos set Congelado = ? where Codproducto = ? ");
					sent.setBoolean(1, !congelado);
					sent.setInt(2, codProducto);
					sent.executeUpdate();

				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("El estado ha cambiado.");
				break;
			case 4:
				try {
					Connection conn = Database.getConnection();
					Statement sentencia = conn.createStatement();
					ResultSet resultado = sentencia.executeQuery(consulta2);

					System.out.println("|— — — — — — — — — — — — — — — — — — — — — — — —|");
					while (resultado.next()) {
						System.out.println("| " + resultado.getInt(1) + " | " + resultado.getString(2) + " |");
						familias.add(resultado.getInt(1));
					}
					System.out.println("|— — — — — — — — — — — — — — — — — — — — — — — —|");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				codFamilia = pedirFamilia(scanner, entradaValida, familias);
				try {
					Connection con = DatabaseMySQL.getConnection();
					PreparedStatement sent = con.prepareStatement("update productos set Codfamilia = ? where Codproducto = ? ");
					sent.setInt(1, codFamilia);
					sent.setInt(2, codProducto);
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

	private static int pedirProducto(Scanner scanner, ArrayList<Integer> productos) {
		int codProducto = 0;
		boolean entradaValida = false;
		do {
			try {
				System.out.print("\n¿Qué producto desea modificar? Inserte el código del mismo: ");
				codProducto = Integer.parseInt(scanner.nextLine().trim());

				if (productos.contains(codProducto)) {
					entradaValida = true;
				} else {
					System.out.print("\nIntroduzca un codigo que se encuentre en la lista: ");
				}
			} catch (NumberFormatException e) {
				System.out.print("\nEntrada inválida. Introduzca un número válido: ");
			}
		} while (!entradaValida);
		return codProducto;
	}

	private static int pedirFamilia(Scanner scanner, boolean entradaValida, ArrayList<Integer> familias) {
		int cambio = -1;
		System.out.println("Introduzca el código de la familia que desee: ");
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
			}
		} while (!entradaValida);
		return cambio;
	}
}
