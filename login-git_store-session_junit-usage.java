//this script stores login session
//also using notations with junit5
//simple login into github - store session, close browser, open another and use store session to look at locator that is visible only for logged user
//also learned launch in order with junit

package org.example;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import java.nio.file.Paths;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SessionStoreWorking {
    // Shared between all tests in this class.
    @Test
    @Order(1)
    void test1_login() {
        Playwright playwright = Playwright.create();
        BrowserContext browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false))
                .newContext();

        Page page = browser.newPage();

        page.navigate("https://github.com/login");
        page.locator("//input[@name=\"login\"]").fill("test_test@gmail.com\n");
        page.locator("//input[@name=\"password\"]").fill("381-GT**a");
        page.locator("//input[@value=\"Sign in\"]").click();
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
            page.navigate("https://github.com/");
            assertThat(page.locator("#dashboard > div > feed-container > div.d-flex.flex-justify-between.flex-items-center > h2")).isVisible();
            System.out.println("logged");
            browser.close();
        }
    }
