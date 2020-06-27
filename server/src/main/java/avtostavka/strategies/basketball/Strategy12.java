package avtostavka.strategies.basketball;

import avtostavka.App;
import avtostavka.Filter;
import avtostavka.bookmakers.FonBetBasketball;
import avtostavka.data.BasketballGame;
import avtostavka.data.Bet;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;

public class Strategy12 {

    @Subscribe
    public void ProcessGame(BasketballGame value) {
        if (!Filter.getInstance().contains(value.league, App.config.basketballBlackList)) {
            if (!value.isSentStr12 && value.gameTotal <= (value.gameInitTotal) -26 &&
                    value.gameTotal > 0 && value.gameInitTotal > 0 && value.floatTime <= 20.0) {
                App.config.basketballCount++;
                AsyncEventBus eventBus = App.getEventBus();
                value.isSentStr12 = true;
                value.str12SendTotal = value.gameTotal;
                Bet bet = new Bet(value.teams, getClass().getName(), value.reference, "Лига: " + value.league + System.lineSeparator() +
                        "Команды: " + value.teams + System.lineSeparator() + "Время: " + value.time + System.lineSeparator() + "Счет: " + value.score + System.lineSeparator() +
                        "Начальный тотал: " + value.gameInitTotal * 4 + System.lineSeparator() + "Тотал: " + value.gameTotal * 4 + System.lineSeparator() +
                        FonBetBasketball.fonBet + value.reference);
                eventBus.post(bet);
            }
        }
    }
}
