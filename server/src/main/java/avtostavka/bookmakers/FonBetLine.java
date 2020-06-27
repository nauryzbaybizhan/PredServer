package avtostavka.bookmakers;

import avtostavka.Data;
import avtostavka.Options;
import avtostavka.Threads.FonBetBasketballThread;
import avtostavka.Threads.FonBetTennisThread;
import avtostavka.data.BasketballGame;
import avtostavka.data.TennisGame;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;

public class FonBetLine extends BookParser {

    private ChromeDriver driver;
    private WebElement webElement;

    public void init(String ref) {
        boolean equal = false;
        do {
            try {
                driver = new ChromeDriver(Options.getInstance().getOptions(false));
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
                equal = true;
            } catch (Exception e) {
                e.printStackTrace();
                equal = false;
                driver.quit();
            }
        } while (!equal);
    }

    public void close() {driver.quit();}

    public void parseLineBasketBall(int i) {
        String basketball = "Баскетбол";
        try {
            driver.get(fonBet+ FonBetBasketballThread.line);
            webElement = (new WebDriverWait(driver, 12))
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
                                sportData = Data.retBasketball.get(key);
                            }
                            if (sportData == null) {
                                sportData = new BasketballGame();
                            }
                            try {
                                link = tr.select("td.table__col._size_long > div.table__match-title > a").first();
                                sportData.reference = link.attr("href").replace("bets","live");
                            } catch (NullPointerException e) {
                                //e.printStackTrace();
                                continue;
                            }
                            sportData.isTracked = true;
                            sportData.league = element.select("tr.table__row._type_segment > th.table__col._type_head._size_long > div > h2").text().trim();
                            sportData.teams = tr.select("td.table__col._size_long > div.table__match-title._indent_1 > a").text().trim();
                            float initTotal, lineWin1, lineWin2;
                            try {
                                lineWin1 = Float.parseFloat(tr.select("td:nth-child(3)").text().trim());
                                lineWin2 = Float.parseFloat(tr.select("td:nth-child(5)").text().trim());
                                initTotal = Float.parseFloat(tr.select("td:nth-child(13)").text().trim());
                            } catch (NumberFormatException e) {
                                continue;
                            }
                            sportData.lineWin1 = lineWin1;
                            sportData.lineWin2 = lineWin2;
                            sportData.gameInitTotal = initTotal;
                            Data.retBasketball.put(sportData.reference, sportData);
                        }
                    }
                }
            }
        } catch (NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
            driver.get(fonBet+ FonBetBasketballThread.line);
        }
    }

    public void parseLineTennis(int i) {
        String tennis = "Теннис";
        try {
            driver.get(fonBet+ FonBetTennisThread.line);
            webElement = (new WebDriverWait(driver, 12))
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
                                sportData = Data.retTennis.get(key);
                            }
                            if (sportData == null) {
                                sportData = new TennisGame();
                            }
                            try {
                                link = tr.select("td.table__col._size_long > div.table__match-title > a").first();
                                sportData.reference = link.attr("href").replace("bets","live");
                            } catch (NullPointerException e) {
                                //e.printStackTrace();
                                continue;
                            }
                            sportData.isTracked = true;
                            sportData.league = element.select("tr.table__row._type_segment > th.table__col._type_head._size_long > div > h2").text().trim();
                            sportData.teams = tr.select("td.table__col._size_long > div.table__match-title._indent_1 > a").text().trim();
                            float win1, win2;
                            try {
                                win1 = Float.parseFloat(tr.select("td:nth-child(3)").text().trim());
                                win2 = Float.parseFloat(tr.select("td:nth-child(4)").text().trim());
                            } catch (NumberFormatException e) {
                                //e.printStackTrace();
                                continue;
                            }
                            sportData.lineWin1 = win1;
                            sportData.lineWin2 = win2;
                            if (win1 > 1.02 && win2 > 1.02) {
                                Data.retTennis.put(sportData.reference, sportData);
                            }
                        }
                    }
                }
            }
        } catch (NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
            driver.get(fonBet + FonBetTennisThread.line);
        }
    }
}
