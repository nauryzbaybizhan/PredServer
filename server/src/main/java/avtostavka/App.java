package avtostavka;

import avtostavka.communication.Rabbit;
import avtostavka.communication.Telegram;
import avtostavka.strategies.basketball.FaultStrategy;
import avtostavka.strategies.volleyball.VolleyBallStrategy;
import com.google.common.eventbus.AsyncEventBus;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class App {

    public static Config config;

    public static void main(String[] args) throws IOException {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream("config.json"), StandardCharsets.UTF_8);
            Scanner sc = new Scanner(inputStreamReader);
            String someJson = sc.nextLine();
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new StringReader(someJson));
            reader.setLenient(true);
            config = gson.fromJson(reader, Config.class);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.in.read();
        }
        Object[] receivers = {new Rabbit(), new Telegram(), new FaultStrategy(),
                new VolleyBallStrategy()};
        for (Object receiver : receivers) {
            eventBus.register(receiver);
        }
    }

    private static AsyncEventBus eventBus = new AsyncEventBus(Executors.newFixedThreadPool(15));

    public static AsyncEventBus getEventBus() {
        return eventBus;
    }
}
