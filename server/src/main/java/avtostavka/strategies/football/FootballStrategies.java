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
        int[] scoreArray = value.getScoreArray();
        boolean a = value.getLineWin1() <= 1.6 || value.getLineWin2() <= 1.6;
        if (a && !value.isSent() && value.getFloatTime() > 65 && value.getFloatTime() < 66 && scoreArray[1] <= 1 && scoreArray[2] <= 1 && scoreArray[1] + scoreArray[2] <= 1) {
            App.config.footballCount++;
            AsyncEventBus eventBus = App.getEventBus();
            value.setSendSum(scoreArray[1] + scoreArray[2]);
            value.setSent(true);
            int score = scoreArray[1] + scoreArray[2];
            Bet bet = new Bet(value.getTeams(), getClass().getName(), value.getReference(), "Лига: " + value.getLeague() + System.lineSeparator() +
                    "Команды: " + value.getTeams() + System.lineSeparator() +
                    "Время: " + value.getTime() + System.lineSeparator() + "Счет: " + value.getScore() + System.lineSeparator() +
                    "Начальные коэфициенты: " + value.getLineWin1() + ":" + value.getLineWin2() + System.lineSeparator() +
                    FonBetFootball.fonBet + value.getReference());
            eventBus.post(bet);
        }
    }
}
