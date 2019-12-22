package avtostavka.communication;

public class ChatId {
    private static ChatId ourInstance = new ChatId();

    public static ChatId getInstance() {
        return ourInstance;
    }

    private String handballChat = "-1001305871455";

    private String basketballChat = "-1001170322134";

    private String footballChat = "-1001119378456";

    private ChatId() {
    }

    public String getHandballChat() {
        return handballChat;
    }

    public String getBasketballChat() {
        return basketballChat;
    }

    public String getFootballChat() {
        return footballChat;
    }
}
