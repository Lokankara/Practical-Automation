package com.softserve.edu.teachua.tools;

import com.opencsv.CSVWriter;
import com.softserve.edu.teachua.data.Cities;
import com.softserve.edu.teachua.tests.TestRunner;
import com.softserve.edu.teachua.tests.provider.CityArgumentProvider;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ParseDataTest extends TestRunner {

    @ParameterizedTest
    @ArgumentsSource(CityArgumentProvider.class)
    void testGetClubsDataAndSaveToFile(Cities city) throws IOException {
        loadApplication().selectCityClubs(city);
        String script = "return document.querySelectorAll('.ant-card-body')";
        List<WebElement> cardBodies = (List<WebElement>) js.executeScript(script);

        String csvFile = "src/test/resources/csv/" + city.name() + "_club_data.csv";
        FileWriter fileWriter = new FileWriter(csvFile);
        CSVWriter csvWriter = new CSVWriter(fileWriter);

        for (WebElement cardBody : cardBodies) {
            String title = cardBody.findElement(By.cssSelector(".name")).getText().trim();
            String description = cardBody.findElement(By.cssSelector(".description")).getText().trim();
            String[] data = {"_CLUB" + "(Cities." + city.getCity() + ", \"" + title + "\", \"" + extractFirstSentence(description) + "\")"};
            csvWriter.writeNext(data);
        }
        csvWriter.close();
        fileWriter.close();
    }

    private String extractFirstSentence(String description) {
        Pattern pattern = Pattern.compile("^(.*?)([.?!])\\s+");
        Matcher matcher = pattern.matcher(description);
        return matcher.find() ? matcher.group(1) : description;
    }
}
