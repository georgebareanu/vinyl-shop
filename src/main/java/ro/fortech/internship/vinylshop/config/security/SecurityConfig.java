package ro.fortech.internship.vinylshop.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ROLE_CUSTOMER = "CUSTOMER";
    private static final String ROLE_MANAGER = "MANAGER";
    private static final String ROLE_ADMIN = "ADMIN";

    private final TokenAuthorizationFilter filter;

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
                .antMatchers("/api/users/cart", "/api/users/orders",
                        "/api/users/{id}").hasAuthority(ROLE_CUSTOMER)
                .antMatchers(HttpMethod.POST, "/api/vinyls").hasAuthority("MANAGER")
                .antMatchers("/api/vinyls/{id}", "/api/users/cart/{id}",
                        "/api/users", "/api/users/{userId}/orders").hasAuthority(ROLE_MANAGER)
                .and()
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}