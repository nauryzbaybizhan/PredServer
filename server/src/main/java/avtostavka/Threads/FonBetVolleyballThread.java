package avtostavka.Threads;

import avtostavka.bookmakers.FonBetVolleyball;
import org.openqa.selenium.TimeoutException;

public class FonBetVolleyballThread {

    public static String live = "/live/volleyball", line = "/bets/volleyball";
    public static int liveCounter;

    public void startFonBetWork() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                FonBetVolleyball fonBetVolleyball = new FonBetVolleyball();
                fonBetVolleyball.init(live);
                int iter=0;
                while (true) {
                    try {
                        if (iter == 10000) {
                            iter = 0;
                            fonBetVolleyball.close();
                            fonBetVolleyball.init(live);
                        }
                        iter++;
                        fonBetVolleyball.parseMainPage(iter);
                        Thread.sleep(500);
                    } catch (TimeoutException | InterruptedException e) {
                        fonBetVolleyball.close();
                        fonBetVolleyball.init(live);
                    }
                }
            }
        });
        Thread matchesThread = new Thread(new Runnable() {
            @Override
            public void run() {
                FonBetVolleyball fonBetVolleyball = new FonBetVolleyball();
                fonBetVolleyball.init(live);
                int i =0;
                while (true) {
                    if (liveCounter < 2) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    i++;
                    if (i == 3000) {
                        fonBetVolleyball.close();
                        fonBetVolleyball.init(live);
                        i=0;
                    }
                    try {
                        fonBetVolleyball.checkMatches();
                    } catch (TimeoutException e) {
                        fonBetVolleyball.close();
                        fonBetVolleyball.init(live);
                    }
                }
            }
        });
        thread.start();
        matchesThread.start();
    }
}
