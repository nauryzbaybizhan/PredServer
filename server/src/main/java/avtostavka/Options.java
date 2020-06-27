package avtostavka;

import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;

public class Options {

    private static Options ourInstance = new Options();

    public static Options getInstance() {
        return ourInstance;
    }

    private Options() {
    }

    public ChromeOptions getOptions(boolean headless) {
        ChromeOptions chromeOptions = new ChromeOptions();
        if (headless) chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("start-maximized");
        chromeOptions.addArguments("--lang=ru");
        System.setProperty("webdriver.chrome.driver", "C:/Users/Bizhan/soft/chromedriver.exe");
        HashMap<String, Object> images = new HashMap<String, Object>();
        images.put("images", 2);
        HashMap<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.managed_default_content_settings.images", 2);
        chromeOptions.setExperimentalOption("prefs", prefs);
        return chromeOptions;
    }
}
