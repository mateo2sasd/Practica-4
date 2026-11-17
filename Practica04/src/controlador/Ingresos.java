package controlador;
import modelo.Aportador;
import modelo.RegistroIngresos;
import modelo.Validador;
import vista.Consola;

public class Ingresos {
    private RegistroIngresos registro = new RegistroIngresos();
    private Consola vista = new Consola();
    private Validador validador = new Validador();

    public void iniciar() {
        int opcion;

        do {
            opcion = vista.mostrarMenu();

            switch (opcion) {
                case 1:
                    cargarRegistros();
                    break;
                case 2:
                    mostrarRegistros();
                    break;
                case 3:
                    mostrarResumen();
                    break;
                case 4:
                    vista.mostrarMensaje("Programa finalizado.");
                    break;
                default:
                    vista.mostrarMensaje("Opcion incorrecta.");
            }
        } while (opcion != 4);
    }

    public void cargarRegistros() {
        try {
            registro.leerArchivo("registro_ingresos.txt");

            for (Aportador a : registro.getAportadores()) {
                a.setCedulaValida(
                    validador.validarCedula(a.getCedula()));
                a.setCorreoValido(
                    validador.validarCorreo(a.getCorreo()));
                a.setMontoValido(
                    validador.validarMonto(a.getMonto()));
            }

            vista.mostrarMensaje("Archivo cargado correctamente.");
            vista.mostrarMensaje("Registros: "
                + registro.getAportadores().size());

        } catch (Exception e) {
            vista.mostrarMensaje("Error al leer el archivo: "
                + e.getMessage());
        }
    }

    public void mostrarRegistros() {
        vista.mostrarAportadores(registro.getAportadores());
    }

    public void mostrarResumen() {
        double total = 0;
        int[] errores = new int[3];

        for (Aportador a : registro.getAportadores()) {
            if (!a.isCedulaValida()) errores[0]++;
            if (!a.isCorreoValido()) errores[1]++;
            if (!a.isMontoValido()) errores[2]++;

            if (a.esValido()) {
                total += a.getMonto();
            }
        }

        vista.mostrarResumen(total, errores);
    }
}
