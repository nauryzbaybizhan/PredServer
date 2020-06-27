package avtostavka.bookmakers;

import avtostavka.App;
import avtostavka.BookmakerDataParser;
import avtostavka.Data;
import avtostavka.Options;
import avtostavka.Threads.FonBetFootballThread;
import avtostavka.Threads.FonBetTennisThread;
import avtostavka.data.FootballGame;
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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

public class FonBetFootball extends BookParser<FootballGame> {

    public static int liveCounter;

    public static ConcurrentHashMap<String, FootballGame> retFootball= new ConcurrentHashMap<>();

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
                if (sport.equals(football)) {
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
            if (i==1 || i == 1000) {
                driver.get(fonBet + FonBetFootballThread.line);
            }
            webElement = (new WebDriverWait(driver, 7))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.line-filter-layout__content--q-JdM > section")));
            webElement = driver.findElement(By.cssSelector("div.line-filter-layout__content--q-JdM > section"));
            String sport = driver.findElement(By.cssSelector("div.line-header__filter--2dOYd._type_sport--3QJEd > h1")).getText().trim();
            if (sport.equals(football)) {
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
                            FootballGame sportData;
                            String key;
                            Element link;
                            try {
                                link = tr.select("td.table__col._size_long > div.table__match-title > a").first();
                                key = link.attr("href").replace("bets", "live");
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                                continue;
                            }
                            if (key == null || key.isEmpty()) {
                                sportData = new FootballGame();
                            } else {
                                sportData = Data.retFootball.get(key);
                            }
                            if (sportData == null) {
                                sportData = new FootballGame();
                            }
                            try {
                                link = tr.select("td.table__col._size_long > div.table__match-title > a").first();
                                sportData.reference = link.attr("href").replace("bets", "live");
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                                continue;
                            }
                            sportData.league = element.select("tr.table__row._type_segment > th.table__col._type_head._size_long > div > h2").text().trim();
                            sportData.teams = tr.select("td.table__col._size_long > div.table__match-title._indent_1 > a").text().trim();
                            float win1, win2;
                            try {
                                win1 = Float.parseFloat(tr.select("td:nth-child(3)").text().trim());
                                win2 = Float.parseFloat(tr.select("td:nth-child(5)").text().trim());
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                                continue;
                            }
                            if (win1 > 0 && win2 > 0) {
                                sportData.lineWin1 = win1;
                                sportData.lineWin2 = win2;
                                sportData.isTracked = true;
                            }
                            if (sportData.isTracked) {
                                Data.retFootball.put(sportData.reference, sportData);
                            }
                        }
                    }
                }
            }
        } catch (NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
            driver.get(fonBet + FonBetFootballThread.line);
        }
    }

    @Override
    public void parseMainPage(int i) {
        try {
            if (i==1 || i == 1000) {
                driver.get(fonBet + FonBetFootballThread.live);
            }
            webElement = (new WebDriverWait(driver, 7))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.line-filter-layout__content--q-JdM > section")));
            webElement = driver.findElement(By.cssSelector("div.line-filter-layout__content--q-JdM > section"));
            String sport = driver.findElement(By.cssSelector("div.line-header__filter--2dOYd._type_sport--3QJEd > h1")).getText().trim();
            if (sport.equals(football)) {
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
                            FootballGame sportData;
                            String key;
                            Element link;
                            try {
                                link = tr.select("td.table__col._size_long > div.table__match-title > a").first();
                                key = link.attr("href");
                            } catch (NullPointerException e) {
                                //e.printStackTrace();
                                continue;
                            }
                            if (key == null || key.isEmpty()) {
                                sportData = new FootballGame();
                            } else {
                                sportData = Data.retFootball.get(key);
                            }
                            if (sportData == null) {
                                sportData = new FootballGame();
                            }
                            try {
                                link = tr.select("td.table__col._size_long > div.table__match-title > a").first();
                                sportData.reference = link.attr("href");
                            } catch (NullPointerException e) {
                                //e.printStackTrace();
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
                                score = tr.select("td.table__col._size_long > div.table__timescore > div.table__score > span.table__score-normal").text().replace(":","-").trim();
                            } catch (NoSuchElementException e) {
                                //e.printStackTrace();
                                score = "";
                            }
                            sportData.score = score;
                            sportData.scoreArray = charCheck.parseScore(sportData.score);
                            try {
                                sportData.gameTotalB = Float.parseFloat(tr.select("td:nth-child(13)").text().trim());
                            } catch (Exception e) {
                                sportData.gameTotalB = 0;
                            }
                            try {
                                sportData.gameTotalBCoef = Float.parseFloat(tr.select("td:nth-child(14)").text().trim());
                            } catch (Exception e) {
                                sportData.gameTotalBCoef = 0;
                            }
                            float win1, win2;
                            if (sportData.floatTime < 0.1) {
                                try {
                                    win1 = Float.parseFloat(tr.select("td:nth-child(3)").text().trim());
                                    win2 = Float.parseFloat(tr.select("td:nth-child(5)").text().trim());
                                } catch (NumberFormatException e) {
                                    win1 = 0;
                                    win2 = 0;
                                }
                                if (win1 > 0) {
                                    sportData.lineWin1 = win1;
                                    sportData.lineWin2 = win2;
                                    sportData.isTracked = true;
                                }
                            }
                            if (sportData.isTracked) Data.retFootball.put(sportData.reference, sportData);
                        }
                    }
                }
            }
        } catch (NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
            driver.get(fonBet + FonBetFootballThread.live);
        }
    }

    @Override
    public void checkMatches() {
        FonBetFootballThread.liveCounter = 0;
        for (FootballGame value : Data.retFootball.values()) {
            if (value.isLive) FonBetFootballThread.liveCounter++;
        }
        ArrayList<String> oldMatches = new ArrayList<>();
        System.out.println("Match count football: " + Data.retFootball.size());
        if (Data.retFootball.size() == 0) return;
        for (FootballGame value : Data.retFootball.values()) {
            value.checkTicker++;
            if (value.scoreArray[1] == 4 && value.floatTime <= 75) {
                value.fourthGoalBefore75[0] = true;
            }
            if (value.scoreArray[2] == 4 && value.floatTime <= 75) {
                value.fourthGoalBefore75[1] = true;
            }
            if (value.checkTicker > value.liveTicker + 20) {
                writeStat(value);
                System.out.println("deleted");
                oldMatches.add(value.reference);
            }
            App.getEventBus().post(value);
        }
        for (String ref: oldMatches
        ) {
            Data.retFootball.remove(ref);
        }
    }

    public void openEachGame() {
        if (Data.retFootball.size() == 0) return;
        System.out.println("Match count football: " + Data.retFootball.size());
        String addDataSelector = "div.event-view__factors-wrap--_ORLN > div.ev-grids--KwN1P";
        String newTotalSelector;
        for (FootballGame value : Data.retFootball.values()) {
            try {
                value.ticker++;
                if (value.floatTime < 54.3 || value.floatTime > 60.0) continue;
                value.isLive = true;
                driver.get(fonBet + value.reference);
                try {
                    webElement = (new WebDriverWait(driver, 3))
                            .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.ev-scoreboard--1gaQy > div.ev-scoreboard__head--oZ14Y")));
                } catch (TimeoutException e) {
                    continue;
                }
                Thread.sleep(100);
                webElement = (new WebDriverWait(driver, 3))
                        .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(addDataSelector)));
                webElement = driver.findElement(By.cssSelector(addDataSelector));
                String html = webElement.getAttribute("innerHTML");
                Document document = Jsoup.parse(html);
                String endoTotalSelector;
                int selectorCounter = 0;
                for (Element element : document.select("div.table__details")
                ) {
                    selectorCounter++;
                    String val = element.select("table > thead > tr > th:nth-child(1)").text().trim();
                    if (val.equalsIgnoreCase("Период гола")) {
                        Elements elements = element.select("table > tbody > tr");
                        int size = elements.size();
                        switch (size) {
                            case 1: {
                                endoTotalSelector = "tr > ";
                                String betText;
                                try {
                                    betText = element.select("table > tbody > " + endoTotalSelector + "td.table__grid-col._type_text").text().trim();
                                } catch (Exception e) {
                                    continue;
                                }//div.event-view__factors-wrap--_ORLN > div.ev-grids--KwN1P > div:nth-child(11) > table > tbody > tr > td:nth-child(2)
                                if (betText.contains("4") && betText.contains("75")) {
                                    newTotalSelector = addDataSelector + " > div:nth-child("+selectorCounter+") > " + "table > tbody > " + endoTotalSelector + "td.table__grid-col._type_btn";
                                    value.goalBefore75Koef = Float.parseFloat(driver.findElement(By.cssSelector(newTotalSelector)).getText().trim());
                                }
                                break;
                            }
                            case 2: {
                                String endoTotalSelector1 = "tr:nth-child(1) > ", endoTotalSelector2 = "tr:nth-child(2) > ";
                                String betText1, betText2;
                                try {
                                    betText1 = element.select("table > tbody > " + endoTotalSelector1 + "td.table__grid-col._type_text").text().trim();
                                    betText2 = element.select("table > tbody > " + endoTotalSelector2 + "td.table__grid-col._type_text").text().trim();
                                } catch (Exception e) {
                                    continue;
                                }
                                if (betText1.contains("4") && betText1.contains("75")) {
                                    newTotalSelector = addDataSelector + " > div:nth-child("+selectorCounter+") > " + "table > tbody > " + endoTotalSelector1 + "td.table__grid-col._type_btn";
                                    value.goalBefore75Koef = Float.parseFloat(driver.findElement(By.cssSelector(newTotalSelector)).getText().trim());
                                }
                                if (betText2.contains("4") && betText2.contains("75")) {
                                    newTotalSelector = addDataSelector + " > div:nth-child("+selectorCounter+") > " + "table > tbody > " + endoTotalSelector2 + "td.table__grid-col._type_btn";
                                    value.goalBefore75Koef = Float.parseFloat(driver.findElement(By.cssSelector(newTotalSelector)).getText().trim());
                                }
                                break;
                            }
                            case 3: {
                                String endoTotalSelector1 = "tr:nth-child(1) > ", endoTotalSelector2 = "tr:nth-child(2) > ", endoTotalSelector3 = "tr:nth-child(3) > ";
                                String betText1, betText2, betText3;
                                try {
                                    betText1 = element.select("table > tbody > " + endoTotalSelector1 + "td.table__grid-col._type_text").text().trim();
                                    betText2 = element.select("table > tbody > " + endoTotalSelector2 + "td.table__grid-col._type_text").text().trim();
                                    betText3 = element.select("table > tbody > " + endoTotalSelector3 + "td.table__grid-col._type_text").text().trim();
                                } catch (Exception e) {
                                    continue;
                                }
                                if (betText1.contains("4") && betText1.contains("75")) {
                                    newTotalSelector = addDataSelector + " > div:nth-child("+selectorCounter+") > " + "table > tbody > " + endoTotalSelector1 + "td.table__grid-col._type_btn";
                                    value.goalBefore75Koef = Float.parseFloat(driver.findElement(By.cssSelector(newTotalSelector)).getText().trim());
                                }
                                if (betText2.contains("4") && betText2.contains("75")) {
                                    newTotalSelector = addDataSelector + " > div:nth-child("+selectorCounter+") > " + "table > tbody > " + endoTotalSelector2 + "td.table__grid-col._type_btn";
                                    value.goalBefore75Koef = Float.parseFloat(driver.findElement(By.cssSelector(newTotalSelector)).getText().trim());
                                }
                                if (betText3.contains("4") && betText3.contains("75")) {
                                    newTotalSelector = addDataSelector + " > div:nth-child("+selectorCounter+") > " + "table > tbody > " + endoTotalSelector3 + "td.table__grid-col._type_btn";
                                    value.goalBefore75Koef = Float.parseFloat(driver.findElement(By.cssSelector(newTotalSelector)).getText().trim());
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
            } catch (org.openqa.selenium.NoSuchElementException | InterruptedException e) {
                e.printStackTrace();
                driver.get(fonBet + value.reference);
            }
        }
    }

    @Override
    public void writeStat(FootballGame value)  {
        if (value.isSentStr1 && !value.isWrittenStr1) {
            writeStr1(value);
        }
        if (value.isSentStr2 && !value.isWrittenStr2) {
            writeStr2(value);
        }
    }

    private void writeStr1(FootballGame value) {
        try {
            int result;
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String curDate = dateFormat.format(date);
            value.isWrittenStr1 = true;
            int sum = value.scoreArray[1] + value.scoreArray[2];
            if (sum > value.sendScoreStr1) result = 1;
            else result = 0;
            String text = /*Main.config.footballCount + */n + curDate + n + value.league + n + value.teams + n + value.sendScoreStr1 + n + value.score + n + result + "\n";
            this.getFile(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeStr2(FootballGame value) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String curDate = dateFormat.format(date);
            value.isWrittenStr2 = true;
            boolean goalBefore75 = value.fourthGoalTime[0] <= 75 || value.fourthGoalTime[1] <= 75;
            String text = curDate + n + value.league + n + value.teams + n + value.score + goalBefore75 + "\n";
            this.getFile2(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getFile(String text) throws IOException {
        try {
            String a ="";
            Files.write(Paths.get("footballStr1.csv"), a.getBytes(), StandardOpenOption.APPEND);
        }
        catch (IOException e) {
            try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("footballStr1.csv", false),
                    StandardCharsets.UTF_8)) {
                String startText = "Номер сигнала" + n + "Дата" + n + "Лиги" + n + "Команды" + n + "Счет сигнала" + n + "Счет" + n + "результат" + System.lineSeparator();
                osw.write(startText);
            }
        }
        try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("footballStr1.csv", true),
                StandardCharsets.UTF_8)) {
            osw.append(text);
            updateCounter();
        }
    }

    public void getFile2(String text) throws IOException {
        try {
            String a ="";
            Files.write(Paths.get("footballStr2.csv"), a.getBytes(), StandardOpenOption.APPEND);
        }
        catch (IOException e) {
            try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("footballStr2.csv", false),
                    StandardCharsets.UTF_8)) {
                String startText = "Дата" + n + "Лиги" + n + "Команды" + n + "Счет"+ n + "Гол до 75-й" + System.lineSeparator();
                osw.write(startText);
            }
        }
        try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("footballStr2.csv", true),
                StandardCharsets.UTF_8)) {
            osw.append(text);
            updateCounter();
        }
    }
}
