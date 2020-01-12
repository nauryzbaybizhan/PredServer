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
        if (value.isSuitableTwentyMinStrategy()) {
            float raznica = value.getGameTotal() - value.getGameInitTotal();
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
            if (!value.isSentTwentyMinStrategy() && mess.length() >1) {
                value.setSentTwentyMinStrategy(true);
                AsyncEventBus eventBus = App.getEventBus();
                Bet bet = new Bet(value.getTeams(), getClass().getName(), value.getReference(), "Лига: " + value.getLeague() + System.lineSeparator() +
                        "Команды: " + value.getTeams() + System.lineSeparator() + "Время прошло: " + value.getTime() + System.lineSeparator() + mess + System.lineSeparator()
                        + "Счет: " + value.getScore() + System.lineSeparator() +
                        "Начальный тотал: " + value.getGameInitTotal() + System.lineSeparator() + "Тотал на игру: " + value.getGameTotal() + " (" + value.koef + ")" + System.lineSeparator() +
                        FonBetBasketball.fonBet + value.getReference());
                eventBus.post(bet);
            }
        }
    }
}
