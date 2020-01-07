package avtostavka.data;

public class TennisGame extends Game {

    private float lineWin1;

    private float lineWin2;

    private float gameWin1;

    private float gameWin2;

    public int checkTicker = 0;

    public int liveTicker = 0;

    private boolean isSent = false;

    private boolean isWritten = false;

    private int[][] scoreArray = new int[5][3];

    public float getLineWin1() {
        return lineWin1;
    }

    public void setLineWin1(float lineWin1) {
        this.lineWin1 = lineWin1;
    }

    public float getLineWin2() {
        return lineWin2;
    }

    public void setLineWin2(float lineWin2) {
        this.lineWin2 = lineWin2;
    }

    public float getGameWin1() {
        return gameWin1;
    }

    public void setGameWin1(float gameWin1) {
        this.gameWin1 = gameWin1;
    }

    public float getGameWin2() {
        return gameWin2;
    }

    public void setGameWin2(float gameWin2) {
        this.gameWin2 = gameWin2;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

    public boolean isWritten() {
        return isWritten;
    }

    public void setWritten(boolean written) {
        isWritten = written;
    }

    public int[][] getScoreArray() {
        return scoreArray;
    }

    public void setScoreArray(int[][] scoreArray) {
        this.scoreArray = scoreArray;
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
