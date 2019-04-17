package com.msi.business.controller.implementation;

import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.msi.data.EmployeeEvent;
import com.msi.data.Notification;
import com.msi.data.repository.NotificationRepository;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;

@RestController
@RequestMapping("/notifications")
public class NotificationControllerImplementation {

	@Autowired
	NotificationRepository repo;

	@PostMapping(value = "/")
	public void save(@RequestBody Notification notification) throws NoSuchAlgorithmException, KeyManagementException,
			URISyntaxException, IOException, InterruptedException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUri("amqp://guest:guest@localhost");
		factory.setConnectionTimeout(300000);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare("my-queue", true, false, false, null);
		Gson gson = new Gson();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(notification);
		channel.basicPublish("", "my-queue", null, json.getBytes());

	}

	@GetMapping(value = "/live/object/{objectId}/user/{userId}/role/{roleId}/location/{locationId}/function/{functionId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Notification> getEvents(@PathVariable("objectId") String objectId,
			@PathVariable("userId") String userId, @PathVariable("roleId") String roleId,
			@PathVariable("locationId") Long locationId, @PathVariable("functionId") Long functionId) {
		return repo.findNotificationByObjectIdAndUserIdAndRoleIdAndLocationIdAndFunctionId(objectId, roleId, userId,
				locationId, functionId);
	}

}
