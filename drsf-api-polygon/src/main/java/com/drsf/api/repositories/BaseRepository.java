package com.drsf.api.repositories;

import com.drsf.api.polygon.service.PolygonProxy;
import com.drsf.api.postgres.repositories.LinkedAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BaseRepository {

    @Autowired
    LinkedAccountRepository linkedAccountRepository;

    @Autowired
    PolygonProxy polygonProxy;
}
