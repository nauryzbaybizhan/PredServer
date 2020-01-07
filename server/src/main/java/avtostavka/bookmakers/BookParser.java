package avtostavka.bookmakers;

import avtostavka.App;
import avtostavka.BookmakerDataParser;
import com.google.gson.Gson;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

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
    protected String n = ";";

    public static String fonBet = "https://www.fonbet.ru/";

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
}
