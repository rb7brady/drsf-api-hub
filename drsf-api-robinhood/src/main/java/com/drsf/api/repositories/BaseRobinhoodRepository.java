package com.drsf.api.repositories;

import com.drsf.api.postgres.repositories.LinkedAccountRepository;
import com.drsf.api.robinhood.service.RobinhoodProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BaseRobinhoodRepository {


    @Autowired
    LinkedAccountRepository linkedAccountRepository;



}
