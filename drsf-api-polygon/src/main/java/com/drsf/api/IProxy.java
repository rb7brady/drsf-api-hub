package com.drsf.api;


import reactor.core.publisher.Flux;

public interface IProxy {

    public Object query();

    public Object queryAsMono();

    public Flux<?> queryAsFlux();


}
