package avtostavka;

import java.util.ArrayList;

public class Filter {
    private static Filter ourInstance = new Filter();

    public static Filter getInstance() {
        return ourInstance;
    }

    private Filter() {
    }

    public boolean isAllowed(String currentLeague, ArrayList<String> list) {
        list.add("NBA");
        list.add("Филиппины");
        list.add("NCAA");
        list.add("Китай");
        for (String league: list
        ) {
            if (currentLeague.contains(league) || currentLeague.equalsIgnoreCase(league)) {
                return false;
            }
        }
        return true;
    }
}
