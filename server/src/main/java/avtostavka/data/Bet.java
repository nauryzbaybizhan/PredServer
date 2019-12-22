package avtostavka.data;

public final class Bet {
    public Bet(String betName, String strategyName, String link, String bet) {
        BetName = betName;
        StrategyName = strategyName;
        Link = link;
        Bet = bet;
    }

    public String BetName;
    public String StrategyName;
    public String Link;
    public String Bet;
}
