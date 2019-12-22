package avtostavka.bookmakers;

import avtostavka.Config;
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BookParser<T> {

    public static String fonBet = "https://www.fonbet.ru/";

    public static String xStavka = "https://1xstavka.ru/";

    public void init(String ref) {}

    public void close() {}

    public void parseLine(ConcurrentHashMap<String, T> retBasketball, Config config, int i) {}

    public void parseMainPage(ConcurrentHashMap<String, T> retBasketball, Config config, int i) {}

    public void checkMatches(ConcurrentHashMap<String, T> retHandball) {}

    public void writeStat(T value) {}

    public void updateCounter() {
//        Gson gson = new Gson();
//        String newConfig = gson.toJson(Main.config);
//        try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("config.json", false),
//                StandardCharsets.UTF_8)) {
//            osw.write(newConfig);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
