package avtostavka.strategies.volleyball;

import avtostavka.App;
import avtostavka.data.Bet;
import avtostavka.data.VolleyballGame;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;

public class VolleyBallStrategy {
    @Subscribe
    public void ProcessGame(VolleyballGame game) {
        AsyncEventBus eventBus = App.getEventBus();
        Bet bet = new Bet("Team1 - Team2", getClass().getName(), "", "");
        eventBus.post(bet);
    }
}
