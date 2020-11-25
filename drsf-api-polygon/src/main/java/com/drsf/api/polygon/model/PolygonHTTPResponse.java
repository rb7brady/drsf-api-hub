package com.drsf.api.polygon.model;

import com.drsf.api.polygon.config.PolygonEndpoint;
import reactor.core.publisher.Mono;

public interface PolygonHTTPResponse<T> {

    public Mono<T> getMonoObject(PolygonEndpoint URI, String ... params);




}
