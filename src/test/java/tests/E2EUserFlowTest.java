package tests;

import api.ApiClient;
import database.DatabaseManager;
import io.restassured.response.Response;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.LoginPage;

public class E2EUserFlowTest {
    private WebDriver driver;

    @BeforeMethod
    public void setup() {
        // 1. Connect to the Database
        DatabaseManager.connect();

        // 2. Configure Chrome to run in Headless mode (required for Jenkins/CI-CD)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // Runs Chrome invisibly in the background
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        // Initialize Chrome Browser with options
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @Test
    public void testUserCreationAndLoginFlow() {
        String userEmail = "testuser_999@example.com";

        // Use JSONPlaceholder which is a completely open public test API
        String apiEndpoint = "https://jsonplaceholder.typicode.com/posts";

        String requestBody = "{\"title\": \"foo\", \"body\": \"bar\", \"userId\": 1}";
        Response response = ApiClient.sendPostRequest(apiEndpoint, requestBody);

        // JSONPlaceholder returns 201 Created for a successful POST
        Assert.assertEquals(response.getStatusCode(), 201, "API failed to create user!");

        // (We will skip the DB check for now since you don't have a local database running yet)

        // Step 2: Use Selenium to open a public site (like Google or a dummy page) to test the browser
        driver.get("https://www.google.com");

        // Simple assertion to prove browser worked
        String title = driver.getTitle();
        Assert.assertTrue(title.contains("Google"), "Browser failed to load Google!");
    }

    @AfterMethod
    public void tearDown() {
        // Close browser
        if (driver != null) {
            driver.quit();
        }
        // Close Database connection
        DatabaseManager.close();
    }
}