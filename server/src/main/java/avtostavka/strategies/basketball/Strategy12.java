package avtostavka.strategies.basketball;

import avtostavka.App;
import avtostavka.data.BasketballGame;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;

public class Strategy12 {

    @Subscribe
    public void ProcessGame(BasketballGame value) {
        if (!value.isSentStr12() && value.getGameTotal() <= (value.getGameInitTotal()) -26 &&
                value.getGameTotal() > 0 && value.getGameInitTotal() > 0 && value.getFloatTime() <= 20.0) {
            App.config.basketballCount++;
            AsyncEventBus eventBus = App.getEventBus();
            value.setSentStr12(true);
            value.setSendTotal(value.getGameTotal());
            eventBus.post(value);
        }

    }

//     public void strategy12(BasketballSportData value) {
//        if (!value.isSentStr12() && value.getGameTotal() * 4 <= (value.getGameInitTotal() * 4) -26 &&
//                value.getGameTotal() > 0 && value.getGameInitTotal() > 0 && value.getFloatTime() <= 20.0) {
//            Main.config.basketballCount++;
//            value.setSentStr12(true);
//            value.setSendTotal(value.getGameTotal() * 4);
//            sendToTelegram(value);
//        }
//    }
//
//    @Override
//    public void sendToTelegram(BasketballSportData value) {
//        try {
//            Telegram.getInstance().SendMessage("Сигнал №: " + Main.config.basketballCount + System.lineSeparator() +
//                    "Лига: " + value.getLeague() + System.lineSeparator() + "Команды: " + value.getTeams() + System.lineSeparator() +
//                    "Время: " + value.getTime() + System.lineSeparator() + "Счет: " + value.getScore() + System.lineSeparator() +
//                    "Начальный тотал: " + value.getGameInitTotal() * 4 + System.lineSeparator() + "Тотал: " + value.getGameTotal() * 4 + System.lineSeparator()
//                    + BookParser.fonBet + value.getReference(), ChatId.getInstance().getBasketballChat());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
