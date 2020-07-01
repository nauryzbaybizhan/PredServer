package avtostavka.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {
    private final BetRepository betRepository;

    @Autowired
    public DatabaseService(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    public BetRepository getBetRepository() {
        return betRepository;
    }
}
