package ro.fortech.internship.vinylshop.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/vinyls").permitAll()
                .antMatchers("/api/users/login").permitAll()
                .antMatchers("/api/users/cart").authenticated()
                .antMatchers("/api/users/orders").authenticated()
                .antMatchers("/api/users/{id}").authenticated()
                .antMatchers(HttpMethod.POST, "/api/vinyls").hasAuthority("MANAGER")
                .antMatchers("/api/vinyls/{id}").hasAuthority("MANAGER")
                .antMatchers("/api/users").hasAuthority("MANAGER")
                .antMatchers("/api/users/{userId}/orders").hasAuthority("MANAGER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}