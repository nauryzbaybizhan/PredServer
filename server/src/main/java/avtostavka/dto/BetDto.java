package avtostavka.dto;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BetDto {
    @Id
    private int BetId;
    private String BetName;
    private String StrategyName;
    private String Link;
    private String Bet;
}
