package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashMap;

public class JsonUtil {

    public static LinkedHashMap<String, Object> transformDotLineToMap(LinkedHashMap<String, String> map) {

        LinkedHashMap<String, Object> resultMap = new LinkedHashMap<>();

        map.entrySet().stream().forEach(entry -> {
            String[] jsonHierarchy = entry.getKey().split("\\.");
            recursiveJsonHierarchy(resultMap, entry.getValue(), jsonHierarchy, 0);
        });
        return resultMap;
    }

    public static LinkedHashMap<String, Object> recursiveJsonHierarchy(LinkedHashMap<String, Object> bottom, String nodeValue, String[] jsonHierarchy, int nextIndex) {

        LinkedHashMap<String, Object> currentLoop = bottom;
        String key = jsonHierarchy[nextIndex];

        // Last index
        if (nextIndex >= jsonHierarchy.length - 1) {
            currentLoop.put(key, nodeValue);
            return bottom;
        } else {
            if (!currentLoop.containsKey(key)) {
                LinkedHashMap<String, Object> innerMap = new LinkedHashMap<>();
                currentLoop.put(key, innerMap);
                return recursiveJsonHierarchy(innerMap, nodeValue, jsonHierarchy, ++nextIndex);
            } else {
                currentLoop = (LinkedHashMap<String, Object>) currentLoop.get(key);
                return recursiveJsonHierarchy(currentLoop, nodeValue, jsonHierarchy, ++nextIndex);
            }
        }

    }

    public static void writeJson(LinkedHashMap<String, Object> transformedValues, Path filePath) throws IOException {
        try (Writer writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
                StandardOpenOption.WRITE)) {
            Gson gson = getGson();
            gson.toJson(transformedValues, writer);
        }
    }

    private static Gson getGson() {
        return new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    }

}