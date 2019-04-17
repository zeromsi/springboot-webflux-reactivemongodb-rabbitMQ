package com.msi.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

import com.msi.data.repository.NotificationRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@Service
@Document
public class Notification {

	@Id
	private String id;
	private String objectId;
	private String instanceId;
	private Long functionId;
	private Long locationId;
	private String roleId;
	private String stateId;
	private String userId;

	public String getId() {
		return id;
	}

	public String getObjectId() {
		return objectId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public Long getFunctionId() {
		return functionId;
	}

	public Long getLocationId() {
		return locationId;
	}

	public String getRoleId() {
		return roleId;
	}

	public String getStateId() {
		return stateId;
	}

	public String getUserId() {
		return userId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Notification(String id, String objectId, String instanceId, Long functionId, Long locationId, String roleId,
			String stateId, String userId) {
		super();
		this.id = id;
		this.objectId = objectId;
		this.instanceId = instanceId;
		this.functionId = functionId;
		this.locationId = locationId;
		this.roleId = roleId;
		this.stateId = stateId;
		this.userId = userId;
	}

	public Notification() {
		super();
	}

	@Override
	public String toString() {
		return "Notification [id=" + id + ", objectId=" + objectId + ", instanceId=" + instanceId + ", functionId="
				+ functionId + ", locationId=" + locationId + ", roleId=" + roleId + ", stateId=" + stateId
				+ ", userId=" + userId + "]";
	}

//	@Autowired
//	@Transient
//	NotificationRepository repo;
//
//	public Flux<Notification> findAll() {
//		return repo.findAll();
//	}
//
//	public void save(Notification notification) {
//		try {
//			repo.save(notification);
//		} catch (Exception e) {
//
//		}
//	}
//	
//	public Flux<Notification> findByObjectIdAndUserIdAndRoleIdAndLocationIdAndFunctionId( String objectId,String userId, String roleId,Long locationId,Long functionId) {
//		return repo.findByObjectIdAndUserIdAndRoleIdAndLocationIdAndFunctionId(objectId,userId,  roleId, locationId, functionId);
//	}
//
//	public Mono<Notification> findById(String empId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
