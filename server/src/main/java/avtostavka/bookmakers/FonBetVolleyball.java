package avtostavka.bookmakers;

import avtostavka.App;
import avtostavka.BookmakerDataParser;
import avtostavka.Options;
import avtostavka.data.VolleyballGame;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

public class FonBetVolleyball extends BookParser<VolleyballGame> {

    public static String live = "#!/live/volleyball", line = "#!/bets/volleyball";
    public static int liveCounter;

    public static ConcurrentHashMap<String, VolleyballGame> retVolleyball = new ConcurrentHashMap<>();

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
                if (sport.equals(volleyball)) {
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
        super.close();
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
            if (sport.equals(volleyball)) {
                webElement = (new WebDriverWait(driver, 7))
                        .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section > div.table__flex-container > table")));
                webElement = driver.findElement(By.cssSelector("div.line-filter-layout__content--q-JdM > section"));
                String html = webElement.getAttribute("innerHTML");
                Document document = Jsoup.parse(html);
                BookmakerDataParser charCheck = new BookmakerDataParser();
                Elements content = document.select("div.table__flex-container > table");
                ArrayList<String> oldMatches = new ArrayList<>();
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
                            VolleyballGame sportData;
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
                                sportData = new VolleyballGame();
                            } else {
                                sportData = retVolleyball.get(key);
                            }
                            if (sportData == null) {
                                sportData = new VolleyballGame();
                            }
                            try {
                                link = tr.select("td.table__col._size_long > div.table__match-title > a").first();
                                sportData.reference = link.attr("href");
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                                continue;
                            }
                            sportData.league = element.select("tr.table__row._type_segment._sport_9 > th.table__col._type_head._size_long > div > h2").text().trim();
                            sportData.teams = tr.select("td.table__col._size_long > div.table__match-title._indent_1 > a").text().trim();
                            charCheck.parseTeam(sportData);
                            sportData.isLive = true;
                            String setScore, fullScore;
                            try {
                                setScore = tr.select("td.table__col._size_long > div.table__timescore > div.table__score > span.table__score-normal").text().trim();
                                fullScore = tr.select("td.table__col._size_long > div.table__timescore > div.table__score > span.table__score-more").text().trim().replace("*","");
                            } catch (Exception e) {
                                continue;
                            }
                            sportData.setScore = setScore;
                            charCheck.parseSetScore(sportData);
                            sportData.fullScore = fullScore;
                            sportData.scoreArray = charCheck.parseFullScore(fullScore);
                            if (sportData.scoreArray [0][0] == -1) continue;
                            if (sportData.scoreArray [0][0] > 1) continue;
                            retVolleyball.put(sportData.reference, sportData);
                            if (sportData.scoreArray [0][0] == 2) {
                                writeStat(sportData);
                                oldMatches.add(sportData.reference);
                            }
                        }
                    }
                }
                for (String ref: oldMatches
                ) {
                    retVolleyball.remove(ref);
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
        System.out.println("Match count volleyball: " + retVolleyball.size());
        if (retVolleyball.size() == 0) return;
        for (VolleyballGame value : retVolleyball.values()) {
            try {
                value.ticker++;
                if (value.scoreArray[0][0] == -1) continue;
                if (value.scoreArray[0][0] == 1 && value.scoreArray[0][1] + value.scoreArray[0][2] > 46.5) {
                    driver.get(fonBet + value.reference);
                    try {
                        webElement = (new WebDriverWait(driver, 3))
                                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.ev-scoreboard__head--oZ14Y._sport_9--2Owzw > span.ev-scoreboard__head-caption--2PsZ1")));
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
                    value.totalQuarter2 =Float.parseFloat(document.select("div.event-view__factors-wrap--_ORLN > div:nth-child(1) > div.ev-factors__row--3LmVL._type_body--HtZvu > div.ev-factors__col--uCmqD._type_factor--oqccY._type_param--1HrQl._col_total-p--2ryqH").text().trim());
                    value.totalMKoefQuarter2 = Float.parseFloat(document.select("div.event-view__factors-wrap--_ORLN > div:nth-child(1) > div.ev-factors__row--3LmVL._type_body--HtZvu > div.ev-factors__col--uCmqD._type_factor--oqccY._type_bet--3ZujX._col_total-vu--1P5Ld").text().trim());
                    App.getEventBus().post(value);
                }
            } catch (StaleElementReferenceException e) {
                e.printStackTrace();
                driver.get(fonBet + value.reference);
            }
        }
    }
}
