package AED.primerProyecto;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		
		System.out.println("¿Con qué base de datos desea conectarse?\n" +
				"1. MySQL\n"+
				"2. SQL Server\n");
		int opcionBD = scanner.nextInt();
		
		switch(opcionBD){
			case 1:
				Database.setDatabase(new DatabaseMySQL());
				break;
			case 2:
				Database.setDatabase(new DatabaseSQLServer());
				break;
		}
	
		boolean menu = true;
		while(menu == true) {
			System.out.println("""
				¿Qué desea hacer?
				1. Visualizar los productos
				2. Insertar un producto
				3. Actualizar
				4. Eliminar
				5. Cerrar conexion
				""");
			if (scanner.hasNextInt()) {
				int opcionMenu = scanner.nextInt();
				switch(opcionMenu){
				
					case 1:
						Visualizar.visualizar();
						break;
					case 2:
						Insertar.insertar();
						break;
					case 3:
						Modificar.modificar();
						break;
					case 4:
						Eliminar.eliminar();
						break;
					case 5:
						System.out.println("Adiós 👋");
						menu = false;
						break;
					default:
                        System.out.println("Opción no válida. Por favor, ingrese un número del 1 al 5.");
                        break;
		        }
			}
		}
					
		scanner.close();
	}		
}

