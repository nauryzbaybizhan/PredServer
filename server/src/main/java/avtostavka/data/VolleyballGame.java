package avtostavka.data;

public class VolleyballGame extends Game {

    private String setScore;

    private String fullScore;

    private int setScore1 = -1;

    private int setScore2 = -1;

    public int[][] scoreArray = new int[4][3];

    private float totalQuarter2;

    private float totalMKoefQuarter2;

    private boolean isSent;

    public String getSetScore() {
        return setScore;
    }

    public void setSetScore(String setScore) {
        this.setScore = setScore;
    }

    public String getFullScore() {
        return fullScore;
    }

    public void setFullScore(String fullScore) {
        this.fullScore = fullScore;
    }

    public int getSetScore1() {
        return setScore1;
    }

    public void setSetScore1(int setScore1) {
        this.setScore1 = setScore1;
    }

    public int getSetScore2() {
        return setScore2;
    }

    public void setSetScore2(int setScore2) {
        this.setScore2 = setScore2;
    }

    public float getTotalQuarter2() {
        return totalQuarter2;
    }

    public void setTotalQuarter2(float totalQuarter2) {
        this.totalQuarter2 = totalQuarter2;
    }

    public float getTotalMKoefQuarter2() {
        return totalMKoefQuarter2;
    }

    public void setTotalMKoefQuarter2(float totalMKoefQuarter2) {
        this.totalMKoefQuarter2 = totalMKoefQuarter2;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
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
