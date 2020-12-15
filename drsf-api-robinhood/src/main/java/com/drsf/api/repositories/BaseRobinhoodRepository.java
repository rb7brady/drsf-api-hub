package com.drsf.api.repositories;

import com.drsf.api.postgres.repositories.LinkedAccountRepository;
import com.drsf.api.robinhood.service.RobinhoodProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

@NoRepositoryBean
public interface BaseRobinhoodRepository<U> {


}
