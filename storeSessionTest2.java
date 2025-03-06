package org.example;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.nio.file.Paths;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class storeSessionTest2 {
    // Shared between all tests in this class.
    @Test
    @Order(1)
    void test1_login() {
        Playwright playwright = Playwright.create();
        BrowserContext browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false))
                .newContext();

        Page page = browser.newPage();

        page.navigate("https://x.com/");
        page.waitForTimeout(1000);
        page.locator("//button[@role=\"button\"]").locator("nth=1").click();
        assertThat(page.locator("//a[@href=\"/login\"]")).isVisible();
        page.click("//a[@href=\"/login\"]");
        page.locator("//input[@name=\"text\"]").fill("bobahkocmohabt@gmail.com");
        page.keyboard().press("Enter");
        assertThat(page.locator("//input[@data-testid=\"ocfEnterTextTextInput\"]")).isVisible();
        if (page.locator("//input[@data-testid=\"ocfEnterTextTextInput\"]").isVisible()){
            System.out.println("system asked for username");
            page.locator("//input[@data-testid=\"ocfEnterTextTextInput\"]").fill("@leBOBAH");
            page.locator("//button[@data-testid=\"ocfEnterTextNextButton\"]").click();
        } else {
            System.out.println("system didn't asked for username");
        }
        page.locator("//input[@type=\"password\"]").fill("381-TWTTR**");
        page.locator("//button[@data-testid=\"LoginForm_Login_Button\"]").click();
        //saving login session:
        browser.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get("playwright/.auth/auth.json")));
        browser.close();
    }


    /*@Test
    @Order(2)
    void test2_testingSequence() {
        context = browser.newContext(
                new Browser.NewContextOptions().setStorageStatePath(Paths.get("playwright/.auth/state.json")));
    }*/

        @Test
        @Order(3)
        void test3_z () {
            Playwright playwright = Playwright.create();
            BrowserContext browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false))
                    .newContext(new Browser.NewContextOptions().setStorageStatePath(Paths.get("playwright/.auth/auth.json")));
            Page page = browser.newPage();
            page.navigate("https://x.com/home");
            page.waitForTimeout(30000);
            assertThat(page.locator("//a[@href=\"/home\"]")).isVisible();
            System.out.println("logged");
            browser.close();
        }
    }
