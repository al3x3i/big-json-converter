package org.example;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;

public class JsonBuilderFromFile {

    public static void buildJsonFromText(Path inFilePath, Path outFilePath) {
        try {
            LinkedHashMap<String, String> dotKeyTranslation = FilesUtil.readDataFromTextFile(inFilePath);
            LinkedHashMap transformedMap = JsonUtil.transformDotLineToMap(dotKeyTranslation);

            JsonUtil.writeJson(transformedMap, outFilePath);
        } catch (IOException exception) {
            System.out.println("Error, occurred exception while transforming text file to json." + exception.getMessage());
        }
    }
}
