package com.drsf.api.polygon.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface persist {

    public boolean key() default false ;

}
