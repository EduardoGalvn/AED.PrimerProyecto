package AED.primerProyecto;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int v2;
		int v1;
		
		System.out.println("¿Con qué base de datos desea conectarse?\n" +
				"1. MySQL\n"+
				"2. SQL Server\n"+
				"3. Base\n");
				v1 = scanner.nextInt();
				
				switch(v1){
				
					case 1:
						ConexionMySQL.conectarMySQL();
						break;
					case 2:
						ConexionSQLServer.conectarSQLServer();
						break;
					case 3:
						//Conexion a base
				}
			
				boolean menu = true;
				while(menu == true) {
					System.out.println("\n¿Qué desea hacer?\n" +
						"1. Visualizar los productos\n"+
						"2. Insertar un producto\n"+
						"3. Actualizar\n"+
						"4. Eliminar\n"+
						"5. Cerrar conexion");
					if (scanner.hasNextInt()) {
						v2 = scanner.nextInt();
						switch(v2){
						
							case 1:
								Visualizar.visualizar();
								break;
							case 2:
								Insertar.insertar();
								break;
							case 3:
								System.out.println("No está implementado.");
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

