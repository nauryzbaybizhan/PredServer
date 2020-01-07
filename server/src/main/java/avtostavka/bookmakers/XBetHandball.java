package avtostavka.bookmakers;

import avtostavka.App;
import avtostavka.Options;
import avtostavka.data.HandballGame;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
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
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class XBetHandball extends BookParser<HandballGame> {

    public static String live = "live/Handball/", line = "line/Handball/";
    public static int liveCounter = 0;
    public static ConcurrentHashMap<String, HandballGame> retHandball = new ConcurrentHashMap<>();

    @Override
    public void init(String ref) {
        boolean equal = false;
        do {
            try {
                ChromeOptions options = Options.getInstance().getOptions(false);
                driver = new ChromeDriver(options);
                driver.get(xStavka + ref);
                webElement = (new WebDriverWait(driver, 15))
                        .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#hottest_games > div")));
                String currUrl = driver.getCurrentUrl();
                if (currUrl.equals(xStavka + ref)) equal = true;
                else {
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
    public void close() {driver.quit();}

    @Override
    public void parseLine(int i) {
        try {
            if (i==1) driver.get(xStavka + line);
            webElement = (new WebDriverWait(driver, 7))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#games_content")));
            String html;
            try {
                webElement = driver.findElement(By.cssSelector("#games_content > div > div:nth-child(2)"));
                html = webElement.getAttribute("innerHTML");
            } catch (StaleElementReferenceException e) {
                e.printStackTrace();
                return;
            }
            Document document = Jsoup.parse(html);
            Elements allLeagues = document.select("div");
            String currLeague;
            for (Element league : allLeagues.select("div")
            ) {
                Element dataName;
                String dashBoard;
                try {
                    dataName = league.select("div").first();
                    dashBoard = dataName.attr("data-name");
                    if (dashBoard.equals("dashboard-champ-content")) {
                        currLeague = league.select("div.c-events__item.c-events__item_head.greenBack > div.c-events__name > a").text().trim();
                        for (Element match: league.select("div.c-events__item.c-events__item_col")
                        ) {
                            HandballGame sportData;
                            String key, display;
                            Element link, style;
                            try {
                                link = match.select("div.c-events__item.c-events__item_game > a").first();
                                key = link.attr("href");
                            } catch (NullPointerException  e) {
                                continue;
                            }
                            if (key == null || key.isEmpty()) {
                                sportData = new HandballGame();
                            } else {
                                sportData = retHandball.get(key);
                            }
                            if (sportData == null) {
                                sportData = new HandballGame();
                            }
                            try {
                                link = match.select("div.c-events__item.c-events__item_game > a").first();
                                sportData.setLineRef(link.attr("href"));
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                                continue;
                            }
                            float initTotal;
                            try {
                                initTotal = Float.parseFloat(match.select("div.c-events__item.c-events__item_game > div.c-bets > a:nth-child(8)").text().trim());
                            } catch (Exception e) {
                                continue;
                                //e.printStackTrace();
                            }
                            if (initTotal > 0) sportData.setGameInitTotal(initTotal);
                            sportData.setTeam1(match.select("div.c-events__item.c-events__item_game > a > span > span:nth-child(1)").text().trim());
                            sportData.setTeam2(match.select("div.c-events__item.c-events__item_game > a > span > span:nth-child(2)").text().trim());
                            sportData.setTeams(sportData.getTeam1() + " - " + sportData.getTeam2());
                            sportData.setLeague(currLeague);
//                            try {
//                                driver.get(xStavka + sportData.lineRef);
//                                webElement = (new WebDriverWait(driver, 7))
//                                        .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#allBetsTable > div:nth-child(2) > div:nth-child(1) > div > div")));
//                                webElement = driver.findElement(By.cssSelector("#allBetsTable > div:nth-child(2) > div:nth-child(1) > div > div"));
//                                webElement.click();
//                                String total = driver.findElement(By.cssSelector("#allBetsTable > div:nth-child(2) > div:nth-child(1) > div > div.bets.betCols2 > div:nth-child(1) > span.bet_type")).getText().replace("Б","").trim();
//                                sportData.quarterInitTotal = Float.parseFloat(total);
//                                System.out.println(sportData.quarterInitTotal);
//                            } catch (Exception e) {
//                                continue;
//                            }
                            sportData.setTracked(true);
                            retHandball.put(sportData.getReference(), sportData);
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        } catch (NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
            driver.get(xStavka+ line);
        }
    }

    @Override
    public void parseMainPage(int i) {
        try {
            if (i==1) {
                driver.get(xStavka + live);
            }
            webElement = (new WebDriverWait(driver, 7))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#games_content")));
            String html;
            try {
                webElement = driver.findElement(By.cssSelector("#games_content > div > div:nth-child(1)"));
                html = webElement.getAttribute("innerHTML");
            } catch (StaleElementReferenceException e) {
                e.printStackTrace();
                return;
            }
            ArrayList<String> oldMatches = new ArrayList<>();
            Document document = Jsoup.parse(html);
            Elements allLeagues = document.select("div");
            String currLeague;
            for (Element league : allLeagues.select("div")
            ) {
                Element dataName;
                String dashBoard;
                try {
                    dataName = league.select("div").first();
                    dashBoard = dataName.attr("data-name");
                    if (dashBoard.equals("dashboard-champ-content")) {
                        currLeague = league.select("div.c-events__item.c-events__item_head.greenBack > div.c-events__name > a").text().trim();
                        for (Element match: league.select("div.c-events__item.c-events__item_col")
                        ) {
                            HandballGame sportData;
                            String key, display;
                            Element link, style;
                            try {
                                link = match.select("div > div.c-events-scoreboard > div:nth-child(1) > a").first();
                                key = link.attr("href");
                            } catch (NullPointerException  e) {
                                continue;
                            }
                            if (key == null || key.isEmpty()) {
                                sportData = new HandballGame();
                            } else {
                                sportData = retHandball.get(key);
                            }
                            if (sportData == null) {
                                sportData = new HandballGame();
                            }
                            try {
                                link = match.select("div > div.c-events-scoreboard > div:nth-child(1) > a").first();
                                sportData.setReference(link.attr("href"));
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                                continue;
                            }
                            float total;
                            try {
                                total = Float.parseFloat(match.select("div > div.c-bets > a:nth-child(8)").text().trim());
                            } catch (Exception e) {
                                total = 0;
                            }
                            try {
                                sportData.setTime(match.select("div > div.c-events-scoreboard > div:nth-child(2) > div.c-events-scoreboard__subitem > div.c-events__time > span:nth-child(1)").text().trim());
                            } catch (Exception e) {
                                sportData.setTime("60:0");
                            }
                            sportData.setFloatTime(charCheck.parseTime(sportData.getTime()));
                            String score1 = match.select("div > div.c-events-scoreboard > div:nth-child(1) > div > div:nth-child(1) > span.c-events-scoreboard__cell.c-events-scoreboard__cell--all").text().trim();
                            String score2 = match.select("div > div.c-events-scoreboard > div:nth-child(1) > div > div:nth-child(2) > span.c-events-scoreboard__cell.c-events-scoreboard__cell--all").text().trim();
                            sportData.setScore(score1 + "-" + score2);
                            sportData.setScoreArray(charCheck.parseScore(sportData.getScore()));
                            if (Float.parseFloat(score1) == 0 && Float.parseFloat(score2) == 0 && total > 0) {
                                sportData.setTracked(true);
                                sportData.setGameInitTotal(total);
                            }
                            if (total > 0) sportData.setGameTotal(total);
                            sportData.setTeam1(match.select("div > div.c-events-scoreboard > div:nth-child(1) > a > span > div:nth-child(1)").text().trim());
                            sportData.setTeam2(match.select("div > div.c-events-scoreboard > div:nth-child(1) > a > span > div:nth-child(2)").text().trim());
                            sportData.setTeams(sportData.getTeam1() + " - " + sportData.getTeam2());
                            sportData.setLeague(currLeague);
                            sportData.setLive(true);
                            if (sportData.getFloatTime() == 60.0 && sportData.isTracked()) {
                                writeStat(sportData);
                                oldMatches.add(sportData.getReference());
                            }
                            retHandball.put(sportData.getReference(), sportData);
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
            for (String ref: oldMatches
            ) {
                retHandball.remove(ref);
            }
        } catch (NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
            driver.get(xStavka + live);
        }
    }

    public void checkMatches(ConcurrentHashMap<String, HandballGame> retHandball) {
        liveCounter = 0;
        for (HandballGame value : retHandball.values()) {
            if (value.isLive()) liveCounter++;
        }
        System.out.println("Match count: " + retHandball.size());
        System.out.println("Live count: " + liveCounter);
        if (retHandball.size() == 0) return;
        for (HandballGame value : retHandball.values()) {
            if (!value.isLive()) continue;
            App.getEventBus().post(value);
        }
    }

    @Override
    public void writeStat(HandballGame value) {
        try {
            int result;
            String text;
            int [] scoreArray = value.getScoreArray();
            if (value.isSentStr1() && !value.isWrittenStr1()) {
                value.setWrittenStr1(true);
                if (scoreArray[1] + scoreArray[2] > value.getSendTotal()) {
                    result = 1;
                } else result = -1;
                text = value.getLeague() + n + value.getTeams() + n + value.getStrategy() + n + value.getScore() + n + value.getSendTotal() + n
                        + value.getGameInitTotal() + n + result + System.lineSeparator();
                this.getFile(text);
            }
            if (value.isSentStr2() && !value.isWrittenStr2()) {
                value.setWrittenStr2(true);
                if (scoreArray[1] + scoreArray[2] < value.getSendTotal()) {
                    result = 1;
                } else result = -1;
                text = value.getLeague() + n + value.getTeams() + n + value.getStrategy() + n + value.getScore() + n + value.getSendTotal() + n
                        + value.getGameInitTotal() + n + result + System.lineSeparator();
                this.getFile(text);
            }
        } catch (IOException| NumberFormatException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void getFile(String text) throws IOException {
        try {
            String a ="";
            Files.write(Paths.get("handball.csv"), a.getBytes(), StandardOpenOption.APPEND);
        }
        catch (IOException e) {
            try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("handball.csv", false),
                    StandardCharsets.UTF_8)) {
                String startText = "Лиги" + n + "Команды" + n + "Стратегия" + n + "Счет" + n + "Тотал сигнала" + n + "Начальный тотал" + n + "Результат" + System.lineSeparator();
                osw.write(startText);
            }
        }
        try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("handball.csv", true),
                StandardCharsets.UTF_8)) {
            osw.append(text);
            updateCounter();
        }
    }

    @Override
    public void updateCounter() {
        super.updateCounter();
    }
}
