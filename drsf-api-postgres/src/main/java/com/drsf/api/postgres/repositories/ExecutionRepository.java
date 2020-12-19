package com.drsf.api.postgres.repositories;

import com.drsf.api.entities.Execution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExecutionRepository extends JpaRepository<Execution, Long> {
}
