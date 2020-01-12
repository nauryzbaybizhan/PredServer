package avtostavka.strategies.basketball;

import avtostavka.App;
import avtostavka.bookmakers.FonBetBasketball;
import avtostavka.data.BasketballGame;
import avtostavka.data.Bet;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;

public class Strategy12 {

    @Subscribe
    public void ProcessGame(BasketballGame value) {
        if (value.isSuitableStr12()) {
            if (!value.isSentStr12() && value.getGameTotal() <= (value.getGameInitTotal()) -26 &&
                    value.getGameTotal() > 0 && value.getGameInitTotal() > 0 && value.getFloatTime() <= 20.0) {
                App.config.basketballCount++;
                AsyncEventBus eventBus = App.getEventBus();
                value.setSentStr12(true);
                value.setSendTotal(value.getGameTotal());
                Bet bet = new Bet(value.getTeams(), getClass().getName(), value.getReference(), "Лига: " + value.getLeague() + System.lineSeparator() +
                        "Команды: " + value.getTeams() + System.lineSeparator() + "Время: " + value.getTime() + System.lineSeparator() + "Счет: " + value.getScore() + System.lineSeparator() +
                        "Начальный тотал: " + value.getGameInitTotal() * 4 + System.lineSeparator() + "Тотал: " + value.getGameTotal() * 4 + System.lineSeparator() +
                        FonBetBasketball.fonBet + value.getReference());
                eventBus.post(bet);
            }
        }
    }
}
