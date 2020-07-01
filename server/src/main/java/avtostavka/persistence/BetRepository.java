package avtostavka.persistence;

import avtostavka.dto.BetDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BetRepository extends CrudRepository<BetDto, Integer> {
}
