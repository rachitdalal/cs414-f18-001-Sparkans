package com.sparkans.banqi.user;

public class InviteObject {
	
	private String sentUser;
	private String receivedUser;
	private String status;
	
	public String getSentUser() {
		return sentUser;
	}
	public void setSentUser(String sentUser) {
		this.sentUser = sentUser;
	}
	public String getReceivedUser() {
		return receivedUser;
	}
	public void setReceivedUser(String receivedUser) {
		this.receivedUser = receivedUser;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
