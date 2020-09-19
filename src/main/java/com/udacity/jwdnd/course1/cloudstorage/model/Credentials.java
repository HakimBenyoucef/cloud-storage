package com.udacity.jwdnd.course1.cloudstorage.model;

import org.springframework.beans.factory.annotation.Autowired;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;

public class Credentials {
	private Integer credentialId;
	private String url;
	private String username;
	private String key;
	private String password;
	private Integer userId;

	@Autowired
	CredentialService credentialService;

	public Credentials(Integer credentialiId, String url, String username, String key, String password,
			Integer userId) {

		this.credentialId = credentialiId;
		this.url = url;
		this.username = username;
		this.key = key;
		this.password = password;
		this.userId = userId;
	}

	public Integer getCredentialId() {
		return credentialId;
	}

	public void setCredentialId(Integer credentialiId) {
		this.credentialId = credentialiId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}


}
