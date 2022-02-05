package com.enatcpromanager.com.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "user_accounts")
public class UserAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_accounts_generator")
	@SequenceGenerator(name = "user_accounts_generator", sequenceName = "user_accounts_seq", allocationSize = 1)
	@Column(name = "user_id")
	private long userId;
	
	@Column(name = "username")
	private String userName;
	
	private String password;
	
	private boolean enabled = true;

	public UserAccount() {
		super();
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	

}
