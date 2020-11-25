package com.drsf.api.polygon.config;

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
