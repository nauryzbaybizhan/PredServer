package avtostavka.data;

public class TennisGame extends Game {

    public boolean isSuitable = false;

    public boolean isRestricted = false;

    public boolean isSent = false;

    public boolean isWritten = false;

    public int[][] scoreArray = new int[5][3];

    public float lineWin1;

    public float lineWin2;

    public int checkTicker = 0;

    public int liveTicker = 0;

    public float quarterTotal;

    public float quarterTotalBKoef;
}
