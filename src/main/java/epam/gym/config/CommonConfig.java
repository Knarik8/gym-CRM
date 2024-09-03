package epam.gym.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("epam.gym")
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class CommonConfig {
}
