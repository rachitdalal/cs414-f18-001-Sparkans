package com.sparkans.banqi.user;

import com.google.gson.annotations.Expose;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class UserBean{

	protected String email;
	@Expose
	protected String nickName;

	protected String password;
	protected String isActive_flag;
	protected Timestamp createdTS;
	protected String isLoggedIn_flag;
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
		return nickName;
	}

	public void setNickName(String nickname){
		this.nickName = nickname;
	}

	//getter and setter methods for Password.
	public String getPassword(){
		return password;
	}

	public void setPassword(String password){
		this.password = password;
	}

	//getter and setter methods for isActive_flag.
	public String isActive(){
		return isActive_flag;
	}

	public void setActive(String isActive_flag){
		this.isActive_flag = isActive_flag;
	}

	//getter and setter methods for CreatedDate.
	public Timestamp getCreateTS(){
		
		LocalDateTime now = LocalDateTime.now();
		Timestamp createdTS = Timestamp.valueOf(now);
		return createdTS;
	}

	public void setCreateTS(Timestamp createdTS) {
		this.createdTS = createdTS;
	}

	//getter and setter methods for isLoggedIn_flag.
	public String isLoggedIn(){
		return isLoggedIn_flag;
	}

	public void setLoggedIn(String isLoggedIn_flag) {
		this.isLoggedIn_flag = isLoggedIn_flag;
	}

	//getter and setter methods for lastLoggedInTS.
	public  Timestamp getLastLoggedInTS(){
		
		LocalDateTime now = LocalDateTime.now();
		Timestamp lastLoggedInTS = Timestamp.valueOf(now);
		return lastLoggedInTS;
	}

	public void setLastLoggedInTS(Timestamp lastLoggedInTS) {
		this.lastLoggedInTS = lastLoggedInTS;
	}
}
