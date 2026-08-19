package tests;

import api.ApiClient;
import database.DatabaseManager;
import io.restassured.response.Response;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ExtentReportManager;

public class E2EUserFlowTest {
    private WebDriver driver;
    private static ExtentReports extent;
    private ExtentTest extentTest;

    @BeforeClass
    public void setupSuite() {
        extent = ExtentReportManager.getInstance();
    }

    @BeforeMethod
    public void setup(ITestResult result) {
        // Start Extent Report test tracking
        extentTest = extent.createTest(result.getMethod().getMethodName());
        ExtentReportManager.test.set(extentTest);

        // 1. Connect to Database
        DatabaseManager.connect();
        extentTest.info("Database connected successfully.");

        // 2. Configure and Initialize Chrome (Headless mode for Jenkins & Local)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        extentTest.info("Chrome browser initialized in headless mode.");
    }

    @Test
    public void testUserCreationAndLoginFlow() {
        // Step 1: API Request
        String apiEndpoint = "https://jsonplaceholder.typicode.com/posts";
        String requestBody = "{\"title\": \"foo\", \"body\": \"bar\", \"userId\": 1}";

        extentTest.info("Sending POST request to API: " + apiEndpoint);
        Response response = ApiClient.sendPostRequest(apiEndpoint, requestBody);

        Assert.assertEquals(response.getStatusCode(), 201, "API failed to create user!");
        extentTest.pass("API successfully returned status 201 Created.");

        // Step 2: Selenium UI Interaction
        extentTest.info("Navigating to Google to verify browser test.");
        driver.get("https://www.google.com");

        String title = driver.getTitle();
        Assert.assertTrue(title.contains("Google"), "Browser failed to load Google!");
        extentTest.pass("UI verified successfully. Page title: " + title);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            extentTest.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentTest.pass("Test passed successfully.");
        }

        if (driver != null) {
            driver.quit();
        }
        DatabaseManager.close();
    }

    @AfterClass
    public void teardownSuite() {
        // Flush the report so it writes everything to the HTML file
        if (extent != null) {
            extent.flush();
        }
    }
}