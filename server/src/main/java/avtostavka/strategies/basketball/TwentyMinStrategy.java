package avtostavka.strategies.basketball;

import avtostavka.App;
import avtostavka.bookmakers.FonBetBasketball;
import avtostavka.data.BasketballGame;
import avtostavka.data.Bet;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;

public class TwentyMinStrategy {

    @Subscribe
    public void ProcessGame(BasketballGame value) {
        if (value.isSuitableTwentyMinStrategy) {
            float raznica = value.gameTotal - value.gameInitTotal;
            String mess ="";
            if (raznica >= 20 && raznica < 25) {
                mess = "Тотал меньше до 25";
            }
            if (raznica >= 25) {
                mess = "Тотал меньше, разница больше 25";
            }
            if (raznica <= -20 && raznica > -25) {
                mess = "Тотал больше до 25";
            }
            if (raznica <= -25) {
                mess = "Тотал больше, разница больше 25";
            }
            if (!value.isSentTwentyMinStrategy && mess.length() >1) {
                value.isSentTwentyMinStrategy = true;
                AsyncEventBus eventBus = App.getEventBus();
                Bet bet = new Bet(value.teams, getClass().getName(), value.reference, "Лига: " + value.league + System.lineSeparator() +
                        "Команды: " + value.teams + System.lineSeparator() + "Время прошло: " + value.time + System.lineSeparator() + mess + System.lineSeparator()
                        + "Счет: " + value.score + System.lineSeparator() +
                        "Начальный тотал: " + value.gameInitTotal + System.lineSeparator() + "Тотал на игру: " + value.gameTotal + " (" + value.koef + ")" + System.lineSeparator() +
                        FonBetBasketball.fonBet + value.reference);
                eventBus.post(bet);
            }
        }
    }
}
