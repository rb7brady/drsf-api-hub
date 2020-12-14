package com.drsf.api.postgres.repositories;

import com.drsf.api.entities.Dividend;
import com.drsf.api.entities.LinkedAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkedAccountRepository extends JpaRepository<LinkedAccount, Long> {

    public LinkedAccount findByUsernameAndType(String username, String type);

    public List<LinkedAccount> findAllByUsername(String username);

}
