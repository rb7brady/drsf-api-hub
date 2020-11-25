package com.drsf.api.polygon.service;

import com.drsf.api.polygon.config.UserConf;

public class MockUserConfigurationService implements IUserConfigurationService {

    @Override
    public UserConf findConf() {
        UserConf userConf = new UserConf();
        userConf.setAlpacaPublic("AKPK11IOWNSOK8DS2VYK");
        return userConf;
    }
}
