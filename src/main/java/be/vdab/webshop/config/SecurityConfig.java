package be.vdab.webshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;



@Configuration
@EnableWebSecurity
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
        http
                .csrf(csrf -> csrf.disable())
                .formLogin(form ->
                form.defaultSuccessUrl("/account.html", true)
                        .permitAll())
                .logout(logout ->
                        logout.logoutSuccessUrl("/"));
        http.authorizeHttpRequests(req ->
                req.requestMatchers("/css/**", "/js/**", "/images/**", "/", "/index.html", "/product.html", "/shopcart.html", "/account.html").permitAll()
                        .requestMatchers("/principal/**", "/prodgroups/**", "/products/**", "/users/**").permitAll()
                        .requestMatchers("/login.html")
                        .hasAnyAuthority("admin", "user")
                        .anyRequest().authenticated());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
