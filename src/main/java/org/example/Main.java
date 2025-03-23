package org.example;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        OutPut outPut = new OutPut();
        Functions functions = new Functions(outPut);

        String data=functions.createdData();
        String filePath = "src/main/java/org/example/allData.arff";
        Model.Analice(filePath);
        Model.predict(data);

    }

}

