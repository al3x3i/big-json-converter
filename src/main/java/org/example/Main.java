package org.example;

import java.nio.file.Path;

import static java.util.Objects.isNull;
import static org.example.JsonBuilderFromFile.buildJsonFromText;

public class Main {

    public static void main(String[] args) {

        String[] filePaths = parseArguments(args);

        Path inFilename = Path.of(filePaths[0]);
        Path outFilename = Path.of(filePaths[1]);

        buildJsonFromText(inFilename, outFilename);

        System.out.println("Completed!");

    }

    private static String[] parseArguments(String... args) {
        String inFilename = null;
        String outFilename = null;

        for (String arg : args) {
            String[] argType = arg.split("\\=");
            switch (argType[0]) {
                case "in":
                    inFilename = argType[1];
                    break;
                case "out":
                    outFilename = argType[1];
                    break;
            }
        }

        if (isNull(inFilename) || isNull(outFilename)) {
            throw new RuntimeException("\nError, please provide 'in' and 'out' argument files!\n");
        }
        return new String[]{inFilename, outFilename};
    }

}

