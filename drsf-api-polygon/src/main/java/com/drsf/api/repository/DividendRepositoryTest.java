package com.drsf.api.repository;

import com.drsf.api.entities.Dividend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DividendRepositoryTest extends JpaRepository<Dividend, Long> {

    public List<Dividend> findAllByTicker(String ticker);
}
