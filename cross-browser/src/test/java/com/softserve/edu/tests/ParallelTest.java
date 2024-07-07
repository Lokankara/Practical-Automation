package com.softserve.edu.tests;

import com.softserve.edu.reporter.LoggerUtils;
import com.softserve.edu.runner.BaseTestSuite;
import org.bouncycastle.util.Arrays;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Execution(ExecutionMode.CONCURRENT)
public class ParallelTest extends BaseTestSuite {

    @BeforeAll
    public static void setup() {
        LoggerUtils.logInfo("@BeforeAll executed" + Thread.currentThread().getName());
    }

    @AfterAll
    public static void tear() {
        LoggerUtils.logInfo("@AfterAll executed" + Thread.currentThread().getName());
    }

    @BeforeEach
    void setupThis() {
        LoggerUtils.logInfo("@BeforeEach executed" + Thread.currentThread().getName());
    }

    @AfterEach
    void tearThis() {
        LoggerUtils.logInfo("@AfterEach executed" + Thread.currentThread().getName());
    }

    @Test
    void testOne() {
        LoggerUtils.logInfo("@Test testOne()" + Thread.currentThread().getName());
    }

    @Test
    void testTwo() {
        LoggerUtils.logInfo("@Test testTwo()" + Thread.currentThread().getName());
    }


    public static Object[][] numbers() {
        return new Object[][]{
                {new int[]{1, 2, 3, 4, 5}, 3},
                {new int[]{5, 4, 3, 2, 1}, 4},
                {new int[]{1, 2, 3, 4, 10}, 10}
        };
    }

    private static Stream<Arguments> urlProvider() {
        return Stream.of(
                Arguments.of("http://speak-ukrainian.eastus2.cloudapp.azure.com/dev/"),
                Arguments.of("https://speak-ukrainian.org.ua/")
        );
    }

    @ParameterizedTest
    @MethodSource("numbers")
    void testThree(int[] arr, int num) {
        LoggerUtils.logInfo("@Test testThree()", Thread.currentThread().getName(), "  num = " + num);
        Assertions.assertTrue(Arrays.contains(arr, num), "Array should contain the number");
    }

    private static Stream<Arguments> sumProvider() {
        return Stream.of(
                Arguments.of(1, 1, 2),
                Arguments.of(2, 3, 5)
        );
    }

    @DisplayName("Should calculate the correct sum")
    @ParameterizedTest(name = "{index} => a={0}, b={1}, sum={2}")
    @MethodSource("sumProvider")
    void testFour(int a, int b, int sum) {
        LoggerUtils.logInfo("@Test testThree()",
                Thread.currentThread().getName(), "  sum =", String.valueOf(sum));

        assertEquals(sum, a + b);
    }

    @DisplayName("Should calculate the correct sum")
    @ParameterizedTest(name = "{index} => urlProvider={0}")
    @MethodSource("urlProvider")
    void testFive(String url) {
        LoggerUtils.logInfo("@TestThree()", Thread.currentThread().getName(), "  url = ", url);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        driver.get(url);
        assertEquals(driver.getCurrentUrl(), url);
    }

    @Test
    void testSix() {
        LoggerUtils.logInfo("@Test testTwo()", Thread.currentThread().getName());
        LoggerUtils.logInfo("surefire.java.version", System.getProperty("surefire.application.password"));
        LoggerUtils.logInfo("System.getenv(\"JAVA_HOME\")", System.getenv("JAVA_HOME"));
        LoggerUtils.logInfo("System.getenv(\"DEFAULT_PASS\")", System.getenv("DEFAULT_PASS"));
        LoggerUtils.logInfo("System.getenv().MY_IDE", System.getenv().get("MY_IDE"));
    }
}
