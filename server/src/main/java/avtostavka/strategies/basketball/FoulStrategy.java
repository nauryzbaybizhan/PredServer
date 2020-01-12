package avtostavka.strategies.basketball;

import avtostavka.App;
import avtostavka.data.BasketballGame;
import avtostavka.data.Bet;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;

public class FoulStrategy {
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
        if (!value.isSentFoulStrategy()[0] && value.quarterTotal < value.getGameInitTotal()/4 && value.quarter == 1) {
            if ((value.getFloatTime() > 1.0f && value.getFloatTime() < 5.1f && value.fouls[0] > 2 && value.fouls[1] > 2 && value.fouls[0] + value.fouls[1] > 6) ||
                    (value.getFloatTime() > 1.0f && value.getFloatTime() < 6.05f && value.fouls[0] > 3 && value.fouls[1] > 3 && value.fouls[0] + value.fouls[1] > 7)) {
                boolean[] sent = value.isSentFoulStrategy();
                sent[0] = true;
                value.setSentFoulStrategy(sent);
                value.sendQuarter[0] = value.quarter;
                value.sendKoef[0] = value.koef;
                value.sendTB[0] = value.quarterTotal;
                Bet bet = new Bet(value.getTeams(), getClass().getName(), value.getReference(), value.getLeague() + System.lineSeparator() + value.getTeams() + System.lineSeparator()
                        + "Время прошло " + value.getTime() + System.lineSeparator() + "Четверть " + 1
                        + System.lineSeparator() + "Начальный тотал " + value.getGameInitTotal() + System.lineSeparator() + "Счет " + value.getScore() + System.lineSeparator()
                        + "Фолы " + value.fouls[0] + "-" + value.fouls[1]
                        + System.lineSeparator() + "Ставка на ТБ " + value.quarterTotal + " (" + value.koef + ")");
                eventBus.post(bet);
                //toSend(value, "1");
            }
        }
    }

    public void quarter2(BasketballGame value, AsyncEventBus eventBus) {
        if (!value.isSentFoulStrategy()[1] && value.quarter == 2) {
            if (value.getScoreArray()[0][1] + value.getScoreArray()[0][2] < 45 && value.quarterTotal < value.getGameInitTotal()/4) {
                if ((value.getFloatTime() > 11.0f && value.getFloatTime() < 15.1f && value.fouls[0] > 2 && value.fouls[1] > 2 && value.fouls[0] + value.fouls[1] > 6) ||
                        (value.getFloatTime() > 11.0f && value.getFloatTime() < 16.05f && value.fouls[0] > 3 && value.fouls[1] > 3 && value.fouls[0] + value.fouls[1] > 7)) {
                    boolean[] sent = value.isSentFoulStrategy();
                    sent[1] = true;
                    value.setSentFoulStrategy(sent);
                    value.sendQuarter[1] = value.quarter;
                    value.sendKoef[1] = value.koef;
                    value.sendTB[1] = value.quarterTotal;
                    Bet bet = new Bet(value.getTeams(), getClass().getName(), value.getReference(), value.getLeague() + System.lineSeparator() + value.getTeams() + System.lineSeparator()
                            + "Время прошло " + value.getTime() + System.lineSeparator() + "Четверть " + 2
                            + System.lineSeparator() + "Начальный тотал " + value.getGameInitTotal() + System.lineSeparator() + "Счет " + value.getScore() + System.lineSeparator()
                            + "Фолы " + value.fouls[0] + "-" + value.fouls[1]
                            + System.lineSeparator() + "Ставка на ТБ " + value.quarterTotal + " (" + value.koef + ")");
                    eventBus.post(bet);
                    //toSend(value,"2");
                }
            }
        }
    }

    public void quarter3(BasketballGame value, AsyncEventBus eventBus) {
        if (!value.isSentFoulStrategy()[2]) {
            if (value.getScoreArray()[1][1] + value.getScoreArray()[1][2] < 45 && value.quarterTotal < value.getGameInitTotal()/4) {
                if ((value.getFloatTime() > 21.0f && value.getFloatTime() < 25.1f && value.fouls[0] > 2 && value.fouls[1] > 2 && value.fouls[0] + value.fouls[1] > 6) ||
                        (value.getFloatTime() > 21.0f && value.getFloatTime() < 26.05f && value.fouls[0] > 3 && value.fouls[1] > 3 && value.fouls[0] + value.fouls[1] > 7)) {
                    boolean[] sent = value.isSentFoulStrategy();
                    sent[2] = true;
                    value.setSentFoulStrategy(sent);
                    value.sendQuarter[2] = value.quarter;
                    value.sendKoef[2] = value.koef;
                    value.sendTB[2] = value.quarterTotal;
                    Bet bet = new Bet(value.getTeams(), getClass().getName(), value.getReference(), value.getLeague() + System.lineSeparator() + value.getTeams() + System.lineSeparator()
                            + "Время прошло " + value.getTime() + System.lineSeparator() + "Четверть " + 3
                            + System.lineSeparator() + "Начальный тотал " + value.getGameInitTotal() + System.lineSeparator() + "Счет " + value.getScore() + System.lineSeparator()
                            + "Фолы " + value.fouls[0] + "-" + value.fouls[1]
                            + System.lineSeparator() + "Ставка на ТБ " + value.quarterTotal + " (" + value.koef + ")");
                    eventBus.post(bet);
                    //toSend(value, "3");
                }
            }
        }
    }

    public void quarter4(BasketballGame value, AsyncEventBus eventBus) {
        int sum = 0;
        for (int i =0; i < 3; i++) {
            sum += value.getScoreArray()[i][1] + value.getScoreArray()[i][2];
        }
        if (!value.isSentFoulStrategy()[3] && value.getScoreArray()[2][1] - value.getScoreArray()[1][2] < 12 && value.getScoreArray()[2][1] - value.getScoreArray()[2][2] > -12) {
            boolean ok = (value.getGameTotal() - sum) < value.getGameInitTotal()/4;
            if (value.getScoreArray()[2][1] + value.getScoreArray()[2][2] < 45 && ok) {
                if ((value.getFloatTime() > 31.0f && value.getFloatTime() < 35.1f && value.fouls[0] > 2 && value.fouls[1] > 2 && value.fouls[0] + value.fouls[1] > 6) ||
                        (value.getFloatTime() > 31.0f && value.getFloatTime()< 36.05f && value.fouls[0] > 3 && value.fouls[1] > 3 && value.fouls[0] + value.fouls[1] > 7)) {
                    boolean[] sent = value.isSentFoulStrategy();
                    sent[3] = true;
                    value.setSentFoulStrategy(sent);
                    value.sendQuarter[3] = value.quarter;
                    value.sendKoef[3] = value.koef;
                    value.sendTB[3] = value.getGameTotal();
                    Bet bet = new Bet(value.getTeams(), getClass().getName(), value.getReference(), value.getLeague() + System.lineSeparator() + value.getTeams() + System.lineSeparator()
                            + "Время прошло " + value.getTime() + System.lineSeparator() + "Четверть " + 4
                            + System.lineSeparator() + "Начальный тотал " + value.getGameInitTotal() + System.lineSeparator() + "Счет " + value.getScore() + System.lineSeparator()
                            + "Фолы " + value.fouls[0] + "-" + value.fouls[1]
                            + System.lineSeparator() + "Ставка на ТБ " + value.getGameTotal() + " (" + value.koef + ")");
                    eventBus.post(bet);
                    //toSend(value, "4");
                }
            }
        }
    }
}
