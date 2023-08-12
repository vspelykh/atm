package ua.vspelykh.atm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for creating and initializing application beans.
 *
 * @version 1.0
 */
@Configuration
public class AppConfig {

    public static final int ATM_ID = 1;

    /**
     * Creates and configures an instance of AtmIdHolder bean.
     *
     * @return An AtmIdHolder instance initialized with the ATM_ID constant.
     */
    @Bean
    public AtmIdHolder atmIdHolder() {
        return new AtmIdHolder(ATM_ID);
    }
}