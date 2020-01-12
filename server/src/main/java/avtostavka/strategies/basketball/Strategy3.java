package avtostavka.strategies.basketball;

import avtostavka.App;
import avtostavka.data.BasketballGame;
import avtostavka.data.Bet;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;

public class Strategy3 {

    @Subscribe
    public void ProcessGame(BasketballGame game) {
        AsyncEventBus eventBus = App.getEventBus();
        if (game.isSuitableFoulStrategy()) {
            switch (game.quarter) {
                case 1: {
                    quarter1(game, eventBus);
                    break;
                }
                case 2: {
                    quarter2(game, eventBus);
                    break;
                }
                case 3: {
                    quarter3(game, eventBus);
                    break;
                }
                case 4: {
                    quarter4(game, eventBus);
                }
            }
        }
    }

    public void quarter1(BasketballGame value, AsyncEventBus eventBus) {
        if (!value.isSentStrategy3()[0] && value.getFloatTime() > 1.0f && value.getFloatTime() < 10.0f) {
            if ((value.fouls[0] == 2 && value.fouls[1] == 5)|| (value.fouls[0] == 5 && value.fouls[1] == 2)||
                    (value.fouls[0] == 2 && value.fouls[1] == 6)|| (value.fouls[0] == 6 && value.fouls[1] == 2)||
                    (value.fouls[0] == 3 && value.fouls[1] == 3) ||
                    (value.fouls[0] == 4 && value.fouls[1] == 2) || (value.fouls[0] == 2 && value.fouls[1] == 4)) {
                boolean[] sent = value.isSentStrategy3();
                sent[0] = true;
                value.setSentStrategy3(sent);
                Bet bet = new Bet(value.getTeams(), getClass().getName(), value.getReference(), value.getLeague() + System.lineSeparator() + value.getTeams() + System.lineSeparator()
                        + "Время прошло " + value.getTime() + System.lineSeparator() + "Четверть " + 1
                        + System.lineSeparator() + "Начальный тотал " + value.getGameInitTotal() + System.lineSeparator() + "Счет " + value.getScore() + System.lineSeparator()
                        + "Фолы " + value.fouls[0] + "-" + value.fouls[1] + System.lineSeparator() + "Ставка на ТБ " + value.quarterTotal + " (" + value.koef + ")");
                eventBus.post(bet);
                //toSend(value, "1");
            }
        }
    }

    public void quarter2(BasketballGame value, AsyncEventBus eventBus) {
        if (!value.isSentStrategy3()[1] && value.getFloatTime() > 11.0f && value.getFloatTime() < 20.0f) {
            if ((value.fouls[0] == 2 && value.fouls[1] == 5)|| (value.fouls[0] == 5 && value.fouls[1] == 2)||
                    (value.fouls[0] == 2 && value.fouls[1] == 6)|| (value.fouls[0] == 6 && value.fouls[1] == 2)||
                    (value.fouls[0] == 3 && value.fouls[1] == 3) ||
                    (value.fouls[0] == 4 && value.fouls[1] == 2) || (value.fouls[0] == 2 && value.fouls[1] == 4)) {
                boolean[] sent = value.isSentStrategy3();
                sent[1] = true;
                value.setSentStrategy3(sent);
                Bet bet = new Bet(value.getTeams(), getClass().getName(), value.getReference(), value.getLeague() + System.lineSeparator() + value.getTeams() + System.lineSeparator()
                        + "Время прошло " + value.getTime() + System.lineSeparator() + "Четверть " + 2
                        + System.lineSeparator() + "Начальный тотал " + value.getGameInitTotal() + System.lineSeparator() + "Счет " + value.getScore() + System.lineSeparator()
                        + "Фолы " + value.fouls[0] + "-" + value.fouls[1] + System.lineSeparator() + "Ставка на ТБ " + value.quarterTotal + " (" + value.koef + ")");
                //toSend(value, "2");
            }
        }
    }

    public void quarter3(BasketballGame value, AsyncEventBus eventBus) {
        if (!value.isSentStrategy3()[2] && value.getFloatTime() > 21.0f && value.getFloatTime() < 30.0f) {
            if ((value.fouls[0] == 2 && value.fouls[1] == 5)|| (value.fouls[0] == 5 && value.fouls[1] == 2)||
                    (value.fouls[0] == 2 && value.fouls[1] == 6)|| (value.fouls[0] == 6 && value.fouls[1] == 2)||
                    (value.fouls[0] == 3 && value.fouls[1] == 3) ||
                    (value.fouls[0] == 4 && value.fouls[1] == 2) || (value.fouls[0] == 2 && value.fouls[1] == 4)) {
                boolean[] sent = value.isSentStrategy3();
                sent[2] = true;
                value.setSentStrategy3(sent);
                Bet bet = new Bet(value.getTeams(), getClass().getName(), value.getReference(), value.getLeague() + System.lineSeparator() + value.getTeams() + System.lineSeparator()
                        + "Время прошло " + value.getTime() + System.lineSeparator() + "Четверть " + 3
                        + System.lineSeparator() + "Начальный тотал " + value.getGameInitTotal() + System.lineSeparator() + "Счет " + value.getScore() + System.lineSeparator()
                        + "Фолы " + value.fouls[0] + "-" + value.fouls[1] + System.lineSeparator() + "Ставка на ТБ " + value.quarterTotal + " (" + value.koef + ")");
                //toSend(value, "3");
            }
        }
    }

    public void quarter4(BasketballGame value, AsyncEventBus eventBus) {
        if (!value.isSentStrategy3()[3] && value.getFloatTime() > 31.0f && value.getFloatTime() < 40.0f) {
            if ((value.fouls[0] == 2 && value.fouls[1] == 5)|| (value.fouls[0] == 5 && value.fouls[1] == 2)||
                    (value.fouls[0] == 2 && value.fouls[1] == 6)|| (value.fouls[0] == 6 && value.fouls[1] == 2)||
                    (value.fouls[0] == 3 && value.fouls[1] == 3) ||
                    (value.fouls[0] == 4 && value.fouls[1] == 2) || (value.fouls[0] == 2 && value.fouls[1] == 4)) {
                boolean[] sent = value.isSentStrategy3();
                sent[3] = true;
                value.setSentStrategy3(sent);
                Bet bet = new Bet(value.getTeams(), getClass().getName(), value.getReference(), value.getLeague() + System.lineSeparator() + value.getTeams() + System.lineSeparator()
                        + "Время прошло " + value.getTime() + System.lineSeparator() + "Четверть " + 4
                        + System.lineSeparator() + "Начальный тотал " + value.getGameInitTotal() + System.lineSeparator() + "Счет " + value.getScore() + System.lineSeparator()
                        + "Фолы " + value.fouls[0] + "-" + value.fouls[1] + System.lineSeparator() + "Ставка на ТБ " + value.getGameTotal() + " (" + value.koef + ")");
                //toSend(value, "4");
            }
        }
    }
}
