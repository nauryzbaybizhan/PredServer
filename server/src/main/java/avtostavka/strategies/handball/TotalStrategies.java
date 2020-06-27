package avtostavka.strategies.handball;

import avtostavka.App;
import avtostavka.bookmakers.XBetHandball;
import avtostavka.data.Bet;
import avtostavka.data.HandballGame;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;

public class TotalStrategies {

    @Subscribe
    public void ProcessGame(HandballGame value) {
        if (value.floatTime > 8 && value.floatTime < 52) {
            AsyncEventBus eventBus = App.getEventBus();
            if (value.gameTotal <= value.gameInitTotal -6 && !value.isSentStr1) {
                App.config.handballCount++;
                value.isSentStr1 = true;
                value.sendTotal = value.gameTotal;
                value.strategy = "Алгоритм 1 (ТБ)";
                Bet bet = new Bet(value.teams, getClass().getName(), value.reference, value.strategy + System.lineSeparator() + "Лига: " + value.league + System.lineSeparator()
                        + "Команды: " + value.teams + System.lineSeparator() + "Счет " + value.score + System.lineSeparator() + "Время: " + value.time + System.lineSeparator()
                        + "Начальный тотал: " + value.gameInitTotal + System.lineSeparator() + "Текущий тотал: " + value.gameTotal
                        + System.lineSeparator() + XBetHandball.xStavka + value.reference);
                eventBus.post(bet);
            }
            if (value.gameTotal >= value.gameInitTotal +7 && !value.isSentStr2) {
                App.config.handballCount++;
                value.isSentStr2 = true;
                value.sendTotal = value.gameTotal;
                value.strategy = "Алгоритм 2 (ТМ)";
                Bet bet = new Bet(value.teams, getClass().getName(), value.reference, value.strategy + System.lineSeparator() + "Лига: " + value.league + System.lineSeparator()
                        + "Команды: " + value.teams + System.lineSeparator() + "Счет " + value.score + System.lineSeparator() + "Время: " + value.time + System.lineSeparator()
                        + "Начальный тотал: " + value.gameInitTotal + System.lineSeparator() + "Текущий тотал: " + value.gameTotal
                        + System.lineSeparator() + XBetHandball.xStavka + value.reference);
                eventBus.post(bet);
            }
        }
    }

}
