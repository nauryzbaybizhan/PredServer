package avtostavka;

import avtostavka.data.*;

import java.util.concurrent.ConcurrentHashMap;

public class Data {

    public static ConcurrentHashMap<String, BasketballGame> retBasketball = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, HandballGame> retHandball = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, FootballGame> retFootball = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, TennisGame> retTennis = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, VolleyballGame> retVolleyball = new ConcurrentHashMap<>();
}
