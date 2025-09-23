package com.example.qa;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UiTests {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final String BASE_URL = "http://localhost:5173"; // update if needed

    // Debug flag - set true if you want pauses during test runs
    private static final boolean DEBUG_MODE = false;

    @BeforeAll
    static void setup() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        driver = new FirefoxDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().setSize(new Dimension(1280, 900));
    }

    @AfterAll
    static void teardown() {
        if (driver != null) driver.quit();
    }

    // Helper pause method for debugging
    private void pause(long millis) {
        if (DEBUG_MODE) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Test @Order(1)
    void login() {
        driver.get(BASE_URL);

        // Wait for the signin page to load
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='email']")));

        pause(1000);

        // Fill in the signin form
        driver.findElement(By.cssSelector("input[type='email']")).sendKeys("cat@cat.com");
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys("cat123");

        pause(1000);

        driver.findElement(By.cssSelector("button.btn.btn-primary")).click();

        // Wait for successful login - should redirect to home page with navbar
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("New Note")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Logout']")));
    }

    @Test @Order(2)
    void createNote() {
        // Assumes still logged in from previous test
        driver.findElement(By.linkText("New Note")).click();

        // Wait for the create page to load
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[placeholder='Note Title']")));

        pause(1000);

        // Fill in the note form
        driver.findElement(By.cssSelector("input[placeholder='Note Title']")).sendKeys("My Selenium Note");
        driver.findElement(By.cssSelector("textarea[placeholder='Write your note here...']"))
                .sendKeys("Content added by Selenium test.");

        pause(1000);

        driver.findElement(By.cssSelector("button.btn.btn-primary")).click();

        // Wait for successful creation - should redirect back to home page
        wait.until(ExpectedConditions.or(
                ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "My Selenium Note"),
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(normalize-space(),'My Selenium Note')]"))
        ));
    }
}
