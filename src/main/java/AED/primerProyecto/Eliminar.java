package AED.primerProyecto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Eliminar {

	public static void eliminar() {

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		ArrayList<Integer> productos = new ArrayList<Integer>();
		int i = 0;
		int eliminar = 0;
		String decide = "";

		// Visualizo todos los productos para que elija el que quiere eliminar.
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
		boolean entradaValida = false;

		do {
			try {
				System.out.print("\n¿Qué producto desea eliminar? Inserte el código del mismo: ");
				eliminar = Integer.parseInt(scanner.nextLine().trim());

				if (productos.contains(eliminar)) {
					entradaValida = true;
				} else {
					System.out.print("\nIntroduzca un codigo que se encuentre en la lista: ");
				}
			} catch (InputMismatchException e) {
				System.out.print("\nEntrada inválida. Introduzca un número válido: ");
			}
		} while (!entradaValida);

		String compruebaStock = "select Codproducto from Stock where Codproducto = " + eliminar;
		try {
			Connection conn = ConexionMySQL.conectarMySQL();
			Statement sentencia = conn.createStatement();
			ResultSet result = sentencia.executeQuery(compruebaStock);

			i = 0;
			while (result.next()) {
				i++;
			}
			if (i > 0) {
				System.out.println("El producto elegido se encuentra en el stock de " + i + " tienda/as, "
						+ "¿Desea borrarlo de todas? (Y o N): ");
				decide = scanner.nextLine();

				System.out.println("Valor de decide: [" + decide + "]");
				if (decide.trim().equalsIgnoreCase("Y") || decide.trim().equalsIgnoreCase("y")) {
				    try (Connection con = ConexionMySQL.conectarMySQL()) {
				        con.setAutoCommit(false); // Desactivar el autocommit para gestionar la transacción manualmente

				        try (PreparedStatement sentStock = con.prepareStatement("DELETE FROM stock WHERE Codproducto = ?")) {
				            sentStock.setInt(1, eliminar);
				            sentStock.executeUpdate();
				        } catch (SQLException e) {
				            con.rollback(); // Deshacer la transacción en caso de error
				            e.printStackTrace();
				        }
				        try (PreparedStatement sentProductos = con.prepareStatement("DELETE FROM productos WHERE Codproducto = ?")) {
				            sentProductos.setInt(1, eliminar);
				            sentProductos.executeUpdate();
				        } catch (SQLException e) {
				            con.rollback(); // Deshacer la transacción en caso de error
				            e.printStackTrace();
				        }
				        con.commit(); // Confirmar la transacción
				        System.out.println("Producto eliminado correctamente.");
				    } catch (SQLException e) {
				        e.printStackTrace();
				    }
				} else {
				    System.out.println("No se ha eliminado el producto ni sus stocks.");
				}
			} else {
				try {
					Connection con = ConexionMySQL.conectarMySQL();
					PreparedStatement sent = con.prepareStatement("delete from productos where Codproducto = ?");
					sent.setInt(1, eliminar);
					sent.executeUpdate();
					System.out.println("Producto eliminado correctamente.");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
