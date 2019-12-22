package avtostavka.data;

public class FootballGame extends Game {

    private boolean isSuitable = false;

    private boolean isRestricted = false;

    private boolean isSent = false;

    private boolean isWritten = false;

    private int[] scoreArray = new int[2];

    private float win1;

    private float win2;

    public int liveTicker;

    public int checkTicker;

    private int sendSum;

    public int getSendSum() {
        return sendSum;
    }

    public void setSendSum(int sendSum) {
        this.sendSum = sendSum;
    }

    public float getWin1() {
        return win1;
    }

    public void setWin1(float win1) {
        this.win1 = win1;
    }

    public float getWin2() {
        return win2;
    }

    public void setWin2(float win2) {
        this.win2 = win2;
    }

    public boolean isSuitable() {
        return isSuitable;
    }

    public void setSuitable(boolean suitable) {
        isSuitable = suitable;
    }

    public boolean isRestricted() {
        return isRestricted;
    }

    public void setRestricted(boolean restricted) {
        isRestricted = restricted;
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

    public int[] getScoreArray() {
        return scoreArray;
    }

    public void setScoreArray(int[] scoreArray) {
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