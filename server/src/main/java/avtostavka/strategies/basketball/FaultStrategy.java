package avtostavka.strategies.basketball;

import avtostavka.App;
import avtostavka.data.BasketballGame;
import avtostavka.data.Bet;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;

public class FaultStrategy {
    @Subscribe
    public void ProcessGame(BasketballGame game) {
        AsyncEventBus eventBus = App.getEventBus();
        Bet bet = new Bet("Team1 - Team2", getClass().getName(), "", "");
        eventBus.post(bet);
    }
}
