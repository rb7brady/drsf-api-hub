package com.drsf.polygon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class UserConf {

    public UserConf(String[] args) {
        this.alpacaPublic = args[0];
    }

    public UserConf() { }


    public void setAlpacaPublic(String alpacaPublic) {
        this.alpacaPublic = alpacaPublic;
    }

    private String alpacaPublic;

    public String getAlpacaPublic() {
        return alpacaPublic;
    }
}
