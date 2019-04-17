package com.msi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.msi.data.Notification;
import com.msi.data.repository.NotificationRepository;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

@EnableReactiveMongoRepositories
@SpringBootApplication
public class Application {
	public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, InterruptedException {
		ConfigurableApplicationContext application = SpringApplication.run(Application.class, args);
		final MongoOperations mongoOperations = application.getBean(MongoOperations.class);
		final NotificationRepository notificationRepository = application.getBean(NotificationRepository.class);	
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://guest:guest@localhost");
        factory.setConnectionTimeout(300000);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("my-queue", true, false, false, null);
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume("my-queue", false, consumer);
        
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			if (delivery != null) {
                try {
                    String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                    System.out.println("Message consumed: " + delivery.getBody());
                    Gson gson = new Gson();
                    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
           		    String json = ow.writeValueAsString(message);
                    Notification notification= gson.fromJson(message , Notification.class);                
                    notificationRepository.save(notification).subscribe();
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);                
                } catch (Exception e) {
                    channel.basicReject(delivery.getEnvelope().getDeliveryTag(), true);
                }
            }
        }
	}
}
	
	