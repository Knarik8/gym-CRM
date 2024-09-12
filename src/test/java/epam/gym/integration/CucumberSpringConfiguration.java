package epam.gym.integration;

import epam.gym.App;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = App.class)
public class CucumberSpringConfiguration {
}
