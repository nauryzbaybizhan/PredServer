package avtostavka;

import avtostavka.Threads.*;
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

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

public class App {

    public static Config config;
    public static SevenBasketConfig sevenBasketConfig;

    public static void main(String[] args) throws IOException, InterruptedException {
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream("config.json"), StandardCharsets.UTF_8));
             BufferedReader bf2 = new BufferedReader(new InputStreamReader(new FileInputStream("whiteList.json"), StandardCharsets.UTF_8))) {
            Gson gson = new Gson();

            JsonReader reader = new JsonReader(bf);
            config = gson.fromJson(reader, Config.class);

            JsonReader reader2 = new JsonReader(bf2);
            sevenBasketConfig = gson.fromJson(reader2, SevenBasketConfig.class);

            reader.close();
            reader2.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.in.read();
        }
        Object[] receivers = {new Rabbit(), new Telegram(), new FoulStrategy(),
                new VolleyBallStrategy(), new FootballStrategies(), new TotalStrategies(), new Strategy12()};
        for (Object receiver : receivers) {
            eventBus.register(receiver);
        }
        new FonBetLineThread().parseLine();
        Thread.sleep(4000);
        new FonBetBasketballThread().startFonBetWork();
        new FonBetFootballThread().startFonBetWork();
        new FonBetTennisThread().startFonBetWork();
        new FonBetVolleyballThread().startFonBetWork();
    }

    private static AsyncEventBus eventBus = new AsyncEventBus(Executors.newFixedThreadPool(15));

    public static AsyncEventBus getEventBus() {
        return eventBus;
    }
}
