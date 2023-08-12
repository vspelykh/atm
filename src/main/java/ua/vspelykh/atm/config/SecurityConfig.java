package ua.vspelykh.atm.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static ua.vspelykh.atm.config.ConfigConstants.*;
import static ua.vspelykh.atm.controller.utils.PageUrls.*;


/**
 * Configuration class for setting up security configurations in the application.
 *
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final DataSource dataSource;

    /**
     * Configures the security filter chain.
     *
     * @param http The HttpSecurity object to configure security settings.
     * @return A configured SecurityFilterChain instance.
     * @throws Exception If there's an error during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(configurer -> configurer
                        .requestMatchers(PERMIT_ALL_URLS).permitAll()
                        .requestMatchers(AUTHENTICATED_URLS).authenticated()
                        .requestMatchers(ANONYMOUS_URLS).anonymous()
                )
                .logout(logout -> logout.logoutUrl(LOGOUT_URL).logoutSuccessUrl(HOME_URL).permitAll())
                .formLogin(c -> c.loginPage(LOGIN_URL));
        return http.build();
    }

    /**
     * Provides a custom implementation of JdbcDao for user and authority authentication.
     *
     * @return A JdbcDaoImpl instance configured with data source and queries.
     */
    @Bean
    public JdbcDaoImpl jdbcDao() {
        JdbcDaoImpl jdbcDao = new JdbcDaoImpl();
        jdbcDao.setDataSource(dataSource);
        jdbcDao.setUsersByUsernameQuery(SQL_LOG_ACCOUNT);
        jdbcDao.setAuthoritiesByUsernameQuery(SQL_LOG_AUTHORITIES);
        return jdbcDao;
    }

    /**
     * Provides a BCrypt password encoder for password hashing.
     *
     * @return A BCryptPasswordEncoder instance for password encoding.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
