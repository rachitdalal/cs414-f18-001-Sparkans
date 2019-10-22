package com.sparkans.banqi.user;

import java.sql.Timestamp;

public class UserBean{

	protected String email;
	protected String nickname;
	protected String password;
	protected Character isActive_flag;
	protected Timestamp createdTS;
	protected Character isLoggedIn_flag; 
	protected Timestamp lastLoggedInTS;

	public UserBean() {
	}

	//getter and setter methods for Email.
	public String getEmail(){
		return email;
	}

	public void setEmail(String email){
		this.email = email;
	}

	//getter and setter methods for Nickname.
	public String getNickname(){
		return nickname;
	}

	public void setNickname(String nickname){
		this.nickname = nickname;
	}

	//getter and setter methods for Password.
	public String getPassword(){
		return password;
	}

	public void setPassword(String password){
		this.password = password;
	}

	//getter and setter methods for isActive_flag.
	public Character isActive(){
		return isActive_flag;
	}

	public void setActive(Character isActive_flag){
		this.isActive_flag = isActive_flag;
	}

	//getter and setter methods for CreatedDate.
	public Timestamp getCreateDate(){
		return createdTS;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createdTS = createDate;
	}

	//getter and setter methods for isLoggedIn_flag.
	public Character isLoggedIn(){
		return isLoggedIn_flag;
	}

	public void setLoggedIn(Character isLoggedIn_flag) {
		this.isLoggedIn_flag = isLoggedIn_flag;
	}

	//getter and setter methods for lastLoggedInTS.
	public  Timestamp getLastLoggedInTS(){
		return lastLoggedInTS;
	}

	public void setLastLoggedInTS(Timestamp lastLoggedInTS) {
		this.lastLoggedInTS = lastLoggedInTS;
	}
}