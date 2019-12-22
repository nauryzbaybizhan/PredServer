package avtostavka.data;

public class HandballGame extends Game {

    private int[] scoreArray = new int[3];

    private String lineRef;

    private float gameInitTotal;

    private float gameTotal;

    private float quarterInitTotal;

    private float quarterTotal;

    private float sendTotal;

    private boolean isSentStr1 = false;

    private boolean isSentStr2 = false;

    private boolean isSentStr3 = false;

    private boolean isWrittenStr1 = false;

    private boolean isWrittenStr2 = false;

    private boolean isWrittenStr3 = false;

    private boolean isRestrictedStrat2 = false;

    private boolean isSuitableStrat1 = false;

    private boolean isSuitableStrat2 = false;

    public int[] getScoreArray() {
        return this.scoreArray;
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

    public float getQuarterInitTotal() {
        return this.quarterInitTotal;
    }

    public void setQuarterInitTotal(float quarterInitTotal) {
        this.quarterInitTotal = quarterInitTotal;
    }

    public float getQuarterTotal() {
        return this.quarterTotal;
    }

    public void setQuarterTotal(float quarterTotal) {
        this.quarterTotal = quarterTotal;
    }

    public float getSendTotal() {
        return this.sendTotal;
    }

    public void setSendTotal(float sendTotal) {
        this.sendTotal = sendTotal;
    }

    public String getLineRef() {
        return this.lineRef;
    }

    public void setLineRef(String lineRef) {
        this.lineRef = lineRef;
    }

    public boolean isSentStr1() {
        return this.isSentStr1;
    }

    public void setSentStr1(boolean sentStr1) {
        this.isSentStr1 = sentStr1;
    }

    public boolean isSentStr2() {
        return this.isSentStr2;
    }

    public void setSentStr2(boolean sentStr2) {
        this.isSentStr2 = sentStr2;
    }

    public boolean isSentStr3() {
        return this.isSentStr3;
    }

    public void setSentStr3(boolean sentStr3) {
        this.isSentStr3 = sentStr3;
    }

    public boolean isWrittenStr1() {
        return this.isWrittenStr1;
    }

    public void setWrittenStr1(boolean writtenStr1) {
        this.isWrittenStr1 = writtenStr1;
    }

    public boolean isWrittenStr2() {
        return this.isWrittenStr2;
    }

    public void setWrittenStr2(boolean writtenStr2) {
        this.isWrittenStr2 = writtenStr2;
    }

    public boolean isWrittenStr3() {
        return this.isWrittenStr3;
    }

    public void setWrittenStr3(boolean writtenStr3) {
        this.isWrittenStr3 = writtenStr3;
    }

    public boolean isRestrictedStrat2() {
        return this.isRestrictedStrat2;
    }

    public void setRestrictedStrat2(boolean restrictedStrat2) {
        this.isRestrictedStrat2 = restrictedStrat2;
    }

    public boolean isSuitableStrat1() {
        return this.isSuitableStrat1;
    }

    public void setSuitableStrat1(boolean suitableStrat1) {
        this.isSuitableStrat1 = suitableStrat1;
    }

    public boolean isSuitableStrat2() {
        return this.isSuitableStrat2;
    }

    public void setSuitableStrat2(boolean suitableStrat2) {
        this.isSuitableStrat2 = suitableStrat2;
    }
}
