package avtostavka.strategies.basketball;

import avtostavka.App;
import avtostavka.data.SevenBasketGame;
import avtostavka.data.Bet;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;

public class FoulStrategy {
    @Subscribe
    public void ProcessGame(SevenBasketGame game) {
        AsyncEventBus eventBus = App.getEventBus();
        if (game.isSuitableFoulStrategy) {
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

    public void quarter1(SevenBasketGame value, AsyncEventBus eventBus) {
        if (!value.isSentFoulStrategy[0] && value.quarterTotal < value.gameInitTotal/4 && value.quarter == 1) {
            if ((value.floatTime > 1.0f && value.floatTime < 5.1f && value.fouls[0] > 2 && value.fouls[1] > 2 && value.fouls[0] + value.fouls[1] > 6) ||
                    (value.floatTime > 1.0f && value.floatTime < 6.05f && value.fouls[0] > 3 && value.fouls[1] > 3 && value.fouls[0] + value.fouls[1] > 7)) {
                boolean[] sent = value.isSentFoulStrategy;
                sent[0] = true;
                value.isSentFoulStrategy = sent;
                value.sendQuarterFoulStrategy[0] = value.quarter;
                value.sendCoefFoulStrategy[0] = value.koef;
                value.sendTBFoulStrategy[0] = value.quarterTotal;
                Bet bet = new Bet(value.teams, getClass().getName(), value.reference, value.league + System.lineSeparator() + value.teams + System.lineSeparator()
                        + "Время прошло " + value.time + System.lineSeparator() + "Четверть " + 1
                        + System.lineSeparator() + "Начальный тотал " + value.gameInitTotal + System.lineSeparator() + "Счет " + value.score + System.lineSeparator()
                        + "Фолы " + value.fouls[0] + "-" + value.fouls[1]
                        + System.lineSeparator() + "Ставка на ТБ " + value.quarterTotal + " (" + value.koef + ")");
                eventBus.post(bet);
                //toSend(value, "1");
            }
        }
    }

    public void quarter2(SevenBasketGame value, AsyncEventBus eventBus) {
        if (!value.isSentFoulStrategy[1] && value.quarter == 2) {
            if (value.scoreArray[0][1] + value.scoreArray[0][2] < 45 && value.quarterTotal < value.gameInitTotal/4) {
                if ((value.floatTime > 11.0f && value.floatTime < 15.1f && value.fouls[0] > 2 && value.fouls[1] > 2 && value.fouls[0] + value.fouls[1] > 6) ||
                        (value.floatTime > 11.0f && value.floatTime < 16.05f && value.fouls[0] > 3 && value.fouls[1] > 3 && value.fouls[0] + value.fouls[1] > 7)) {
                    boolean[] sent = value.isSentFoulStrategy;
                    sent[1] = true;
                    value.isSentFoulStrategy = sent;
                    value.sendQuarterFoulStrategy[1] = value.quarter;
                    value.sendCoefFoulStrategy[1] = value.koef;
                    value.sendTBFoulStrategy[1] = value.quarterTotal;
                    Bet bet = new Bet(value.teams, getClass().getName(), value.reference, value.league + System.lineSeparator() + value.teams + System.lineSeparator()
                            + "Время прошло " + value.time + System.lineSeparator() + "Четверть " + 2
                            + System.lineSeparator() + "Начальный тотал " + value.gameInitTotal + System.lineSeparator() + "Счет " + value.score + System.lineSeparator()
                            + "Фолы " + value.fouls[0] + "-" + value.fouls[1]
                            + System.lineSeparator() + "Ставка на ТБ " + value.quarterTotal + " (" + value.koef + ")");
                    eventBus.post(bet);
                    //toSend(value,"2");
                }
            }
        }
    }

    public void quarter3(SevenBasketGame value, AsyncEventBus eventBus) {
        if (!value.isSentFoulStrategy[2]) {
            if (value.scoreArray[1][1] + value.scoreArray[1][2] < 45 && value.quarterTotal < value.gameInitTotal/4) {
                if ((value.floatTime > 21.0f && value.floatTime < 25.1f && value.fouls[0] > 2 && value.fouls[1] > 2 && value.fouls[0] + value.fouls[1] > 6) ||
                        (value.floatTime > 21.0f && value.floatTime < 26.05f && value.fouls[0] > 3 && value.fouls[1] > 3 && value.fouls[0] + value.fouls[1] > 7)) {
                    boolean[] sent = value.isSentFoulStrategy;
                    sent[2] = true;
                    value.isSentFoulStrategy = sent;
                    value.sendQuarterFoulStrategy[2] = value.quarter;
                    value.sendCoefFoulStrategy[2] = value.koef;
                    value.sendTBFoulStrategy[2] = value.quarterTotal;
                    Bet bet = new Bet(value.teams, getClass().getName(), value.reference, value.league + System.lineSeparator() + value.teams + System.lineSeparator()
                            + "Время прошло " + value.time + System.lineSeparator() + "Четверть " + 3
                            + System.lineSeparator() + "Начальный тотал " + value.gameInitTotal + System.lineSeparator() + "Счет " + value.score + System.lineSeparator()
                            + "Фолы " + value.fouls[0] + "-" + value.fouls[1]
                            + System.lineSeparator() + "Ставка на ТБ " + value.quarterTotal + " (" + value.koef + ")");
                    eventBus.post(bet);
                    //toSend(value, "3");
                }
            }
        }
    }

    public void quarter4(SevenBasketGame value, AsyncEventBus eventBus) {
        int sum = 0;
        for (int i =0; i < 3; i++) {
            sum += value.scoreArray[i][1] + value.scoreArray[i][2];
        }
        if (!value.isSentFoulStrategy[3] && value.scoreArray[2][1] - value.scoreArray[1][2] < 12 && value.scoreArray[2][1] - value.scoreArray[2][2] > -12) {
            boolean ok = (value.gameTotal - sum) < value.gameInitTotal/4;
            if (value.scoreArray[2][1] + value.scoreArray[2][2] < 45 && ok) {
                if ((value.floatTime > 31.0f && value.floatTime < 35.1f && value.fouls[0] > 2 && value.fouls[1] > 2 && value.fouls[0] + value.fouls[1] > 6) ||
                        (value.floatTime > 31.0f && value.floatTime< 36.05f && value.fouls[0] > 3 && value.fouls[1] > 3 && value.fouls[0] + value.fouls[1] > 7)) {
                    boolean[] sent = value.isSentFoulStrategy;
                    sent[3] = true;
                    value.isSentFoulStrategy = sent;
                    value.sendQuarterFoulStrategy[3] = value.quarter;
                    value.sendCoefFoulStrategy[3] = value.koef;
                    value.sendTBFoulStrategy[3] = value.gameTotal;
                    Bet bet = new Bet(value.teams, getClass().getName(), value.reference, value.league + System.lineSeparator() + value.teams + System.lineSeparator()
                            + "Время прошло " + value.time + System.lineSeparator() + "Четверть " + 4
                            + System.lineSeparator() + "Начальный тотал " + value.gameInitTotal + System.lineSeparator() + "Счет " + value.score + System.lineSeparator()
                            + "Фолы " + value.fouls[0] + "-" + value.fouls[1]
                            + System.lineSeparator() + "Ставка на ТБ " + value.gameTotal + " (" + value.koef + ")");
                    eventBus.post(bet);
                    //toSend(value, "4");
                }
            }
        }
    }
}
