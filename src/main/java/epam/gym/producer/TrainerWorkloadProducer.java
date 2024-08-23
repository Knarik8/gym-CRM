package epam.gym.producer;

import epam.gym.entity.TrainerWorkload;
import epam.gym.service.hibernateImpl.TrainerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class TrainerWorkloadProducer {

    private static final Logger logger = LoggerFactory.getLogger(TrainerWorkloadProducer.class);

    private final JmsTemplate jmsTemplate;


    TrainerWorkloadProducer(JmsTemplate jmsTemplate){
        this.jmsTemplate = jmsTemplate;
    }

    public void sendTo(String destination, TrainerWorkload trainerWorkload) {
        jmsTemplate.convertAndSend(destination, trainerWorkload);
        logger.info("Producer> Trainer Workload Sent to destination: {}", destination);
    }

}
