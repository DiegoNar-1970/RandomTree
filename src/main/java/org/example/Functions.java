package org.example;

import java.io.*;
import java.util.Random;

public class Functions {

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
