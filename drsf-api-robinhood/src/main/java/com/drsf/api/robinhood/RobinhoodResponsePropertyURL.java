package com.drsf.api.robinhood;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RobinhoodResponsePropertyURL {
     boolean requiresAuthentication() default false;
}
