package com.softserve.edu.tests;

import com.softserve.edu.reporter.LoggerUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.stream.Stream;

@Execution(ExecutionMode.CONCURRENT)
public class ParallelTest {

    @BeforeAll
    public static void setup() {
        LoggerUtils.logInfo("@BeforeAll executed, ThreadId = " + Thread.currentThread().getName());
    }

    @AfterAll
    public static void tear() {
        LoggerUtils.logInfo("@AfterAll executed, ThreadId = " + Thread.currentThread().getName());
    }

    @BeforeEach
    public void setupThis() {
        LoggerUtils.logInfo("\t@BeforeEach executed, ThreadId = " + Thread.currentThread().getName());
    }

    @AfterEach
    public void tearThis() {
        LoggerUtils.logInfo("\t@AfterEach executed, ThreadId = " + Thread.currentThread().getName());
    }

    @Test
    void testOne() {
        LoggerUtils.logInfo("\t\t@Test testOne(), ThreadId = " + Thread.currentThread().getName());
    }

    @Test
    void testTwo() {
        LoggerUtils.logInfo("\t\t@Test testTwo(), ThreadId = " + Thread.currentThread().getName());
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
    public void testThree(int[] arr, int num) {
        LoggerUtils.logInfo("\t\t@Test testThree(), ThreadId = " + Thread.currentThread().getId() + "  num = " + num);
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
        LoggerUtils.logInfo("\t\t@Test testThree(), ThreadId = ", 
                Thread.currentThread().getName(), "  sum =", String.valueOf(sum));
        
        Assertions.assertEquals(sum, a + b);
    }

    @DisplayName("Should calculate the correct sum")
    @ParameterizedTest(name = "{index} => urlProvider={0}")
    @MethodSource("urlProvider")
    void testFive(String url) {
        LoggerUtils.logInfo("t@Test testThree()", "ThreadId = ",
                Thread.currentThread().getName(), "  url = ", url);
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get(url);

        driver.quit();
    }

    @Test
    void testSix() {
        LoggerUtils.logInfo("\t\t\t@Test testTwo(), ThreadId = ",
                Thread.currentThread().getName());
        // From Maven
        LoggerUtils.logInfo("\t\t\tsurefire.java.version = "
                + System.getProperty("surefire.application.password"));
        // From OS
        LoggerUtils.logInfo("\t\t\tSystem.getenv(\"JAVA_HOME\") = ",
                System.getenv("JAVA_HOME"));
        LoggerUtils.logInfo("\t\t\tSystem.getenv(\"DEFAULT_PASS\") = ", 
                System.getenv("DEFAULT_PASS"));
        // From Eclipse/Idea
        LoggerUtils.logInfo("\t\t\tSystem.getenv().MY_IDE = ", 
                System.getenv().get("MY_IDE"));
    }
}
