package avtostavka.data;

public abstract class Game {

    public String reference;

    public String team1;

    public String team2;

    public String teams;

    public String league;

    public String score;

    public int ticker = 0;

    public String strategy;

    public String time = "default";

    public float floatTime = -1;

    public boolean isTracked;

    public boolean isLive;
}
