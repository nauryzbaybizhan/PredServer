package avtostavka.bookmakers;

import avtostavka.App;
import avtostavka.BookmakerDataParser;
import com.google.gson.Gson;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public abstract class BookParser<T> {

    protected ChromeDriver driver;
    protected WebElement webElement;
    protected BookmakerDataParser charCheck = new BookmakerDataParser();
    protected String basketball = "Баскетбол";
    protected String football = "Футбол";
    protected String tennis = "Теннис";
    protected String volleyball = "Волейбол";
    protected String n = ";";

    public static String fonBet = "https://www.fonbet.ru";

    public static String xStavka = "https://1xstavka.ru/";

    public void init(String ref) {
    }

    public void close() {
        driver.quit();
    }

    public void parseLine(int i) {
    }

    public void parseMainPage(int i) {
    }

    public void checkMatches() {
    }

    public void writeStat(T value) {
    }

    public void updateCounter() {
        Gson gson = new Gson();
        String newConfig = gson.toJson(App.config);
        try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("config.json", false),
                StandardCharsets.UTF_8)) {
            osw.write(newConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean clickCurrQuarter() {
        String currentQuarterSelector = "section > div.event-view__inner--2Eg5p > div.ev-tabs--3u3Yz > span:nth-child(2)";
        int i =0;
        boolean clicked;
        do {
            i++;
            if (i == 20) return false;
            try {
                webElement = (new WebDriverWait(driver, 2))
                        .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(currentQuarterSelector)));
                webElement = driver.findElement(By.cssSelector(currentQuarterSelector));
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("var elem=arguments[0]; setTimeout(function() {elem.click();}, 100)", webElement);
                clicked = true;
            } catch (org.openqa.selenium.NoSuchElementException | ElementClickInterceptedException | TimeoutException e) {
                e.printStackTrace();
                clicked = false;
            }
        } while (!clicked);
        return true;
    }
}
