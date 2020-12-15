package com.drsf.api.repositories;

import com.drsf.api.postgres.repositories.LinkedAccountRepository;
import com.drsf.api.robinhood.model.PaginatedRobinhoodHTTPResponse;
import com.drsf.api.robinhood.service.RobinhoodProxy;
import com.dsrf.api.meta.HttpQueryMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public abstract class PaginatedRobinhoodRepository<T,U> implements BaseRobinhoodRepository<U> {

    @Autowired
    RobinhoodProxy robinhoodProxy;

    @Autowired
    LinkedAccountRepository linkedAccountRepository;

    abstract List<U> findAll(String username, HttpQueryMeta query);
}
