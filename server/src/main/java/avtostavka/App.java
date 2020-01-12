package avtostavka;

import avtostavka.communication.Rabbit;
import avtostavka.communication.Telegram;
import avtostavka.strategies.basketball.FoulStrategy;
import avtostavka.strategies.basketball.Strategy12;
import avtostavka.strategies.football.FootballStrategies;
import avtostavka.strategies.handball.TotalStrategies;
import avtostavka.strategies.volleyball.VolleyBallStrategy;
import com.google.common.eventbus.AsyncEventBus;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

public class App {

    public static Config config;
    public static SevenBasketConfig sevenBasketConfig;

    public static void main(String[] args) throws IOException {
        try {
            Gson gson = new Gson();

            FileReader fileReader = new FileReader("config.json", StandardCharsets.UTF_8);
            JsonReader reader = new JsonReader(fileReader);
            config = gson.fromJson(reader, Config.class);

            FileReader fileReader2 = new FileReader("whiteList.json", StandardCharsets.UTF_8);
            JsonReader reader2 = new JsonReader(fileReader2);
            sevenBasketConfig = gson.fromJson(reader2, SevenBasketConfig.class);

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.in.read();
        }
        Object[] receivers = {new Rabbit(), new Telegram(), new FoulStrategy(),
                new VolleyBallStrategy(), new FootballStrategies(), new TotalStrategies(), new Strategy12()};
        for (Object receiver : receivers) {
            eventBus.register(receiver);
        }
    }

    private static AsyncEventBus eventBus = new AsyncEventBus(Executors.newFixedThreadPool(15));

    public static AsyncEventBus getEventBus() {
        return eventBus;
    }
}
