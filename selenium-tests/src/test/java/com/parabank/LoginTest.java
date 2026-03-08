package com.parabank;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class LoginTest {

    WebDriver driver; // create the driver instance variable

@BeforeClass
public void setup() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless");
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
    driver = new ChromeDriver(options);
    driver.manage().window().maximize();
}

    @Test
    public void validLoginTest() {
        // Go to parabank
        driver.get("https://parabank.parasoft.com/parabank/index.htm"); // naviagte to the login page

        // Find username and password fields and login button
        WebElement username = driver.findElement(By.name("username"));
        WebElement password = driver.findElement(By.name("password"));
        WebElement loginBtn = driver.findElement(By.xpath("//input[@value='Log In']"));

        // Enter credentials
        username.sendKeys("john");
        password.sendKeys("demo");
        loginBtn.click();

        // Wait up to 10 seconds for title to contain "Accounts"
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleContains("Accounts"));

        // Verify we landed on the account page
        Assert.assertTrue(driver.getTitle().contains("Accounts"), 
            "Login failed - not on accounts page");
    }

    @Test
    public void invalidLoginTest() {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");

        WebElement username = driver.findElement(By.name("username"));
        WebElement password = driver.findElement(By.name("password"));
        WebElement loginBtn = driver.findElement(By.xpath("//input[@value='Log In']"));

        username.sendKeys("wronguser");
        password.sendKeys("wrongpass");
        loginBtn.click();

        
        // Verify error message appears
        WebElement error = driver.findElement(By.className("error"));
        Assert.assertTrue(error.isDisplayed(), 
            "Error message not shown for invalid login");
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}