package com.drsf.api.postgres.repositories;

import com.drsf.api.entities.AccountOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOrderRepository extends JpaRepository<AccountOrder, Long> {

}
