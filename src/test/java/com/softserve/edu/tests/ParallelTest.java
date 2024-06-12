package com.softserve.edu.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.bouncycastle.util.Arrays;
import org.junit.jupiter.api.*;
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
        System.out.println("@BeforeAll executed, ThreadId = " + Thread.currentThread().getId());
    }

    @AfterAll
    public static void tear() {
        System.out.println("@AfterAll executed, ThreadId = " + Thread.currentThread().getId());
    }

    @BeforeEach
    public void setupThis() {
        System.out.println("\t@BeforeEach executed, ThreadId = " + Thread.currentThread().getId());
    }

    @AfterEach
    public void tearThis() {
        System.out.println("\t@AfterEach executed, ThreadId = " + Thread.currentThread().getId());
    }

    @Test
    public void testOne() {
        System.out.println("\t\t@Test testOne(), ThreadId = " + Thread.currentThread().getId());;
    }

    @Test
    public void testTwo() {
        System.out.println("\t\t@Test testTwo(), ThreadId = " + Thread.currentThread().getId());
    }


    public static Object[][] numbers() {
        return new Object[][]{
                {new int[]{1, 2, 3, 4, 5}, 3},
                {new int[]{5, 4, 3, 2, 1}, 4},
                {new int[]{1, 2, 3, 4, 10}, 10}
        };
    }

    @ParameterizedTest
    @MethodSource("numbers")
    public void testThree(int[] arr, int num) {
        System.out.println("\t\t@Test testThree(), ThreadId = " + Thread.currentThread().getId() + "  num = " + num);
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
    public void testFour(int a, int b, int sum) {
        System.out.println("\t\t@Test testThree(), ThreadId = " + Thread.currentThread().getId() + "  sum = " + sum);
        Assertions.assertEquals(sum, a + b);
    }

    private static Stream<Arguments> urlProvider() {
        return Stream.of(
                Arguments.of("http://speak-ukrainian.eastus2.cloudapp.azure.com/dev/"),
                Arguments.of("https://speak-ukrainian.org.ua/")
        );
    }

    @DisplayName("Should calculate the correct sum")
    @ParameterizedTest(name = "{index} => urlProvider={0}")
    @MethodSource("urlProvider")
    public void testFive(String url) throws InterruptedException {
        System.out.println("\t\t@Test testThree(), ThreadId = " + Thread.currentThread().getId()
                + "  url = " + url);
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // 0 by default
        Thread.sleep(2000); // For Presentation
        driver.get(url);
        Thread.sleep(2000); // For Presentation
        //
        //driver.quit();
    }

    @Test
    public void testSix() {
        System.out.println("\t\t\t@Test testTwo(), ThreadId = "
                + Thread.currentThread().getId());
        // From Maven
        System.out.println("\t\t\tsurefire.java.version = "
                + System.getProperty("surefire.application.password"));
        // From OS
        System.out.println("\t\t\tSystem.getenv(\"JAVA_HOME\") = "
                + System.getenv("JAVA_HOME"));
        System.out.println("\t\t\tSystem.getenv(\"DEFAULT_PASS\") = "
                + System.getenv("DEFAULT_PASS"));
        // From Eclipse/Idea
        System.out.println("\t\t\tSystem.getenv().MY_IDE = "
                + System.getenv().get("MY_IDE"));
    }
}
