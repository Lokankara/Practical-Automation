package org.softserve.academy.runner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class Helper {
    private Helper() {
    }

    private static class SingletonHelper {
        private static final Helper INSTANCE = new Helper();
    }

    public static Helper getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public List<String> readCsvFile(String resourcePath) {
        InputStream inputStream = getClass().getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new RuntimeException("Resource not found: " + resourcePath);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().toList();
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file", e);
        }
    }
}
