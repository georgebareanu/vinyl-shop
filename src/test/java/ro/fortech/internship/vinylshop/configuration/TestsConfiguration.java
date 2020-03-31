package ro.fortech.internship.vinylshop.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ro.fortech.internship.vinylshop.config.security.JwtTokenUtil;
import ro.fortech.internship.vinylshop.user.service.UserService;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class TestsConfiguration {

    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return mock(JwtTokenUtil.class);
    }

    @Bean
    public UserService userService() {
        return mock(UserService.class);
    }
}
