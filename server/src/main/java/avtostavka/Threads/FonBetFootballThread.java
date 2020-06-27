package avtostavka.Threads;

import avtostavka.bookmakers.FonBetFootball;
import org.openqa.selenium.TimeoutException;

public class FonBetFootballThread {

    public static String live = "/live/football", line = "/bets/football";
    public static int liveCounter;

    public void startFonBetWork() {
        Thread lineThread = new Thread(new Runnable() {
            @Override
            public void run() {
                FonBetFootball fonBetFootball = new FonBetFootball();
                fonBetFootball.init(line);
                int iter=0;
                while (true) {
                    try {
                        if (iter == 5000) {
                            iter = 0;
                            fonBetFootball.close();
                            fonBetFootball.init(line);
                        }
                        iter++;
                        fonBetFootball.parseLine(iter);
                        Thread.sleep(500);
                    } catch (TimeoutException | InterruptedException e) {
                        fonBetFootball.close();
                        fonBetFootball.init(line);
                    }
                }
            }
        });
        Thread mainPageThread = new Thread(new Runnable() {
            @Override
            public void run() {
                FonBetFootball fonBetFootball = new FonBetFootball();
                fonBetFootball.init(live);
                int iter=0;
                while (true) {
                    try {
                        if (iter == 5000) {
                            iter = 0;
                            fonBetFootball.close();
                            fonBetFootball.init(live);
                        }
                        iter++;
                        fonBetFootball.parseMainPage(iter);
                        fonBetFootball.checkMatches();
                        Thread.sleep(500);
                    } catch (TimeoutException | InterruptedException e) {
                        fonBetFootball.close();
                        fonBetFootball.init(live);
                    }
                }
            }
        });
        Thread matchesThread = new Thread(new Runnable() {
            @Override
            public void run() {
                FonBetFootball fonBet = new FonBetFootball();
                fonBet.init(live);
                int i =0;
                while (true) {
                    if (liveCounter < 2) {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    i++;
                    if (i == 5000) {
                        fonBet.close();
                        fonBet.init(live);
                        i=0;
                    }
                    try {
                        fonBet.openEachGame();
                    } catch (TimeoutException e) {
                        fonBet.close();
                        fonBet.init(live);
                    }
                }
            }
        });
        mainPageThread.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        matchesThread.start();
    }
}
