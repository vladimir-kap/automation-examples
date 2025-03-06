package org.example;
import com.microsoft.playwright.*;
import java.nio.file.Paths;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static java.util.Arrays.asList;

public class Test1 extends Thread {
    private final String browserName;

    private Test1(String browserName) {
        this.browserName = browserName;
    }

    public static void main(String[] args) throws InterruptedException {
        // Create separate playwright thread for each browser.
        for (String browserName: asList("chromium", "webkit", "firefox")) {
            Thread thread = new Test1(browserName);
            thread.start();
        }
    }

    @Override
    public void run() {
        try (Playwright playwright = Playwright.create()) {
            BrowserType browserType = getBrowserType(playwright, browserName);
            Browser browser = browserType.launch(
                    new BrowserType.LaunchOptions().setHeadless(false));
            Page page = browser.newPage();
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
            page.waitForTimeout(10000);
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("user-agent-" + browserName + ".png")));
        }
    }

    private static BrowserType getBrowserType(Playwright playwright, String browserName) {
        switch (browserName) {
            case "chromium":
                return playwright.chromium();
            case "webkit":
                return playwright.webkit();
            case "firefox":
                return playwright.firefox();
            default:
                throw new IllegalArgumentException();
        }
    }
}