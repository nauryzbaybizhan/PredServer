package avtostavka.Threads;

import avtostavka.bookmakers.FonBetTennis;
import org.openqa.selenium.TimeoutException;

public class FonBetTennisThread {

    public static String live = "/live/tennis", line = "/bets/tennis";
    public static int liveCounter;

    public void startFonBetWork() {
        Thread lineThread = new Thread(new Runnable() {
            @Override
            public void run() {
                FonBetTennis fonBetTennis = new FonBetTennis();
                fonBetTennis.init(line);
                int iter = 0;
                while (true) {
                    try {
                        if (iter == 5000) {
                            iter = 0;
                            fonBetTennis.close();
                            fonBetTennis.init(line);
                        }
                        iter++;
                        fonBetTennis.parseLine(iter);
                        Thread.sleep(500);
                    } catch (TimeoutException | InterruptedException e) {
                        fonBetTennis.close();
                        fonBetTennis.init(line);
                    }
                }
            }
        });
        Thread mainPageThread = new Thread(new Runnable() {
            @Override
            public void run() {
                FonBetTennis fonBetTennis = new FonBetTennis();
                fonBetTennis.init(live);
                int iter = 0;
                while (true) {
                    try {
                        if (iter == 5000) {
                            iter = 0;
                            fonBetTennis.close();
                            fonBetTennis.init(live);
                        }
                        iter++;
                        fonBetTennis.parseMainPage(iter);
                        fonBetTennis.checkMatches();
                        Thread.sleep(500);
                    } catch (TimeoutException | InterruptedException e) {
                        fonBetTennis.close();
                        fonBetTennis.init(live);
                    }
                }
            }
        });
//        lineThread.start();
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        mainPageThread.start();
    }
}
