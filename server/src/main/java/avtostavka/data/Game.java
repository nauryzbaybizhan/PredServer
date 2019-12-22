package avtostavka.data;

public abstract class Game {

    private String reference;

    private String team1;

    private String team2;

    private String teams;

    private String league;

    private String score;

    public int ticker = 0;

    private String strategy;

    private String time = "default";

    private float floatTime = -1;

    private boolean isTracked;

    private boolean isLive;

    public String getReference() {
        return reference;
    }

    protected void setReference(String reference) {
        this.reference = reference;
    }

    public String getTeam1() {
        return team1;
    }

    protected void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    protected void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getTeams() {
        return teams;
    }

    protected void setTeams(String teams) {
        this.teams = teams;
    }

    public String getLeague() {
        return league;
    }

    protected void setLeague(String league) {
        this.league = league;
    }

    public String getScore() {
        return score;
    }

    protected void setScore(String score) {
        this.score = score;
    }

    public String getStrategy() {
        return strategy;
    }

    protected void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getTime() {
        return time;
    }

    protected void setTime(String time) {
        this.time = time;
    }

    public float getFloatTime() {
        return floatTime;
    }

    protected void setFloatTime(float floatTime) {
        this.floatTime = floatTime;
    }

    public boolean isTracked() {
        return isTracked;
    }

    protected void setTracked(boolean tracked) {
        isTracked = tracked;
    }

    public boolean isLive() {
        return isLive;
    }

    protected void setLive(boolean live) {
        isLive = live;
    }
}
