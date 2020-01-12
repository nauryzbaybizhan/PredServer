package avtostavka.bookmakers;

import avtostavka.App;
import avtostavka.Options;
import avtostavka.data.TennisGame;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

public class FonBetTennis extends BookParser<TennisGame> {

    public static String live = "#!/live/tennis", line = "#!/bets/tennis";
    public static int liveCounter;
    public static ConcurrentHashMap<String, TennisGame> retTennis = new ConcurrentHashMap<>();

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
    public void parseLine(int i) {
        try {
            if (i==1 || i == 2500) {
                driver.get(fonBet + line);
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
                                key = link.attr("href").replace("bets","live");
                            } catch (NullPointerException e) {
                                //e.printStackTrace();
                                continue;
                            }
                            if (key == null || key.isEmpty()) {
                                sportData = new TennisGame();
                            } else {
                                sportData = retTennis.get(key);
                            }
                            if (sportData == null) {
                                sportData = new TennisGame();
                            }
                            try {
                                link = tr.select("td.table__col._size_long > div.table__match-title > a").first();
                                sportData.setReference(link.attr("href").replace("bets","live"));
                            } catch (NullPointerException e) {
                                //e.printStackTrace();
                                continue;
                            }
                            sportData.setTracked(true);
                            sportData.setLeague(element.select("tr.table__row._type_segment._sport_4 > th.table__col._type_head._size_long > div > h2").text().trim());
                            sportData.setTeams(tr.select("td.table__col._size_long > div.table__match-title._indent_1 > a").text().trim());
                            float win1, win2;
                            try {
                                win1 = Float.parseFloat(tr.select("td:nth-child(3)").text().trim());
                                win2 = Float.parseFloat(tr.select("td:nth-child(4)").text().trim());
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                                continue;
                            }
                            sportData.setLineWin1(win1);
                            sportData.setLineWin2(win2);
                            retTennis.put(sportData.getReference(), sportData);
                        }
                    }
                }
            }
        } catch (NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
            driver.get(fonBet + line);
        }
    }

    @Override
    public void parseMainPage(int i) {
        try {
            if (i==1 || i == 2500) {
                driver.get(fonBet + live);
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
                                sportData = retTennis.get(key);
                            }
                            if (sportData == null) {
                                sportData = new TennisGame();
                            }
                            try {
                                link = tr.select("td.table__col._size_long > div.table__match-title > a").first();
                                sportData.setReference(link.attr("href"));
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                                continue;
                            }
                            sportData.liveTicker++;
                            sportData.setLeague(element.select("tr.table__row._type_segment._sport_4 > th.table__col._type_head._size_long > div > h2").text().trim());
                            sportData.setTeams(tr.select("td.table__col._size_long > div.table__match-title._indent_1 > a").text().trim());
                            String time = tr.select("td.table__col._size_long > div.table__timescore > div.table__time > span.table__time-text").text().trim();
                            if (time.isEmpty()) {
                                sportData.setTime("0:0");
                            }  else {
                                sportData.setTime(time);
                            }
                            sportData.setFloatTime(charCheck.parseTime(sportData.getTime()));
                            sportData.setLive(true);
                            String score;
                            try {
                                score = tr.select("td.table__col._size_long > div.table__timescore > div.table__score > span.table__score-more").text().trim();
                            } catch (NoSuchElementException e) {
                                //e.printStackTrace();
                                score = "";
                            }
                            sportData.setScore(score);
                            sportData.setScoreArray(charCheck.parseFullScore(sportData.getScore()));
                            if (sportData.isTracked()) retTennis.put(sportData.getReference(), sportData);
                        }
                    }
                }
            }
        } catch (NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
            driver.get(fonBet + live);
        }
    }

    @Override
    public void checkMatches() {
        liveCounter = 0;
        ArrayList<String> oldMatches = new ArrayList<>();
        for (TennisGame value : retTennis.values()) {
            if (value.isLive()) liveCounter++;
        }
        System.out.println("Match count tennis: " + retTennis.size());
        System.out.println("Live count tennis: " + liveCounter);
        if (retTennis.size() == 0) return;
        for (TennisGame value : retTennis.values()) {
            if (!value.isLive()) continue;
            try {
                value.checkTicker++;
                if (value.checkTicker > value.liveTicker + 20) {
                    System.out.println("deleted");
                    writeStat(value);
                    oldMatches.add(value.getReference());
                }
                if ((value.getScoreArray()[0][1] == 6  && value.getScoreArray()[0][2] == 0) || (value.getScoreArray()[0][1] == 0  && value.getScoreArray()[0][2] == 6)) {
                    driver.get(fonBet + value.getReference());
                    try {
                        webElement = (new WebDriverWait(driver, 3))
                                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.ev-scoreboard__head--oZ14Y._sport_9--2Owzw > span.ev-scoreboard__head-caption--2PsZ1")));
                    } catch (TimeoutException e) {
                        continue;
                    }
                    clickIfQuarter4(value.getScoreArray());
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
                driver.get(fonBet + value.getReference());
            }
        }
        for (String ref: oldMatches
        ) {
            retTennis.remove(ref);
        }
    }

    public boolean clickIfQuarter4(int[][] allScore) {
        String currentQuarterSelector = "section > div.event-view__inner--2Eg5p > div.ev-tabs--3u3Yz > span:nth-child(2)";
        try {
            webElement = (new WebDriverWait(driver, 4))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(currentQuarterSelector)));
            webElement = driver.findElement(By.cssSelector(currentQuarterSelector));
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("var elem=arguments[0]; setTimeout(function() {elem.click();}, 100)", webElement);
            String set = driver.findElement(By.cssSelector(currentQuarterSelector)).getText().trim();
            switch (allScore[0][0]) {
                case 1: {
                    return set.contains("2");
                }
                case 2: {
                    return set.contains("3");
                }
                case 3: {
                    return set.contains("4");
                }
            }
        } catch (org.openqa.selenium.NoSuchElementException | ElementClickInterceptedException | TimeoutException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void writeStat(TennisGame value) {
        //super.writeStat(value);
    }
}
