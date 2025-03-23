package org.example;

import org.example.Interfaces.FunctionsInterface;
import weka.gui.knowledgeflow.StepTreeIgnore;

import java.io.*;
import java.util.Random;

public class Functions implements FunctionsInterface {

    private final OutPut outPut;

    public Functions(OutPut outPut) {
        this.outPut = outPut;
    }
    @Override
    public String createdData(){
        String[] optionsSex={"mujer","varon"};
        String[] optionsGrupoEdad={"menor de 25","25-40","mas de 55","40-55"};
        String[] optionsTipoVehiculo={"Motocicleta","Turismo","Monovolumen/ 4x4","Camioneta/Furgoneta"};
        String[] optionsAntiguedadVehi={"1-4","menos de 1","+10","5-10"};
        String grupoEdad="";
        String dato;

        String sexo = outPut.SelectDialog(optionsSex,"Selecciona un sexo");
        int edad = Integer.parseInt(outPut.TextDialog("Ingrese su edad"));

        if(edad<25){grupoEdad = "menor de 25";}
        if(edad>25 && edad<40){grupoEdad = "25-40";}
        if (edad > 40 && edad <55){grupoEdad = "40-55";}
        if (edad > 55){grupoEdad = "mas de 55";}

        int naños = Integer.parseInt(outPut.TextDialog("Ingrese el dato naño"));
        String tipoVehiculo = outPut.SelectDialog(optionsTipoVehiculo,"ingresa el tipo de vehiculo");
        String antiguedadVehi = outPut.SelectDialog(optionsAntiguedadVehi,"Antiguedad del vehiculo");
        double edadVehicle = Double.parseDouble(outPut.TextDialog("Ingrese la edad del vehiculo"));
        int antigCarnet = Integer.parseInt(outPut.TextDialog("Ingrese la antiguedad de su carnet"));
        int km = Integer.parseInt(outPut.TextDialog("Ingrese el kilometrage del vehiculo"));

        dato=(sexo+","+edad+","+"'"+grupoEdad+"'"+","+naños+","+tipoVehiculo+","+antiguedadVehi+","+edadVehicle+","+antigCarnet+","+km);
        return dato;
    }

    public static String addLineToArff(String filePath, String text) throws IOException {
        // Ruta donde se guardará el nuevo archivo
        Random rand = new Random();

        // Generamos un número aleatorio entre 100 y 999 (inclusive)
        int numRand = rand.nextInt(900) + 100;
        String newFilePath = "src/main/java/org/example/data"+numRand+".arff";
        File newFile = new File(newFilePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(newFile))) {

            String line;
            boolean inDataSection = false;

            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
                if (line.trim().equalsIgnoreCase("@data")) {
                    inDataSection = true;
                }
            }

            if (inDataSection) {
                writer.write(text);
                writer.newLine();
            } else {
                throw new IOException("El archivo ARFF no tiene una sección @data válida.");
            }
        }

        return newFile.getAbsolutePath(); // Retorna la ruta completa del archivo generado
    }
}
