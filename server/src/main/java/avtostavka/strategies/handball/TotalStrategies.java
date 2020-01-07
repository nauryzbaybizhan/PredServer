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
        if (value.getFloatTime() > 8 && value.getFloatTime() < 52) {
            AsyncEventBus eventBus = App.getEventBus();
            if (value.getGameTotal() <= value.getGameInitTotal() -6 && !value.isSentStr1()) {
                App.config.handballCount++;
                value.setSentStr1(true);
                value.setSendTotal(value.getGameTotal());
                value.setStrategy("Алгоритм 1 (ТБ)");
                Bet bet = new Bet(value.getTeams(), getClass().getName(), value.getReference(), value.getStrategy() + System.lineSeparator() + "Лига: " + value.getLeague() + System.lineSeparator()
                        + "Команды: " + value.getTeams() + System.lineSeparator() + "Счет " + value.getScore() + System.lineSeparator() + "Время: " + value.getTime() + System.lineSeparator()
                        + "Начальный тотал: " + value.getGameInitTotal() + System.lineSeparator() + "Текущий тотал: " + value.getGameTotal()
                        + System.lineSeparator() + XBetHandball.xStavka + value.getReference());
                eventBus.post(bet);
            }
            if (value.getGameTotal() >= value.getGameInitTotal() +7 && !value.isSentStr2()) {
                App.config.handballCount++;
                value.setSentStr2(true);
                value.setSendTotal(value.getGameTotal());
                value.setStrategy("Алгоритм 2 (ТМ)");
                Bet bet = new Bet(value.getTeams(), getClass().getName(), value.getReference(), value.getStrategy() + System.lineSeparator() + "Лига: " + value.getLeague() + System.lineSeparator()
                        + "Команды: " + value.getTeams() + System.lineSeparator() + "Счет " + value.getScore() + System.lineSeparator() + "Время: " + value.getTime() + System.lineSeparator()
                        + "Начальный тотал: " + value.getGameInitTotal() + System.lineSeparator() + "Текущий тотал: " + value.getGameTotal()
                        + System.lineSeparator() + XBetHandball.xStavka + value.getReference());
                eventBus.post(bet);
            }
        }
    }

}
