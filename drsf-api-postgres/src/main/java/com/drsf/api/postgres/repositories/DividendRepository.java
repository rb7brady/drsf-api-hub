package drsf.api.postgres.repositories;

import com.drsf.entities.Dividend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface DividendRepository extends JpaRepository<Dividend, Long> {

    public List<Dividend> findAllByTicker(String ticker);
}
