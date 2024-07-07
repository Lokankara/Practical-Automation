package com.softserve.edu.teachua.tests;

import com.softserve.edu.bootstrap.BootstrapFormControlsPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BootstrapFormControlsPageTest extends TestRunner {
    private BootstrapFormControlsPage formControls;

    @BeforeEach
    void setUp() {
        WebDriver driver = driverWrapper.getDriver();
        formControls = new BootstrapFormControlsPage(driver);
        driver.get("https://getbootstrap.com/docs/4.0/components/forms/#form-controls");
    }

    @ParameterizedTest(name = "Test {index} Dropdown Selection by Text: {0}")
    @CsvSource({"1", "2", "3", "4", "5"})
    @DisplayName("Test Dropdown Selection by Text within Shadow DOM")
    void testDropdownSelectionByText(String option) {
        formControls.selectOptionByText(option);
        assertEquals(option, formControls.getOptionText(),
                "Selected option text does not match");
    }

    @ParameterizedTest(name = "Test {index} Dropdown Selection by Index: {0}")
    @CsvSource({"0", "1", "2", "3", "4"})
    @DisplayName("Test Dropdown Selection by Index within Shadow DOM by First Selected Option")
    void testDropdownSelectionByIndex(int optionIndex) {
        formControls.selectOptionByIndex(optionIndex);
        assertEquals(String.valueOf(optionIndex + 1),
                formControls.getSelect().getFirstSelectedOption().getText().trim(),
                "Selected option index does not match");
    }

    @ParameterizedTest(name = "Test {index} Dropdown Selection by Index: {0}")
    @CsvSource({"1", "1", "2", "3", "4"})
    @DisplayName("Test Dropdown Selection by Index within Shadow DOM")
    void testDropdownSelectionByIndexJS(int optionIndex) {
        formControls.selectOptionByIndex(optionIndex);
        assertEquals(String.valueOf(optionIndex + 1),
                formControls.getOptionText(),
                "Selected option index does not match");
    }

    static Stream<List<Integer>> provideTestCases() {
        return Stream.of(
                List.of(0),
                Arrays.asList(0, 2),
                Arrays.asList(1, 3),
                Arrays.asList(2, 3),
                Arrays.asList(0, 4),
                Arrays.asList(0, 2, 4),
                Arrays.asList(0, 1, 2, 3, 4)
        );
    }

    @ParameterizedTest(name = "Test {index} MultipleSelectOptions by Index: {0}")
    @MethodSource("provideTestCases")
    void testMultipleSelectOptions(List<Integer> optionIndexes) {
        formControls.selectMultipleOptionsByIndex(optionIndexes);
        Select dropdown = formControls.getMultiSelect();
        IntStream.range(0, optionIndexes.size()).forEach(i ->
                assertEquals(String.valueOf(optionIndexes.get(i) + 1),
                        dropdown.getAllSelectedOptions().get(i).getText().trim(),
                        "Selected option index does not match"));
    }

    static Stream<List<String>> provideValueTestData() {
        return Stream.of(
                List.of("1"),
                Arrays.asList("1", "3", "5"),
                Arrays.asList("2", "4"),
                Arrays.asList("1", "2", "3", "4", "5")
        );
    }

    @ParameterizedTest(name = "Test {index} MultipleSelectOptions by Index: {0}")
    @MethodSource("provideValueTestData")
    @DisplayName("Test Multiple Selection Options by Value")
    void testMultipleSelectOptionsByValue(List<String> optionTexts) {
        formControls.selectMultipleOptionsByText(optionTexts);
        List<String> selectedOptionTexts = formControls.getSelectedOptionValues();
        assertEquals(optionTexts.size(), selectedOptionTexts.size(), "Number of selected options does not match");
        optionTexts.forEach(text -> assertTrue(selectedOptionTexts.contains(text), "Selected option text '" + text + "' not found"));
    }
}
