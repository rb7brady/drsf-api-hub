package com.drsf.api.polygon;

import com.drsf.api.polygon.config.UserConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(Application.class);


    /**
     * Args 0 = Alpaca Public Key
     * @param args
     */
    public static void main(String[] args) {

        try {
            ApplicationContext context = SpringApplication.run(Application.class, args);




        } catch (Exception e) {
            e.printStackTrace();
            log.debug(e.getLocalizedMessage());
        }
    }

    @Override
    public void run( ApplicationArguments args ) throws Exception
    {
        UserConf userConf = new UserConf(args.getSourceArgs());
        log.debug("Application.run(args) invoked.");
    }

}
