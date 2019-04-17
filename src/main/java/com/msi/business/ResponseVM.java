package com.msi.business;

public class ResponseVM {
    private String objectId;
    private String instanceId;
	public String getObjectId() {
		return objectId;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	@Override
	public String toString() {
		return "ResponseVM [objectId=" + objectId + ", instanceId=" + instanceId + "]";
	}
	public ResponseVM(String objectId, String instanceId) {
		super();
		this.objectId = objectId;
		this.instanceId = instanceId;
	}
	public ResponseVM() {
		super();
	}
    
}
