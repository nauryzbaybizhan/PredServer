package avtostavka.data;

public class BasketballGame extends Game {

    private float gameInitTotal;

    private float gameTotal;

    private float sendTotal;

    private int[][] scoreArray = new int[4][3];

    private boolean isSentStr12;

    public float getGameInitTotal() {
        return this.gameInitTotal;
    }

    public void setGameInitTotal(float gameInitTotal) {
        this.gameInitTotal = gameInitTotal;
    }

    public float getGameTotal() {
        return this.gameTotal;
    }

    public void setGameTotal(float gameTotal) {
        this.gameTotal = gameTotal;
    }

    public int[][] getScoreArray() {
        return this.scoreArray;
    }

    public void setScoreArray(int[][] scoreArray) {
        this.scoreArray = scoreArray;
    }

    public float getSendTotal() {
        return sendTotal;
    }

    public void setSendTotal(float sendTotal) {
        this.sendTotal = sendTotal;
    }

    public boolean isSentStr12() {
        return isSentStr12;
    }

    public void setSentStr12(boolean sentStr12) {
        isSentStr12 = sentStr12;
    }

    @Override
    public String getReference() {
        return super.getReference();
    }

    @Override
    public void setReference(String reference) {
        super.setReference(reference);
    }

    @Override
    public String getTeam1() {
        return super.getTeam1();
    }

    @Override
    public void setTeam1(String team1) {
        super.setTeam1(team1);
    }

    @Override
    public String getTeam2() {
        return super.getTeam2();
    }

    @Override
    public void setTeam2(String team2) {
        super.setTeam2(team2);
    }

    @Override
    public String getTeams() {
        return super.getTeams();
    }

    @Override
    public void setTeams(String teams) {
        super.setTeams(teams);
    }

    @Override
    public String getLeague() {
        return super.getLeague();
    }

    @Override
    public void setLeague(String league) {
        super.setLeague(league);
    }

    @Override
    public String getScore() {
        return super.getScore();
    }

    @Override
    public void setScore(String score) {
        super.setScore(score);
    }

    @Override
    public String getStrategy() {
        return super.getStrategy();
    }

    @Override
    public void setStrategy(String strategy) {
        super.setStrategy(strategy);
    }

    @Override
    public String getTime() {
        return super.getTime();
    }

    @Override
    public void setTime(String time) {
        super.setTime(time);
    }

    @Override
    public float getFloatTime() {
        return super.getFloatTime();
    }

    @Override
    public void setFloatTime(float floatTime) {
        super.setFloatTime(floatTime);
    }

    @Override
    public boolean isTracked() {
        return super.isTracked();
    }

    @Override
    public void setTracked(boolean tracked) {
        super.setTracked(tracked);
    }

    @Override
    public boolean isLive() {
        return super.isLive();
    }

    @Override
    public void setLive(boolean live) {
        super.setLive(live);
    }

}
