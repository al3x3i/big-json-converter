package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilesUtil {

    private static final Pattern LANGUAGE_NAME_PATTERN = Pattern.compile("(.*)\\s(.*)");
    private static final Pattern TRANSLATION_KEY_TEXT_PATTERN = Pattern.compile("^([^\\s]*)\\s+(.+)");

    public static LinkedHashMap<String, String> readDataFromTextFile(Path filePath) throws IOException {

        isFileExist(filePath);

        LinkedHashMap<String, String> translations = new LinkedHashMap<>();
        try (BufferedReader br = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {

            String languageLine;
            if ((languageLine = br.readLine()) != null) {
                parseLanguageLine(languageLine);
            }

            String line;
            while ((line = br.readLine()) != null) {
                parseAndAppendTranslationLine(line, translations);
            }
        }
        return translations;
    }

    private static void isFileExist(Path filePath) {

        if(!filePath.toFile().exists()) {
            throw new RuntimeException("\nError, text file does not exist!\n");
        }
    }

    private static void parseLanguageLine(String languageLine) {
        Matcher languageMatcher = LANGUAGE_NAME_PATTERN.matcher(languageLine);
        if (languageMatcher.matches()) {
            String label = languageMatcher.group(0);
            String language = languageMatcher.group(1);
        }
    }

    private static void parseAndAppendTranslationLine(String line, LinkedHashMap<String, String> translations) {
        Matcher translationMatcher = TRANSLATION_KEY_TEXT_PATTERN.matcher(line);
        if (translationMatcher.matches() && translationMatcher.groupCount() == 2) {
            String id = translationMatcher.group(1);
            String text = translationMatcher.group(2);
            translations.put(id, text);
        } else {
            System.out.println("Warning, invalid translation:" + line);
        }
    }
}