package org.example;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomTree;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Random;

public class Model {

    private static RandomTree tree; // Modelo entrenado
    private static Instances datasetFormat; // Estructura del dataset

    //Entrena el modelo con el archivo ARFF y muestra la evaluación

    public static void Analice(String filePath) {
        try {
            // Verificar si el archivo existe
            File file = new File(filePath);
            if (!file.exists()) {
                System.err.println("❌ El archivo no existe: " + filePath);
                return;
            }

            // Cargar dataset
            ConverterUtils.DataSource source = new ConverterUtils.DataSource(filePath);
            Instances data = source.getDataSet();

            // Establecer la última columna como clase
            data.setClassIndex(data.numAttributes() - 1);

            // Guardar el formato del dataset para futuras predicciones
            datasetFormat = new Instances(data, 0);

            // Entrenar el modelo RandomTree
            tree = new RandomTree();
            tree.setNumFolds(10);
            tree.buildClassifier(data);

            // Evaluar el modelo con validación cruzada
            Evaluation eval = new Evaluation(data);
            eval.crossValidateModel(tree, data, 10, new Random(1));

            // Imprimir información del modelo
            System.out.println("🔹 Modelo RandomTree:\n" + tree);
            System.out.println("\n=== 🔎 Evaluación del Modelo ===");
            System.out.println(eval.toSummaryString());
            System.out.println(eval.toMatrixString("\n=== 🏆 Matriz de Confusión ===\n"));

            //linea para ver la cofiguracion del modelo
            //System.out.println("Opciones del modelo: " + Arrays.toString(tree.getOptions()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Realiza una predicción a partir de un String con los datos separados por comas

    public static void predict(String inputData) {
        if (tree == null || datasetFormat == null) {
            System.err.println("❌ ¡Error! El modelo no ha sido entrenado aún.");
            return;
        }

        try {
            // Convertir el String en una instancia válida para Weka
            Instance newInstance = createInstanceFromString(inputData);

            // Hacer la predicción
            double predictionIndex = tree.classifyInstance(newInstance);
            String predictedClass = datasetFormat.classAttribute().value((int) predictionIndex);

            System.out.println("\n🔮 **Predicción del modelo:** " + predictedClass);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Convierte un String con los valores en una instancia de Weka
    private static Instance createInstanceFromString(String inputData) {
        // Dividir el string en sus valores individuales
        String[] values = inputData.split(",");

        // Crear una nueva instancia con la misma estructura que el dataset original
        Instance instance = new DenseInstance(datasetFormat.numAttributes());

        // Asignar cada valor a la instancia
        for (int i = 0; i < values.length; i++) {
            String cleanValue = values[i].trim().replace("'", ""); // ❗ Eliminar espacios y comillas/apóstrofes

            if (datasetFormat.attribute(i).isNumeric()) {
                instance.setValue(datasetFormat.attribute(i), Double.parseDouble(cleanValue));
            } else {
                instance.setValue(datasetFormat.attribute(i), cleanValue);
            }
        }

        // Asociar la instancia con la estructura del dataset
        instance.setDataset(datasetFormat);
        return instance;
    }
}

