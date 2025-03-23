package org.example;

import java.io.File;
import java.util.Arrays;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomTree;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

public class Model {

    public static void Analice(String filePath) {

        try {
            // Ruta del archivo .arff (cámbiala si es necesario)
            File file = new File(filePath);

            if (!file.exists()) {
                System.err.println("El archivo no existe: " + filePath);
                return;
            }

            // Cargar dataset en formato ARFF
            ConverterUtils.DataSource source = new ConverterUtils.DataSource(filePath);
            Instances data = source.getDataSet();

            // Establecer la última columna como clase
            data.setClassIndex(data.numAttributes() - 1);

            // Crear y entrenar el modelo RandomTree
            RandomTree tree = new RandomTree();
            tree.setNumFolds(1); // Define 5 folds para validación cruzada
            tree.buildClassifier(data);

            // Evaluar el modelo con validación cruzada
            Evaluation eval = new Evaluation(data);
            eval.crossValidateModel(tree, data, 10, new Random(1));

            // Mostrar el modelo entrenado
            System.out.println("Modelo RandomTree:\n" + tree);
            System.out.println("\n=== Evaluación del Modelo ===");
            System.out.println(eval.toSummaryString());
            System.out.println(eval.toMatrixString("\n=== Matriz de Confusión ===\n"));
            System.out.println(Arrays.toString(tree.getOptions()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

