package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;

    // 1. Define the locators (the "tags" or IDs of the elements on the webpage)
    private By emailField = By.id("email");
    private By loginButton = By.id("loginBtn");
    private By welcomeMessage = By.id("welcomeMessage");

    // 2. Constructor: passes the browser driver from our test into this page class
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // 3. Action methods for the page
    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    public String getWelcomeText() {
        return driver.findElement(welcomeMessage).getText();
    }
}