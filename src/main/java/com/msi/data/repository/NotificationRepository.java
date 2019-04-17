package com.msi.data.repository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.msi.data.Notification;
import reactor.core.publisher.Flux;

public interface NotificationRepository extends ReactiveCrudRepository<Notification, String> {
	@Tailable
	Flux<Notification> findNotificationByObjectIdAndUserIdAndRoleIdAndLocationIdAndFunctionId(String objectId, String userId,
			String roleId, Long locationId, Long functionId);
}
