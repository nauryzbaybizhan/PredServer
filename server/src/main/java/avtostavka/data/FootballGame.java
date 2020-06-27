package avtostavka.data;

public class FootballGame extends Game {

    public boolean isSentStr1 = false;

    public boolean isSentStr2 = false;

    public boolean isWrittenStr1 = false;

    public boolean isWrittenStr2 = false;

    public int sendScoreStr1;

    public int[] scoreArray = new int[2];

    public float[] fourthGoalTime = new float[2];

    public float lineWin1;

    public float lineWin2;

    public int liveTicker;

    public int checkTicker;

    public float gameTotalB;

    public float gameTotalBCoef;

    public float goalBefore75Koef;

    public boolean[] fourthGoalBefore75 = new boolean[2];
}
