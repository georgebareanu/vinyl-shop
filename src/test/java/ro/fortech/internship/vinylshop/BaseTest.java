package ro.fortech.internship.vinylshop;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import ro.fortech.internship.vinylshop.user.setup.UserSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseTest {

    protected TestRestTemplate restTemplate = new TestRestTemplate();

    @Autowired
    protected UserSetup userSetup = new UserSetup();

    @LocalServerPort
    private int port;

    protected String createUrl(String path) {
        return "http://localhost:" + port + path;
    }
}
