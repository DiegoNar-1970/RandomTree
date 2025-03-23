package org.example;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        String filePath = "src/main/java/org/example/conditions.arff";
        String path = Functions.addLineToArff(filePath,"mujer,18,'menor de 25',1,Motocicleta,1-4,1.947064,0,35135,'con siniestros'");
        System.out.println(path);
        Model.Analice(path);

    }
}