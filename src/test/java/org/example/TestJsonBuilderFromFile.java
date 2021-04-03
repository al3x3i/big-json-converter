package org.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

import static org.example.JsonBuilderFromFile.buildJsonFromText;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestJsonBuilderFromFile {

    private final static String IN_FILENAME = "in.txt";
    private final static String OUT_FILENAME = "out.json";

    @AfterAll
    public static void after() {
        // Remove json files after tests
        Path path = getTestFilePath(OUT_FILENAME);
        path.toFile().deleteOnExit();
    }

    @Test
    public void should_read_data_from_json() throws IOException {
        // Given
        Path inFilename = getTestFilePath(IN_FILENAME);

        // When
        LinkedHashMap<String, String> dotKeyTranslation = FilesUtil.readDataFromTextFile(inFilename);

        // Then
        thenVerifyLoadedJson(dotKeyTranslation);
    }


    @Test
    public void should_transform_text_to_json() throws IOException {
        // Given
        LinkedHashMap<String, String> dotKeyTranslation = new LinkedHashMap<>();
        dotKeyTranslation.put("HomePage.menu_faultManagement.title", "Fault Management");
        dotKeyTranslation.put("HomePage.menu_administration.title", "Administration");

        // When
        LinkedHashMap<String, Object> transformedMap = JsonUtil.transformDotLineToMap(dotKeyTranslation);

        // Then
        assertEquals(((LinkedHashMap) ((LinkedHashMap) transformedMap.get("HomePage")).get("menu_faultManagement")).get("title"), "Fault Management");
        assertEquals(((LinkedHashMap) ((LinkedHashMap) transformedMap.get("HomePage")).get("menu_administration")).get("title"), "Administration");
    }


    @Test
    public void should_build_json_file_from_text_file() {
        // Given
        Path inFilename = getTestFilePath(IN_FILENAME);
        Path outFilename = getTestFilePath(OUT_FILENAME);

        // When
        buildJsonFromText(inFilename, outFilename);

        // Then
        thenVerifyFileExist(outFilename);
    }

    private void thenVerifyLoadedJson(LinkedHashMap<String, String> dotKeyTranslation) {
        String key = "HomePage.mainMenu.menu_administration.title";
        String value = "Administration";

        assertTrue(dotKeyTranslation.keySet().size() == 22);
        assertEquals(dotKeyTranslation.get(key), value);
    }

    private void thenVerifyFileExist(Path filePath) {
        File file = filePath.toFile();
        assertTrue(file.exists());
    }

    private static Path getTestFilePath(String fileName) {
        Path resourceDirectory = Paths.get("src", "test", "resources", fileName);
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        return Paths.get(absolutePath);
    }
}