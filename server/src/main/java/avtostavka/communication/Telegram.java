package avtostavka.communication;

import avtostavka.data.Bet;
import com.google.common.eventbus.Subscribe;

public class Telegram {
    public static String handballChat = "-1001305871455";

    public static String basketball12Chat = "-1001170322134";

    public static String footballChat = "-1001119378456";

    public static String volleyballChat = "-100";

    public static String tennisChat = "-1001288960361";

    @Subscribe
    void SendMessage(Bet message) {

    }
}
