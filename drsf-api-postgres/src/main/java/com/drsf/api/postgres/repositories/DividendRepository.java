package com.drsf.api.postgres.repositories;

import com.drsf.api.entities.Dividend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DividendRepository extends JpaRepository<Dividend, Long> {

    public List<Dividend> findAllByTicker(String ticker);
}
