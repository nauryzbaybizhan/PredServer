package avtostavka.strategies.volleyball;

import avtostavka.App;
import avtostavka.data.Bet;
import avtostavka.data.VolleyballGame;
import com.google.common.eventbus.Subscribe;

public class VolleyBallStrategy {
    @Subscribe
    public void ProcessGame(VolleyballGame game) {
        if (!game.isSent() && game.getTotalQuarter2() < 45.5 && game.getTotalMKoefQuarter2() > 1.8) {
            game.setSent(true);
            Bet bet = new Bet(game.getTeams(), getClass().getName(), game.getReference(), "Лига: " + game.getLeague() + System.lineSeparator() + "Команды: " + game.getTeams() + System.lineSeparator() +
                    "Счет: " + game.getFullScore() + System.lineSeparator() +
                    "Тотал на четверть 2: " + game.getTotalQuarter2() + System.lineSeparator() + "Коэф на тотал М: " + game.getTotalMKoefQuarter2());
            App.getEventBus().post(bet);
        }
    }
}
