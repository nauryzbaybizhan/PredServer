package avtostavka.bookmakers;

import avtostavka.App;
import avtostavka.Filter;
import avtostavka.Options;
import avtostavka.data.BasketballGame;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

public class FonBetBasketball extends BookParser<BasketballGame> {

    public static String live = "#!/live/basketball", line = "#!/bets/basketball";
    public static int liveCounter;

    public static ConcurrentHashMap<String, BasketballGame> retBasketball = new ConcurrentHashMap<>();

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
                if (sport.equals(basketball)) {
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
    public void parseLine(int i) {
        try {
            if (i==1 || i == 2500) {
                driver.get(fonBet + line);
            }
            webElement = (new WebDriverWait(driver, 7))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.line-filter-layout__content--q-JdM > section")));
            webElement = driver.findElement(By.cssSelector("div.line-filter-layout__content--q-JdM > section"));
            String sport = driver.findElement(By.cssSelector("div.line-header__filter--2dOYd._type_sport--3QJEd > h1")).getText().trim();
            if (sport.equals(basketball)) {
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
                            BasketballGame sportData;
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
                                sportData = new BasketballGame();
                            } else {
                                sportData = retBasketball.get(key);
                            }
                            if (sportData == null) {
                                sportData = new BasketballGame();
                            }
                            try {
                                link = tr.select("td.table__col._size_long > div.table__match-title > a").first();
                                sportData.setReference(link.attr("href").replace("bets","live"));
                            } catch (NullPointerException e) {
                                //e.printStackTrace();
                                continue;
                            }
                            sportData.setTracked(true);
                            sportData.setLeague(element.select("tr.table__row._type_segment._sport_3 > th.table__col._type_head._size_long > div > h2").text().trim());
                            sportData.setTeams(tr.select("td.table__col._size_long > div.table__match-title._indent_1 > a").text().trim());
                            if (Filter.getInstance().isAllowed(sportData.getLeague(), App.config.basketballBlackList)) {
                                sportData.setRestricted12Strategy(false);
                            } else continue;
                            float initTotal;
                            try {
                                initTotal = Float.parseFloat(tr.select("td:nth-child(13)").text().trim()) / 4;
                            } catch (NumberFormatException e) {
                                //e.printStackTrace();
                                continue;
                            }
                            sportData.setGameInitTotal(initTotal);
                            sportData.setSuitable12Strategy(true);
                            retBasketball.put(sportData.getReference(), sportData);
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
            if (sport.equals(basketball)) {
                webElement = (new WebDriverWait(driver, 7))
                        .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section > div.table__flex-container > table")));
                webElement = driver.findElement(By.cssSelector("div.line-filter-layout__content--q-JdM > section"));
                String html = webElement.getAttribute("innerHTML");
                Document document = Jsoup.parse(html);
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
                            BasketballGame sportData;
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
                                sportData = new BasketballGame();
                            } else {
                                sportData = retBasketball.get(key);
                            }
                            if (sportData == null) {
                                sportData = new BasketballGame();
                            }
                            try {
                                link = tr.select("td.table__col._size_long > div.table__match-title > a").first();
                                sportData.setReference(link.attr("href"));
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                                continue;
                            }
                            sportData.setLeague(element.select("tr.table__row._type_segment._sport_3 > th.table__col._type_head._size_long > div > h2").text().trim());
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
                                if (sportData.getFloatTime() <=10) score = "(" + tr.select("td.table__col._size_long > div.table__timescore > div.table__score > span.table__score-normal").text().replace(":","-").trim() + ")";
                                else score = tr.select("td.table__col._size_long > div.table__timescore > div.table__score > span.table__score-more").text().trim();
                            } catch (NoSuchElementException e) {
                                //e.printStackTrace();
                                score = "";
                            }
                            sportData.setScore(score);
                            sportData.setScoreArray(charCheck.parseFullScore(sportData.getScore()));
                            float gameTotal;
                            try {
                                gameTotal = Float.parseFloat(tr.select("td:nth-child(13)").text().trim()) / 4;
                            } catch (NumberFormatException e) {
                                //e.printStackTrace();
                                gameTotal = -1;
                            }
                            sportData.setGameTotal(gameTotal);
                            if (sportData.isTracked()) retBasketball.put(sportData.getReference(), sportData);
                            if (sportData.getFloatTime() == 40.0) {
                                writeStat(sportData);
                                oldMatches.add(sportData.getReference());
                            }
                        }
                    }
                }
                for (String ref: oldMatches
                ) {
                    retBasketball.remove(ref);
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
        for (BasketballGame value : retBasketball.values()) {
            if (value.isLive()) liveCounter++;
        }
        System.out.println("Match count: " + retBasketball.size());
        System.out.println("Live count: " + liveCounter);
        if (retBasketball.size() == 0) return;
        for (BasketballGame value : retBasketball.values()) {
            if (!value.isLive()) continue;
            try {
                value.ticker++;
                int [][] allScore = value.getScoreArray();
                value.setScoreArray(charCheck.parseFullScore(value.getScore()));
                if (allScore[0][0] == -1) continue;
                if (value.isSuitable12Strategy()) {
                    System.out.println(value.getLeague());
                    System.out.println(value.getTeams());
                    System.out.println(value.getScore());
                    System.out.println(value.getGameInitTotal());
                    System.out.println(value.getGameTotal());
                    System.out.println(value.getTime());
                    basketballStrategies.checkStrategy(value);
                }
            } catch (StaleElementReferenceException e) {
                e.printStackTrace();
                driver.get(fonBet + value.getReference());
            }
        }
    }

    @Override
    public void writeStat(BasketballGame value) {
        super.writeStat(value);
    }

    @Override
    public void updateCounter() {
        super.updateCounter();
    }
}
