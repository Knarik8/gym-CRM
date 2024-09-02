package epam.gym.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import epam.gym.entity.TrainerWorkload;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class ReportMessageConverter implements MessageConverter {

    private final ObjectMapper objectMapper;

    public ReportMessageConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    @NonNull
    public Message toMessage(@NonNull Object object, @NonNull Session session) throws JMSException, MessageConversionException {
        try {
            String jsonString = objectMapper.writeValueAsString(object);
            return session.createTextMessage(jsonString);
        } catch (JsonProcessingException e) {
            log.error("Error converting object to JSON", e);
            throw new MessageConversionException("Error converting object to JSON", e);
        }
    }



    @Override
    @NonNull
    public Object fromMessage(@NonNull Message message) throws JMSException, MessageConversionException {
        try {
            TextMessage textMessage = (TextMessage) message;
            String jsonString = textMessage.getText();
            return objectMapper.readValue(jsonString, TrainerWorkload.class);
        } catch (IOException e) {
            log.error("Error converting JSON to object", e);
            throw new MessageConversionException("Error converting JSON to object", e);
        }
    }
}
