package be.vdab.webshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    private final DataSource dataSource;

    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    JdbcUserDetailsManager createPrincipal() {
        var manager = new JdbcUserDetailsManager(dataSource);
        manager.setUsersByUsernameQuery("""
                select email as username, password, enabled from users where email = ?
                """);
        manager.setAuthoritiesByUsernameQuery("""
                select username, authority from authorities where username = ?
                """);
        return manager;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin(withDefaults());
        http.authorizeHttpRequests(req ->
                req.requestMatchers("/css/**", "/js/**", "/images/**", "/", "/index.html", "/product.html", "/shopcart.html").permitAll()
                        .requestMatchers("/principal/**", "/prodgroups/**", "/products/**").permitAll()
                        .requestMatchers("/login.html")
                        .hasAuthority("admin")
                        .anyRequest().authenticated());
        return http.build();
    }
}
