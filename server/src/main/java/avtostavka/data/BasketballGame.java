package avtostavka.data;

public class BasketballGame extends Game {

    public float lineWin1;

    public float lineWin2;

    public int liveTicker;

    public int checkTicker;

    public float gameInitTotal;

    public float gameTotal;

    public float[] quarterInitTotal = new float[3];

    public int[] sendQuarterFoulStrategy = new int[4];

    public String[] sendCoefFoulStrategy = new String[4];

    public float[] sendTBFoulStrategy = new float[4];

    public boolean isSuitableStrategyNick = false;

    public boolean isSuitableFoulStrategy = false;

    public boolean isSuitableTwentyMinStrategy = false;

    public boolean isSentStr12 = false;

    public float str12SendTotal;

    public boolean[] isSentStrategyNickTotalB = new boolean[4];

    public boolean[] isSentStrategyNickTotalM = new boolean[4];

    public boolean isSentTwentyMinStrategy = false;

    public boolean isSentStrategy20 = false;

    public boolean[] isSentFoulStrategy = new boolean[4];

    public boolean[] isWrittenFoulStrategy = new boolean[4];

    public boolean[] isSentStrategy3 = new boolean[4];

    public int[][] scoreArray = new int[4][3];

    public int quarter = -1;

    public String koef = "default";

    public String koefM = "default";

    public float quarterTotal;

    public int[] fouls = new int[2];

    public String coefB = "default";

    public String coefM = "default";
}
