package epam.gym.config;

import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;


import jakarta.jms.ConnectionFactory;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
@EnableJms
public class JmsConfiguration {
//
//    /**
//     * Create JMS Connection Factory (used to create connections to the message broker (e.g., ActiveMQ))
//     * @param connectionFactory
//     * @param configurer
//     * @return Connection Factory
//     */
//    @Bean
//    public JmsListenerContainerFactory<?> jmsFactory(ConnectionFactory connectionFactory,
//                                                     DefaultJmsListenerContainerFactoryConfigurer configurer) {
//        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
////        factory.setMessageConverter(jacksonJmsMessageConverter());
//
//        configurer.configure(factory, connectionFactory);
//        return factory;
//    }
//
//    /**
//     * Serialize message content to json using TextMessage
//     *
//     * @return Message Converter (is responsible for converting messages between different formats when sending and receiving JMS messages)
//     */
////    @Bean
////    public MessageConverter jacksonJmsMessageConverter() {
////        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
////        converter.setTargetType(MessageType.TEXT);
//////        converter.setTypeIdPropertyName("_asb_"); //Sets a custom property in the message header to identify the type of message, helping the converter correctly transform incoming messages into the appropriate Java objects.
////        return converter;
////    }
}
