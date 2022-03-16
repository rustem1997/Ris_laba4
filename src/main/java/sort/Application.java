package sort;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.ConnectionFactory;
import java.util.*;

@SpringBootApplication
@EnableJms
public class Application {
    @Bean
    public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
                                                    DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        // Это обеспечивает все настройки загрузки по умолчанию для этой фабрики, включая конвертер сообщений
        configurer.configure(factory, connectionFactory);
        // Вы все равно можете переопределить некоторые из настроек по умолчанию Boot, если это необходимо.
        return factory;
    }

    @Bean //Сериализация содержимого сообщения в json с использованием TextMessage
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
        System.out.println("Write 10 messages!!!");
        Message message = createMessage();
        jmsTemplate.convertAndSend("message", message);
    }

    private static Message createMessage() {
        Scanner scanner = new Scanner(System.in);
        Message message = new Message();
        List<String> messageList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            messageList.add(scanner.nextLine());
        }
        Comparator<String> stringComparator = new StringLengthSort();
        Collections.sort(messageList, stringComparator);
        message.setMessageList(messageList);
        return message;
    }


}
