package com.sparkans.banqi.user;

public class InviteObject implements Comparable{
	
	private String sentUser;
	private String receivedUser;
	private String status;

	public InviteObject(String sentUser,String receivedUser,String status){
		this.sentUser = sentUser;
		this.receivedUser = receivedUser;
		this.status = status;
	}
	public InviteObject(){}

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

	@Override
	public int compareTo(Object o) {

		InviteObject inv = (InviteObject) o;
		if(inv.getReceivedUser().equals(this.receivedUser) && inv.getSentUser().equals(this.sentUser)){
			return 0;
		}
		else return 1;
	}
}
