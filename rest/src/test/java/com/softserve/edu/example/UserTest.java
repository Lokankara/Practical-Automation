package com.softserve.edu.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softserve.edu.teachua.dao.user.model.SignInResponse;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Random;

class UserTest {
    private static final String BASE_URL = "http://speak-ukrainian.eastus2.cloudapp.azure.com/dev/";
    private static final Long IMPLICITLY_WAIT_SECONDS = 10L;
    private static final Long ONE_SECOND_DELAY = 1000L;

    // Overload
    private static void presentationSleep() {
        presentationSleep(1);
    }

    // Overload
    private static void presentationSleep(int seconds) {
        try {
            Thread.sleep(seconds * ONE_SECOND_DELAY); // For Presentation ONLY
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    void checkCreateNewUser() throws Exception {
        //
        // Random number
        Random rand = new Random();
        String number = String.valueOf(rand.nextInt(100000));

        ObjectMapper mapper = new ObjectMapper();
        //API by okhttp
        OkHttpClient client = new OkHttpClient();
        //
        // Prepare sign up
        SignRequest signRequest = new SignRequest("a1234" + number + "@gmail.com", "SimpleC",
                "SimpleC", "0671234567", "Qwerty_1");
        String jsonBody = mapper.writeValueAsString(signRequest);
        // Sign up
        RequestBody requestBody = RequestBody.create(jsonBody,
                MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url("http://speak-ukrainian.eastus2.cloudapp.azure.com/dev/api/signup")
                //.addHeader("Content-Type", "application/json")
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        String jsonResult = Objects.requireNonNull(response.body()).string();
        SignInResponse signInResponse = mapper.readValue(jsonResult, SignInResponse.class);
        //
        Assertions.assertTrue(response.isSuccessful());
        Assertions.assertEquals(200, response.code());

       Connection con = DriverManager.getConnection(System.getenv("DATASOURCE_URL"), System.getenv("DATASOURCE_USER"), System.getenv("DATASOURCE_PASSWORD"));
        if (con != null) {
            System.out.println("Connection Successful! \n");
        } else {
            System.out.println("Connection ERROR \n");
            System.exit(1);
        }

        //
        // Update
        String query = "UPDATE users SET status=true WHERE id=?;";
        PreparedStatement st = con.prepareStatement(query);
        st.setInt(1, signInResponse.id());
        st.execute();
        //
        // Show Table
        query = "SELECT * FROM users ORDER BY id desc limit 1;";
        st = con.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        int columnCount = rs.getMetaData().getColumnCount();
        //
        // Get information
        for (
                int i = 1;
                i <= columnCount; i++) {
            System.out.print(rs.getMetaData().getColumnName(i) + "\t");
        }
        System.out.println();
        //
        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(rs.getString(i) + "\t");
            }
            System.out.println();
        }
        //

        //
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().
                implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        driver.manage().window().maximize();

        driver.get(BASE_URL);

        driver.findElement(By.cssSelector("div.user-profile span.anticon.anticon-caret-down")).click();

        // Open login window
        driver.findElement(By.cssSelector("li[data-menu-id*='login'] span.ant-dropdown-menu-title-content")).click();

        // Enter email
        driver.findElement(By.id("basic_email")).click();
        driver.findElement(By.id("basic_email")).clear();
        driver.findElement(By.id("basic_email")).sendKeys(signInResponse.email());

        presentationSleep(); // For Presentation
        //
        // Enter password
        driver.findElement(By.id("basic_password")).click();
        driver.findElement(By.id("basic_password")).clear();
        driver.findElement(By.id("basic_password")).sendKeys("Qwerty_1");

        presentationSleep(); // For Presentation
        //
        // Click sign in
        driver.findElement(By.cssSelector("div.login-footer button")).click();

        presentationSleep(); // For Presentation

        //
        List<WebElement> popupMessage = driver.findElements(By.cssSelector("div.ant-message-notice-wrapper span:last-child"));

        presentationSleep(); // For Presentation
        //
        Assertions.assertTrue(popupMessage.get(0).getText().contains("Ви успішно залогувалися!"));

        presentationSleep(); // For Presentation

        driver.findElement(By.cssSelector(".anticon-user")).click();

        presentationSleep();
        driver.findElement(By.cssSelector("li.ant-dropdown-menu-item:nth-child(5) > span:nth-child(1) > div:nth-child(1)")).click();
        //

        driver.quit();
        query = "delete from users where id=?;";
        st = con.prepareStatement(query);
        st.setInt(1, signInResponse.id());
        st.execute();
        rs.close();
        //
        st.close();
        con.close();
        //
    }

}
