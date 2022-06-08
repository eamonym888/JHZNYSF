package com.bsoft.com.jhznysf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;

/*@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class
})*/
@SpringBootApplication
//@ConfigurationPropertiesScan
public class JhznysfApplication {

    public static void main(String[] args) {
        SpringApplication.run(JhznysfApplication.class, args);
    }

}
