package com.softserve.edu.teachua.tools;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.opencsv.bean.CsvToBeanBuilder;
import com.softserve.edu.teachua.exception.FileIOException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public final class ResourceUtils {
    public static final String RESOURCES = "src/test/resources/";
    public static final String LIST_USERS = "users.json";
    private static final String TOKEN_FILE = "token.txt";
    public static final String SIGN_USER_CSV = "sign_user.csv";
    public static final String NEW_USER_JSON = "registered_user.json";
    public static final String SIGNIN_USER_SCHEMA_JSON = "signin_user_schema.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final CsvMapper mapper = new CsvMapper();

    private ResourceUtils() {
    }

    private static class ResourceUtilsHolder {
        private static final ResourceUtils INSTANCE = new ResourceUtils();
    }

    public static ResourceUtils get() {
        return ResourceUtilsHolder.INSTANCE;
    }

    public <T> List<T> convertCsvToEntity(String path, Class<T> clazz) {
        try (FileReader reader = new FileReader(path)) {
            return new CsvToBeanBuilder<T>(reader)
                    .withType(clazz)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .parse();
        } catch (IOException e) {
            throw new FileIOException(e.getMessage());
        }
    }

    public String readTokenFromFile() {
        StringBuilder tokenBuilder = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(TOKEN_FILE);
             InputStreamReader streamReader = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(streamReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                tokenBuilder.append(line);
            }
        } catch (IOException e) {
            throw new FileIOException(e.getMessage());
        }
        return tokenBuilder.toString();
    }

    public <T> void saveAsJson(String filePath, T dto) {
        try {
            objectMapper.writeValue(new File(RESOURCES + filePath), dto);
            System.out.printf("Saved to %s as json: %s", filePath, dto);
        } catch (Exception e) {
            throw new FileIOException("Failed to save dto as JSON", e);
        }
    }

    public <T> void saveAsJson(String filePath, List<T> list) {
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.writeValue(new File(RESOURCES + filePath), list);
        } catch (IOException e) {
            throw new FileIOException("Error saving list as JSON", e);
        }
    }

    public <T> T convertJsonToEntity(String filename, Class<T> clazz) {
        try (InputStream jsonStream = new FileInputStream(RESOURCES + filename)) {
            T t = objectMapper.readValue(jsonStream, clazz);
            System.out.printf("Converted Json to Entity: %s%n", t);
            return t;
        } catch (IOException e) {
            throw new FileIOException("Failed to convert JSON to object: " + e.getMessage());
        }
    }

    public <T> List<? extends T> convertJsonToList(String filePath, Class<T> clazz) {
        try {
            return objectMapper.readValue(new File(RESOURCES + filePath),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            throw new RuntimeException("Error converting JSON to entity list: " + e.getMessage());
        }
    }

    public <T> void saveRecordAsCsv(String filename, T dto) {
        try {
            CsvSchema schema = mapper.schemaFor(dto.getClass())
                    .withHeader()
                    .withColumnSeparator(',')
                    .withoutQuoteChar();
            mapper.writer(schema).writeValue(new File(RESOURCES + filename), dto);
            System.out.printf("Saved record as Csv: %s", dto);
        } catch (IOException e) {
            throw new FileIOException(e.getMessage());
        }
    }

    public void saveTokenToFile(String accessToken) {
        try (FileWriter writer = new FileWriter(RESOURCES + TOKEN_FILE)) {
            writer.write(accessToken);
        } catch (IOException e) {
            throw new FileIOException(e.getMessage());
        }
    }
}
