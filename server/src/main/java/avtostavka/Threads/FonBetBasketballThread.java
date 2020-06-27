package avtostavka.Threads;

import avtostavka.bookmakers.FonBetBasketball;
import org.openqa.selenium.TimeoutException;

public class FonBetBasketballThread {

    public static String live = "/live/basketball", line = "/bets/basketball";
    public static int liveCounter;

    public void startFonBetWork() {
        Thread lineThread = new Thread(new Runnable() {
            @Override
            public void run() {
                FonBetBasketball fonBetBasketball = new FonBetBasketball();
                fonBetBasketball.init(line);
                int iter=0;
                while (true) {
                    try {
                        if (iter == 5000) {
                            iter = 0;
                            fonBetBasketball.close();
                            fonBetBasketball.init(line);
                        }
                        iter++;
                        fonBetBasketball.parseLine(iter);
                        Thread.sleep(500);
                    } catch (TimeoutException | InterruptedException e) {
                        fonBetBasketball.close();
                        fonBetBasketball.init(line);
                    }
                }
            }
        });
        Thread mainPageThread = new Thread(new Runnable() {
            @Override
            public void run() {
                FonBetBasketball fonBetBasketball = new FonBetBasketball();
                fonBetBasketball.init(live);
                int iter=0;
                while (true) {
                    try {
                        if (iter == 5000) {
                            iter = 0;
                            fonBetBasketball.close();
                            fonBetBasketball.init(live);
                        }
                        iter++;
                        fonBetBasketball.parseMainPage(iter);
                        fonBetBasketball.checkMatches();
                        Thread.sleep(500);
                    } catch (TimeoutException | InterruptedException e) {
                        fonBetBasketball.close();
                        fonBetBasketball.init(live);
                    }
                }
            }
        });
        Thread matchesThread = new Thread(new Runnable() {
            @Override
            public void run() {
                FonBetBasketball fonBet = new FonBetBasketball();
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
                        if (!fonBet.openEachGame()) {
                            Thread.sleep(1000);
                        }
                    } catch (TimeoutException | InterruptedException e) {
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
