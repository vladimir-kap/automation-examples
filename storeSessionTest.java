package org.example;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class storeSessionTest {
    // Shared between all tests in this class.
    static Playwright playwright;
    static Browser browser;

    // New instance for each test method.
    BrowserContext context;
    Page page;

    @Test
    @Order(1)
    void test1_login() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
        page.navigate("https://x.com/");
        assertThat(page.locator("//a[@href=\"/login\"]")).isVisible();
        page.click("//a[@href=\"/login\"]");
        page.locator("//input[@name=\"text\"]").fill("test_test@gmail.com");
        page.keyboard().press("Enter");
        assertThat(page.locator("//input[@data-testid=\"ocfEnterTextTextInput\"]")).isVisible();
        if (page.locator("//input[@data-testid=\"ocfEnterTextTextInput\"]").isVisible()){
            System.out.println("system asked for username");
            page.locator("//input[@data-testid=\"ocfEnterTextTextInput\"]").fill("@test_test");
            page.locator("//button[@data-testid=\"ocfEnterTextNextButton\"]").click();
        } else {
            System.out.println("system didn't asked for username");
        }
        page.locator("//input[@type=\"password\"]").fill("test_test");
        page.locator("//button[@data-testid=\"LoginForm_Login_Button\"]").click();
        //saving login session:
        context.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get("playwright/.auth/state.json")));
        context.close();
    }

    /*@Test
    @Order(2)
    void test2_testingSequence() {
        context = browser.newContext(
                new Browser.NewContextOptions().setStorageStatePath(Paths.get("playwright/.auth/state.json")));
    }*/

    @Test
    @Order(3)
    void test3_z() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext(
                new Browser.NewContextOptions().setStorageStatePath(Paths.get("playwright/.auth/state.json")));
        page = context.newPage();
        page.navigate("https://x.com/home");
        assertThat(page.locator("//a[@href=\"/home\"]")).isVisible();
        page.waitForTimeout(5000);
        Page.ScreenshotOptions screenshotOptions = new Page.ScreenshotOptions();
        page.screenshot(screenshotOptions.setPath(Paths.get("./src/screenshots/scr.png")));
        context.close();
        playwright.close();
    }
}
