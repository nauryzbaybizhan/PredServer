package avtostavka.strategies.football;

import avtostavka.App;
import avtostavka.bookmakers.FonBetFootball;
import avtostavka.data.Bet;
import avtostavka.data.FootballGame;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;

public class FootballStrategies {

    @Subscribe
    public void ProcessGame(FootballGame value) {
        strategy1(value);
        strategy2(value);
    }

    private void strategy1(FootballGame value) {
        boolean a = value.lineWin1 <= 1.6 || value.lineWin2 <= 1.6;
        if (a && !value.isSentStr1 && value.floatTime > 65 && value.floatTime < 66 &&
                value.scoreArray[1] + value.scoreArray[2] == 1) {
            value.sendScoreStr1 = value.scoreArray[1] + value.scoreArray[2];
            value.isSentStr1 = true;
//            sendToTelegram(value);
//            sendToExchange(value, value.gameTotalB);
            App.config.footballCount++;
            AsyncEventBus eventBus = App.getEventBus();
//            Bet bet = new Bet(value.teams, getClass().getName(), value.reference, "Лига: " + value.getLeague() + System.lineSeparator() +
//                    "Команды: " + value.getTeams() + System.lineSeparator() +
//                    "Время: " + value.getTime() + System.lineSeparator() + "Счет: " + value.getScore() + System.lineSeparator() +
//                    "Начальные коэфициенты: " + value.getLineWin1() + ":" + value.getLineWin2() + System.lineSeparator() +
//                    FonBetFootball.fonBet + value.getReference());
//            eventBus.post(bet);
        }
    }

    private void strategy2(FootballGame value) {
        if (!value.isSentStr2 && value.floatTime > 55.0 && value.floatTime < 60.0 && ((value.scoreArray[1] == 3 && value.scoreArray[2] == 0) ||
                (value.scoreArray[1] == 0 && value.scoreArray[2] == 3))) {
            System.out.println("ready");
            if (value.goalBefore75Koef > 0) {
                value.isSentStr2 = true;
//                sendToTelegram2(value);
            }
        }
    }
}
