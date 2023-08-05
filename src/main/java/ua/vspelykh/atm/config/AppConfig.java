package ua.vspelykh.atm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    public static final int ATM_ID = 1;

    @Bean
    public AtmIdHolder atmIdHolder() {
        return new AtmIdHolder(ATM_ID);
    }
}