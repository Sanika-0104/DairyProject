package com.role.implementation.model;

import org.springframework.context.annotation.Bean;


public class Admin {
	private String username="admin@gmail.com";
	private String password="admin";
	
	public String getUsername() {
	    return username;
	}

	public void setUsername(String username) {
	    this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
