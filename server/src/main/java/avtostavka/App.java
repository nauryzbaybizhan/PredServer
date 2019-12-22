package avtostavka;

import avtostavka.communication.Rabbit;
import avtostavka.communication.Telegram;
import avtostavka.strategies.basketball.FaultStrategy;
import avtostavka.strategies.volleyball.VolleyBallStrategy;
import com.google.common.eventbus.AsyncEventBus;

import java.util.concurrent.Executors;

public class App {
    public static void main(String[] args) {
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
