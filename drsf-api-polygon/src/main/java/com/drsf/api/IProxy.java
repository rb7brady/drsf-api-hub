package com.drsf.api;


import com.dsrf.api.meta.HttpQueryMeta;
import com.dsrf.api.meta.QueryMeta;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;

public interface IProxy {

    public Object query(QueryMeta queryMeta);

    public Mono<Object> queryAsMono(HttpQueryMeta queryMeta);

    public Flux<?> queryAsFlux(QueryMeta queryMeta);


}
