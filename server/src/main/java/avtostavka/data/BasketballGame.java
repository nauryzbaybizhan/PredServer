package avtostavka.data;

public class BasketballGame extends Game {

    public int[] sendQuarter = new int[4];

    public float[] sendTB = new float[4];

    public String[] sendKoef = new String[4];

    private float gameInitTotal;

    private float gameTotal;

    private float sendTotal;

    private int[][] scoreArray = new int[4][3];

    private boolean isSentStr12;

    private boolean[] isSentFoulStrategy = new boolean[4];

    private boolean isSentTwentyMinStrategy;

    private boolean[] isSentStrategy3 = new boolean[4];

    private boolean isSuitableStr12;

    private boolean isSuitableFoulStrategy;

    private boolean isSuitableTwentyMinStrategy;

    private boolean isSuitableStrategy3;

    public int quarter = -1;

    public int[] fouls = new int[2];

    public String koef = "default";

    public String koefM = "default";

    public float quarterTotal;

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

    public boolean[] isSentFoulStrategy() {
        return isSentFoulStrategy;
    }

    public void setSentFoulStrategy(boolean[] sentFoulStrategy) {
        isSentFoulStrategy = sentFoulStrategy;
    }

    public boolean isSentTwentyMinStrategy() {
        return isSentTwentyMinStrategy;
    }

    public void setSentTwentyMinStrategy(boolean sentTwentyMinStrategy) {
        isSentTwentyMinStrategy = sentTwentyMinStrategy;
    }

    public boolean[] isSentStrategy3() {
        return isSentStrategy3;
    }

    public void setSentStrategy3(boolean[] sentStrategy3) {
        isSentStrategy3 = sentStrategy3;
    }

    public boolean isSuitableStr12() {
        return isSuitableStr12;
    }

    public void setSuitableStr12(boolean suitableStr12) {
        isSuitableStr12 = suitableStr12;
    }

    public boolean isSuitableFoulStrategy() {
        return isSuitableFoulStrategy;
    }

    public void setSuitableFoulStrategy(boolean suitableFoulStrategy) {
        isSuitableFoulStrategy = suitableFoulStrategy;
    }

    public boolean isSuitableTwentyMinStrategy() {
        return isSuitableTwentyMinStrategy;
    }

    public void setSuitableTwentyMinStrategy(boolean suitableTwentyMinStrategy) {
        isSuitableTwentyMinStrategy = suitableTwentyMinStrategy;
    }

    public boolean isSuitableStrategy3() {
        return isSuitableStrategy3;
    }

    public void setSuitableStrategy3(boolean suitableStrategy3) {
        isSuitableStrategy3 = suitableStrategy3;
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
