package avtostavka.bookmakers;

import avtostavka.*;
import avtostavka.Threads.FonBetBasketballThread;
import avtostavka.data.BasketballGame;
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
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class FonBetBasketball extends BookParser<BasketballGame> {

    public static String live = "#!/live/basketball", line = "#!/bets/basketball";
    public static int liveCounter;

    @Override
    public void init(String ref) {
        boolean equal = false;
        do {
            try {
                ChromeOptions options = Options.getInstance().getOptions(false);
                driver = new ChromeDriver(options);
                driver.get(fonBet + ref);
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
    public void parseMainPage(int i) {
        try {
            if (i==1 || i == 1000) {
                driver.get(fonBet+FonBetBasketballThread.live);
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
                                sportData = Data.retBasketball.get(key);
                            }
                            if (sportData == null) {
                                sportData = new BasketballGame();
                            }
                            sportData.isTracked = true;
                            try {
                                link = tr.select("td.table__col._size_long > div.table__match-title > a").first();
                                sportData.reference = link.attr("href");
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                                continue;
                            }
                            sportData.league = element.select("tr.table__row._type_segment > th.table__col._type_head._size_long > div > h2").text().trim();
                            sportData.teams = tr.select("td.table__col._size_long > div.table__match-title._indent_1 > a").text().trim();
                            String time = tr.select("td.table__col._size_long > div.table__timescore > div.table__time > span.table__time-text").text().trim();
                            if (time.isEmpty()) {
                                sportData.time = "0:0";
                            }  else {
                                sportData.time = time;
                            }
                            sportData.floatTime = charCheck.parseTime(sportData.time);
                            sportData.liveTicker++;
                            String score;
                            try {
                                if (sportData.floatTime <=10) {
                                    score = "(" + tr.select("td.table__col._size_long > div.table__timescore > div.table__score > span.table__score-normal").text().replace(":", "-").trim() + ")";
                                } else {
                                    score = tr.select("td.table__col._size_long > div.table__timescore > div.table__score > span.table__score-more").text().trim();
                                }
                            } catch (NoSuchElementException e) {
//                                e.printStackTrace();
                                score = "";
                            }
                            sportData.score = score;
                            sportData.scoreArray = charCheck.parseFullScore(sportData.score);
                            float gameTotal;
                            try {
                                gameTotal = Float.parseFloat(tr.select("td:nth-child(13)").text().trim());
                            } catch (NumberFormatException e) {
                                gameTotal = -1;
                            }
                            sportData.gameTotal = gameTotal;
                            if (sportData.isTracked) {
                                Data.retBasketball.put(sportData.reference, sportData);
                            }
                        }
                    }
                }
            }
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            e.printStackTrace();
            driver.get(fonBet + FonBetBasketballThread.live);
        }
    }

    @Override
    public void checkMatches() {
        liveCounter = 0;
        for (BasketballGame value : Data.retBasketball.values()) {
            if (value.isLive) liveCounter++;
        }
        System.out.println("Match count: " + Data.retBasketball.size());
        System.out.println("Live count: " + liveCounter);
        ArrayList<String> oldMatches = new ArrayList<>();

        if (Data.retBasketball.size() == 0) return;
        for (BasketballGame value : Data.retBasketball.values()) {
            if (!value.isLive) continue;
            value.checkTicker++;
            writeStat(value);
            if (value.checkTicker > value.liveTicker + 20) {
                System.out.println("deleted");
                oldMatches.add(value.reference);
            }
        }
        for (String ref: oldMatches
        ) {
            Data.retBasketball.remove(ref);
        }
    }


    public boolean openEachGame() {
        boolean ret = true;
        if (Data.retBasketball.size() == 0) return false;
        for (BasketballGame value : Data.retBasketball.values()) {
            if (!value.isLive) continue;
            try {
                if ((value.scoreArray[0][0] == -1) || (value.floatTime < 2) || (value.floatTime > 7.30 && value.floatTime < 12.0) ||
                        (value.floatTime > 17.30 && value.floatTime < 22.0) ||
                        (value.floatTime > 27.30 && value.floatTime < 32.0) ||
                        (value.floatTime > 37.30)) {
                    ret = false;
                    continue;
                }
                if (Data.retBasketball.size() > 1) {
                    driver.get(fonBet + value.reference);
                }
                try {
                    webElement = (new WebDriverWait(driver, 3))
                            .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.ev-scoreboard__head--oZ14Y > span.ev-scoreboard__head-caption--2PsZ1")));
                } catch (TimeoutException e) {
                    driver.get(fonBet + value.reference);
                    continue;
                }
                if (value.floatTime < 30) super.clickCurrQuarter();
                Thread.sleep(100);
                webElement = (new WebDriverWait(driver, 3))
                        .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.line-filter-layout__menu--3YfDq > div > div > div.line-header__menu--GWd-F")));
                webElement = driver.findElement(By.cssSelector("#main"));
                String html = webElement.getAttribute("innerHTML");
                Document document = Jsoup.parse(html);
                try {
                    value.coefB = document.select("div.ev-factors__col--uCmqD._type_factor--oqccY._type_bet--3ZujX._col_total-vo--39ham").text().trim();
                    value.coefM = document.select("div.ev-factors__col--uCmqD._type_factor--oqccY._type_bet--3ZujX._col_total-vu--1P5Ld").text().trim();
                } catch (Exception e) {
                    value.coefB = "";
                    value.coefM = "";
                }
                if (value.quarter < 4) {
                    try {
                        value.quarterTotal = Float.parseFloat(document.select("div.ev-factors__row--3LmVL._type_body--HtZvu > div.ev-factors__col--uCmqD._type_factor--oqccY._type_param--1HrQl._col_total-p--2ryqH").text().trim());
                    } catch (Exception e) {
                        value.quarterTotal = 0;
                    }
                } else {
                    value.quarterTotal = value.gameTotal;
                }
                if (value.floatTime < 38.05) this.getFouls(value, document);
                System.out.println("Teams: " + value.teams);
                System.out.println("Initial total: " + value.gameInitTotal);
                System.out.println("Fouls: " + value.fouls[0] + "-" + value.fouls[1]);
                App.getEventBus().post(value);
                ret = true;
            } catch (Exception e) {
                e.printStackTrace();
                driver.get(fonBet + value.reference);
            }
        }
        return ret;
    }


    private void getFouls(BasketballGame value, Document document) throws ElementClickInterceptedException {
        try {
            String toSelect = "div.ev-scoreboard__head--oZ14Y._sport_3--3NEdk >div.ev-scoreboard__channels--1w5Sa";
            List<Element> divTag = document.select(toSelect+"> div");
            List<Element> aTag = document.select(toSelect+"> a");
            int div = divTag.size();
            int a = aTag.size();           String matchCenter = "центр";
            String checkTitle;
            boolean isPresents = false;
            int iter=0;
            for (Element el: document.select(toSelect+"> div")
            ) {
                iter++;
                try {
                    checkTitle = el.attr("title");
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    continue;
                }
                if (checkTitle.contains(matchCenter)) {
                    isPresents = true;
                    break;
                }
            }
            if (isPresents && value.isSuitableFoulStrategy) {
                String select = toSelect;
                if (div == 1) {
                    select = toSelect+"> div";
                }
                if (div == 2 && a==1 && iter ==1) {
                    select = toSelect+"> div:nth-child(2)";
                }
                if (div == 2 && a==1 && iter ==2) {
                    select = toSelect+"> div:nth-child(3)";
                }
                if (div ==2 && a==2 && iter ==1) {
                    select = toSelect+"> div:nth-child(3)";
                }
                if (div ==2 && a==2 && iter ==2) {
                    select = toSelect+"> div:nth-child(4)";
                }
                long wait = 400;
                webElement = driver.findElement(By.cssSelector(select));
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("var elem=arguments[0]; setTimeout(function() {elem.click();}, 100)", webElement);
                driver.manage().timeouts().implicitlyWait(wait, TimeUnit.MILLISECONDS);
                try {
                    webElement = (new WebDriverWait(driver, 5))
                            .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#coupons__inner > div:nth-child(1)")));
                } catch (Exception e) {
                    driver.get(fonBet + value.reference);
                    e.printStackTrace();
                }
                String id = "mc-event_";
                id += charCheck.parseRef(value.reference).trim();
                try {
                    driver.switchTo().frame(id);
                    webElement = (new WebDriverWait(driver, 5))
                            .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#field-bottom-block > div")));
                    webElement = driver.findElement(By.cssSelector("#field-bottom-block > div"));
                    String html2 = webElement.getAttribute("innerHTML");
                    Document document2 = Jsoup.parse(html2);
                    String one = "1", two = "2", three = "3", four = "4";
                    String checkWord = document2.select("div:nth-child(3) > div.fouls-stats__item__text").text().trim();
                    if (checkWord.contains(one)) {
                        value.quarter = 1;
                    }
                    if (checkWord.contains(two)) {
                        value.quarter = 2;
                    }
                    if (checkWord.contains(three)) {
                        value.quarter = 3;
                    }
                    if (checkWord.contains(four)) {
                        value.quarter = 4;
                    }
                    value.fouls[0] = Integer.parseInt(document2.select("div:nth-child(3) > div:nth-child(1)").text().trim());
                    value.fouls[1] = Integer.parseInt(document2.select("div:nth-child(3) > div:nth-child(3)").text().trim());
                    webElement = driver.findElement(By.cssSelector("#big-header > div.flex-center-between.position-relative > div.flex-center-start.interaction-elements > div:nth-child(3)"));
                    executor = (JavascriptExecutor) driver;
                    executor.executeScript("var elem=arguments[0]; setTimeout(function() {elem.click();}, 100)", webElement);
                } catch (WebDriverException e) {
                    driver.get(fonBet + value.reference);
                    e.printStackTrace();
                }
                driver.switchTo().defaultContent();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
