//This script logins into x.com and save screenshot
//Uses try because of construction in documentation
//uses if else because x.com sometimes asks for nickname confirmation
//replaced actual login information with fakes

package org.example;
import com.microsoft.playwright.*;
import java.nio.file.Paths;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class Main {
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
            page.waitForTimeout(3000);
            Page.ScreenshotOptions screenshotOptions = new Page.ScreenshotOptions();
            page.screenshot(screenshotOptions.setPath(Paths.get("./src/screenshots/scr.png")));
        }
    }
}