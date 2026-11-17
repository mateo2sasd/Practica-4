package vista;
import java.util.ArrayList;
import java.util.Scanner;
import modelo.Aportador;

public class Consola {
    private Scanner scanner = new Scanner(System.in);

    public int mostrarMenu() {
        System.out.println("\n===== REGISTRO DE INGRESOS =====");
        System.out.println("1. Cargar archivo");
        System.out.println("2. Mostrar aportadores");
        System.out.println("3. Mostrar resumen");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opcion: ");

        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void mostrarAportadores(ArrayList<Aportador> lista) {
        if (lista.isEmpty()) {
            mostrarMensaje("No hay registros cargados.");
            return;
        }

        for (Aportador a : lista) {
            System.out.println("\n-------------------------");
            System.out.println("Cedula: " + a.getCedula()
                + (a.isCedulaValida() ? " VALIDA" : " ERROR"));
            System.out.println("Correo: " + a.getCorreo()
                + (a.isCorreoValido() ? " VALIDO" : " ERROR"));
            System.out.printf("Monto: $%.2f %s%n",
                a.getMonto(),
                a.isMontoValido() ? "VALIDO" : "ERROR");
            System.out.println("Registro: "
                + (a.esValido() ? "VALIDO" : "INVALIDO"));
        }
    }

    public void mostrarResumen(double total, int[] errores) {
        System.out.println("\n===== RESUMEN =====");
        System.out.printf("Total valido: $%.2f%n", total);
        System.out.println("Cedulas incorrectas: " + errores[0]);
        System.out.println("Correos incorrectos: " + errores[1]);
        System.out.println("Montos incorrectos: " + errores[2]);
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}
