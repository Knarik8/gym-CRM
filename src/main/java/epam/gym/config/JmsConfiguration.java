package epam.gym.config;

import jakarta.jms.Queue;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@Configuration
@EnableJms
public class JmsConfiguration {

    @Value("${activemq.destination}")
    private String destinationName;

    @Bean
    public Queue queue() {
        return new ActiveMQQueue(destinationName);
    }
}
