package org.example;
import javax.swing.*;

public class OutPut {

    public static String TextDialog() {
        try {
            String texto = JOptionPane.showInputDialog("Ingrese un texto:");

            // Condición 1: Texto vacío (o nulo si se cancela)
            if (texto == null || texto.trim().isEmpty()) {
                throw new IllegalArgumentException("El texto no puede estar vacío.");
            }


            return texto;

        } catch (IllegalArgumentException e) {

            return "ERROR: " + e.getMessage();


        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace(); // util para depurar
            return "ERROR: Ocurrió un error inesperado." + e.getMessage()  ;
        }
    }

    public static String SelectDialog(String[] options) {
        if (options == null || options.length == 0) {
            return "ERROR: No se proporcionaron opciones válidas.";
        }

        // Mostrar el cuadro de diálogo con las opciones
        int seleccion = JOptionPane.showOptionDialog(
                null,          // Componente padre (null significa que será centrado en pantalla)
                "Selecciona una opción",      // Mensaje a mostrar en el cuadro
                "Cuadro de Diálogo",          // Título del cuadro de diálogo
                JOptionPane.DEFAULT_OPTION,    // Tipo de cuadro de diálogo (DEFAULT_OPTION es el más simple)
                JOptionPane.INFORMATION_MESSAGE, // Tipo de mensaje
                null,                         // Icono (null para que no haya icono)
                options,                     // Opciones a mostrar
                options[0]                   // Opción predeterminada
        );
        System.out.println(seleccion);

        if (seleccion < 0 )  {
            return "Ninguna opción seleccionada";
             // El usuario eligió la opción 1
        }
        return options[seleccion];
    }


}
