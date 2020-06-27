package avtostavka.Threads;

import avtostavka.bookmakers.XBetHandball;
import org.openqa.selenium.TimeoutException;

public class XBetHandballThread {

    public static String live = "live/Handball/", line = "line/Handball/";
    public static int liveCounter = 0;

    public void startXBetWork() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                XBetHandball xBetHandball = new XBetHandball();
                xBetHandball.init(live);
                int iter=0;
                while (true) {
                    try {
                        if (iter == 10000) {
                            iter = 0;
                            xBetHandball.close();
                            xBetHandball.init(live);
                        }
                        iter++;
                        xBetHandball.parseMainPage(iter);
                        xBetHandball.checkMatches();
                        Thread.sleep(500);
                    } catch (TimeoutException | InterruptedException e) {
                        xBetHandball.close();
                        xBetHandball.init(live);
                    }
                }
            }
        });
        thread.start();
    }
}
