package avtostavka.communication;

import avtostavka.data.Bet;
import com.google.common.eventbus.Subscribe;

public class Telegram {
    private static String handballChat = "-1001305871455";

    private static String basketballChat = "-1001170322134";

    private static String footballChat = "-1001119378456";

    @Subscribe
    void SendMessage(Bet message) {

    }
}
