package modelo;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistroIngresos {
    private ArrayList<Aportador> aportadores = new ArrayList<>();

    public void leerArchivo(String ruta) throws FileNotFoundException {
        Scanner archivo = new Scanner(new File(ruta), "UTF-8");
        StringBuilder texto = new StringBuilder();

        while (archivo.hasNextLine()) {
            texto.append(archivo.nextLine()).append(" ");
        }
        archivo.close();

        extraerDatos(texto.toString());
    }

    public void extraerDatos(String texto) {
        aportadores.clear();

        Pattern separador = Pattern.compile(
            "(?<=\\.)\\s+(?=[A-ZÁÉÍÓÚÑ])"
        );

        Pattern patronCedula = Pattern.compile(
            "(?i)\\b(?:c[eé]dula(?:\\s+es)?|CI)\\s*:?\\s*(\\d+)"
        );

        Pattern patronCorreo = Pattern.compile(
            "(?i)\\bcorreo\\s+([^\\s,;]+)"
        );

        Pattern patronMonto = Pattern.compile(
            "(?i)\\$\\s*(\\d+(?:\\.\\d{1,2})?)"
            + "|\\b(\\d+(?:\\.\\d{1,2})?)\\s*"
            + "(?:d[oó]lares|USD|\\$)"
        );

        String[] registros = separador.split(texto);

        for (String registro : registros) {
            Matcher mc = patronCedula.matcher(registro);
            if (!mc.find()) {
                continue;
            }

            String cedula = mc.group(1);
            String correo = "";
            double monto = 0;

            Matcher me = patronCorreo.matcher(registro);
            if (me.find()) {
                correo = me.group(1).replaceAll("\\.$", "");
            }

            Matcher mm = patronMonto.matcher(registro);
            if (mm.find()) {
                String cantidad = mm.group(1) != null
                        ? mm.group(1) : mm.group(2);
                try {
                    monto = Double.parseDouble(cantidad);
                } catch (NumberFormatException e) {
                    monto = 0;
                }
            }

            aportadores.add(new Aportador(cedula, correo, monto));
        }
    }

    public ArrayList<Aportador> getAportadores() {
        return aportadores;
    }
}
