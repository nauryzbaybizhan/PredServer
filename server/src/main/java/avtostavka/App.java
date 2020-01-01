package avtostavka;

import avtostavka.communication.Rabbit;
import avtostavka.communication.Telegram;
import avtostavka.strategies.basketball.FaultStrategy;
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

    public static void main(String[] args) throws IOException {
        try {
            Gson gson = new Gson();
            FileReader fileReader = new FileReader("config.json", StandardCharsets.UTF_8);
            JsonReader reader = new JsonReader(fileReader);
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
