package com.parabank;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginTest {

    WebDriver driver; // create the driver instance variable

    @BeforeClass
    public void setup() {
        // Selenium 4 manages ChromeDriver automatically - no setup needed
        driver = new ChromeDriver(); // assign a browser
        driver.manage().window().maximize(); // maximize the browser window
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