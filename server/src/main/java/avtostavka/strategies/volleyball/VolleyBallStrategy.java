package avtostavka.strategies.volleyball;

import avtostavka.App;
import avtostavka.data.Bet;
import avtostavka.data.VolleyballGame;
import com.google.common.eventbus.Subscribe;

public class VolleyBallStrategy {
    @Subscribe
    public void ProcessGame(VolleyballGame game) {
        if (!game.isSent && game.totalQuarter2 < 45.5 && game.totalMKoefQuarter2 > 1.8) {
            game.isSent = true;
            Bet bet = new Bet(game.teams, getClass().getName(), game.reference, "Лига: " + game.league + System.lineSeparator() + "Команды: " + game.teams + System.lineSeparator() +
                    "Счет: " + game.fullScore + System.lineSeparator() +
                    "Тотал на четверть 2: " + game.totalQuarter2 + System.lineSeparator() + "Коэф на тотал М: " + game.totalMKoefQuarter2);
            App.getEventBus().post(bet);
        }
    }
}
