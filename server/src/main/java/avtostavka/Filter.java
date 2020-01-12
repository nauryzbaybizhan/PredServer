package avtostavka;

import java.util.ArrayList;

public class Filter {
    private static Filter ourInstance = new Filter();

    public static Filter getInstance() {
        return ourInstance;
    }

    private Filter() {
    }

    public boolean contains(String currentLeague, ArrayList<String> list) {
        for (String league : list
        ) {
            if (currentLeague.contains(league) || currentLeague.equalsIgnoreCase(league)) {
                return true;
            }
        }
        return false;
    }
}
