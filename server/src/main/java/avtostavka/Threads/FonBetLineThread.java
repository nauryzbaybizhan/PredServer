package avtostavka.Threads;

import avtostavka.bookmakers.FonBetLine;
import org.openqa.selenium.TimeoutException;

public class FonBetLineThread {

    public static String line = "/bets/basketball";

    public void parseLine() {
        Thread lineThread = new Thread(new Runnable() {
            @Override
            public void run() {
                FonBetLine fonBetLine = new FonBetLine();
                fonBetLine.init(line);
                int iter=0;
                while (true) {
                    try {
                        if (iter == 5000) {
                            iter = 0;
                            fonBetLine.close();
                            fonBetLine.init(line);
                        }
                        iter++;
                        fonBetLine.parseLineBasketBall(iter);
                        fonBetLine.parseLineTennis(iter);
                        Thread.sleep(1000);
                    } catch (TimeoutException | InterruptedException e) {
                        fonBetLine.close();
                        fonBetLine.init(line);
                    }
                }
            }
        });
        lineThread.start();
    }
}