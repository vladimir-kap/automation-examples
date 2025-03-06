package org.example;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class xLoginTest {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(false)
            );
            Page page = browser.newPage();
            page.navigate("https://x.com/");
            System.out.println(page.title());
            page.click("//a[@href=\"/login\"]");
            page.locator("//input[@name=\"text\"]").fill("test_test@gmail.com");
            page.keyboard().press("Enter");

            //без утверждения этого локатора if else не будет видеть этот локатор (но я не выяснил почему именно так работает) может быть из-за try в начале
            assertThat(page.locator("//input[@data-testid=\"ocfEnterTextTextInput\"]")).isVisible();
            if (page.locator("//input[@data-testid=\"ocfEnterTextTextInput\"]").isVisible()){
                System.out.println("if");
                page.locator("//input[@data-testid=\"ocfEnterTextTextInput\"]").fill("@test_test");
                page.locator("//button[@data-testid=\"ocfEnterTextNextButton\"]").click();
            } else {
                System.out.println("else");
            }

            page.locator("//input[@type=\"password\"]").fill("test_test");
            page.locator("//button[@data-testid=\"LoginForm_Login_Button\"]").click();
            page.locator("//a[@href=\"/home\"]").isVisible();
            /*page.waitForLoadState(LoadState.DOMCONTENTLOADED);*/
            //Ожидаем первый твит
            page.waitForSelector("//article[@data-testid=\"tweet\"]");
            //Таймаут 2 секунды что бы прогрузилось совсем всё
            page.waitForTimeout(1000);
            Page.ScreenshotOptions screenshotOptions = new Page.ScreenshotOptions();
            page.screenshot(screenshotOptions.setPath(Paths.get("./src/screenshots/scr.png")));
        }
    }
}