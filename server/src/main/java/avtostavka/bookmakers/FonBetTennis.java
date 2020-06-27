package avtostavka.bookmakers;

import avtostavka.App;
import avtostavka.BookmakerDataParser;
import avtostavka.Data;
import avtostavka.Options;
import avtostavka.Threads.FonBetTennisThread;
import avtostavka.data.TennisGame;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class FonBetTennis extends BookParser<TennisGame> {

    public static String live = "#!/live/tennis", line = "#!/bets/tennis";
    public static int liveCounter;

    @Override
    public void init(String ref) {
        boolean equal = false;
        do {
            try {
                ChromeOptions options = Options.getInstance().getOptions(false);
                driver = new ChromeDriver(options);
                driver.get(fonBet+ref);
                try {
                    webElement = (new WebDriverWait(driver, 10))
                            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"cookie_policy_popup\"]/div/div/div[2]/a")));
                    webElement = driver.findElement(By.cssSelector("#cookie_policy_popup > div > div > div.modal-window__button-area > a"));
                    webElement.click();
                } catch (Exception e) {
                    //e.printStackTrace();
                }
                webElement = (new WebDriverWait(driver, 40))
                        .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.line-filter-layout__menu--3YfDq > div > div > div.line-header__menu--GWd-F")));
                String sport = driver.findElement(By.cssSelector("div.line-header__filter--2dOYd._type_sport--3QJEd > h1")).getText().trim();
                if (sport.equals(tennis)) {
                    equal = true;
                } else {
                    Thread.sleep(10000);
                    driver.quit();
                }
            } catch (Exception e) {
                e.printStackTrace();
                equal = false;
                driver.quit();
            }
        } while (!equal);
    }

    @Override
    public void close() {
        driver.quit();
    }

    @Override
    public void parseMainPage(int i) {
        try {
            if (i==1 || i == 2500) {
                driver.get(fonBet + FonBetTennisThread.live);
            }
            webElement = (new WebDriverWait(driver, 7))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.line-filter-layout__content--q-JdM > section")));
            webElement = driver.findElement(By.cssSelector("div.line-filter-layout__content--q-JdM > section"));
            String sport = driver.findElement(By.cssSelector("div.line-header__filter--2dOYd._type_sport--3QJEd > h1")).getText().trim();
            if (sport.equals(tennis)) {
                webElement = (new WebDriverWait(driver, 7))
                        .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section > div.table__flex-container > table")));
                webElement = driver.findElement(By.cssSelector("div.line-filter-layout__content--q-JdM > section"));
                String html = webElement.getAttribute("innerHTML");
                Document document = Jsoup.parse(html);
                BookmakerDataParser charCheck = new BookmakerDataParser();
                Elements content = document.select("div.table__flex-container > table");
                for (Element element : content.select("tbody")
                ) {
                    for (Element tr: element.select("tr")
                    ) {
                        Element check;
                        String noClass, yesClass = "table__col _pos_first _indent_1";
                        try {
                            check = tr.select("td:nth-child(1)").first();
                            noClass = check.attr("class").trim();
                        } catch (NullPointerException e) {
                            continue;
                        }
                        if (noClass.equals(yesClass)) {
                            TennisGame sportData;
                            String key;
                            Element link;
                            try {
                                link = tr.select("td.table__col._size_long > div.table__match-title > a").first();
                                key = link.attr("href");
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                                continue;
                            }
                            if (key == null || key.isEmpty()) {
                                sportData = new TennisGame();
                            } else {
                                sportData = Data.retTennis.get(key);
                            }
                            if (sportData == null) {
                                sportData = new TennisGame();
                            }
                            try {
                                link = tr.select("td.table__col._size_long > div.table__match-title > a").first();
                                sportData.reference = link.attr("href");
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                                continue;
                            }
                            sportData.liveTicker++;
                            sportData.league = element.select("tr.table__row._type_segment > th.table__col._type_head._size_long > div > h2").text().trim();
                            sportData.teams = tr.select("td.table__col._size_long > div.table__match-title._indent_1 > a").text().trim();
                            String time = tr.select("td.table__col._size_long > div.table__timescore > div.table__time > span.table__time-text").text().trim();
                            if (time.isEmpty()) {
                                sportData.time = "0:0";
                            }  else {
                                sportData.time = time;
                            }
                            sportData.floatTime = charCheck.parseTime(sportData.time);
                            sportData.isLive = true;
                            String score;
                            try {
                                score = tr.select("td.table__col._size_long > div.table__timescore > div.table__score > span.table__score-more").text().trim();
                            } catch (NoSuchElementException e) {
                                //e.printStackTrace();
                                score = "";
                            }
                            sportData.score = score;
                            sportData.scoreArray = charCheck.parseFullScore(sportData.score);
                            if (sportData.isTracked) Data.retTennis.put(sportData.reference, sportData);
                        }
                    }
                }
            }
        } catch (NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
            driver.get(fonBet + FonBetTennisThread.live);
        }
    }

    @Override
    public void checkMatches() {
        FonBetTennisThread.liveCounter = 0;
        ArrayList<String> oldMatches = new ArrayList<>();
        for (TennisGame value : Data.retTennis.values()) {
            if (value.isLive) liveCounter++;
        }
        System.out.println("Match count tennis: " + Data.retTennis.size());
        System.out.println("Live count tennis: " + liveCounter);
        if (Data.retTennis.size() == 0) return;
        for (TennisGame value : Data.retTennis.values()) {
            if (!value.isLive) continue;
            try {
                value.checkTicker++;
                if (value.checkTicker > value.liveTicker + 20) {
                    System.out.println("deleted");
                    writeStat(value);
                    oldMatches.add(value.reference);
                }
                if ((value.scoreArray[0][1] == 6  && value.scoreArray[0][2] == 0) || (value.scoreArray[0][1] == 0  && value.scoreArray[0][2] == 6)) {
                    driver.get(fonBet + value.reference);
                    try {
                        webElement = (new WebDriverWait(driver, 3))
                                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.ev-scoreboard__head--oZ14Y > span.ev-scoreboard__head-caption--2PsZ1")));
                    } catch (TimeoutException e) {
                        continue;
                    }
                    super.clickCurrQuarter();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    webElement = (new WebDriverWait(driver, 3))
                            .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.line-filter-layout__menu--3YfDq > div > div > div.line-header__menu--GWd-F")));
                    webElement = driver.findElement(By.cssSelector("#main"));
                    String html = webElement.getAttribute("innerHTML");
                    Document document = Jsoup.parse(html);
                    try {
                        value.quarterTotalBKoef = Float.parseFloat(document.select("div.ev-factors__col--uCmqD._type_factor--oqccY._type_bet--3ZujX._col_total-vo--39ham").text().trim());
                    } catch (Exception e) {
                        value.quarterTotalBKoef = 0;
                    }
                    try {
                        value.quarterTotal = Float.parseFloat(document.select("div.ev-factors__row--3LmVL._type_body--HtZvu > div.ev-factors__col--uCmqD._type_factor--oqccY._type_param--1HrQl._col_total-p--2ryqH").text().trim());
                    } catch (Exception e) {
                        value.quarterTotal = 0;
                    }
                    App.getEventBus().post(value);
                }
            } catch (Exception e) {
                e.printStackTrace();
                driver.get(fonBet + value.reference);
            }
        }
        for (String ref: oldMatches
        ) {
            Data.retTennis.remove(ref);
        }
    }

    @Override
    public void writeStat(TennisGame value) {
        //super.writeStat(value);
    }
}
