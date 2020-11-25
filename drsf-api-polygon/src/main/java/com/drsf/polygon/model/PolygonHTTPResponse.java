package com.drsf.polygon.model;

import com.drsf.polygon.config.PolygonEndpoint;
import reactor.core.publisher.Mono;

public interface PolygonHTTPResponse<T> {

    public Mono<T> getMonoObject(PolygonEndpoint URI, String ... params);




}
