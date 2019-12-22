package avtostavka.communication;

import avtostavka.data.Bet;
import com.google.common.eventbus.Subscribe;

public class Telegram {
    @Subscribe
    void SendMessage(Bet message) {

    }
}
