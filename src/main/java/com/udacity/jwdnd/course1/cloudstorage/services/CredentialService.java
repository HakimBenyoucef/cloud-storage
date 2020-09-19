package com.udacity.jwdnd.course1.cloudstorage.services;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;

@Service
public class CredentialService {

	@Autowired
	CredentialMapper credentialMapper;

	@Autowired
	EncryptionService encryptionService;

	public int createCredential(Credentials credential, int userId) throws IOException {
		Credentials newCredential = new Credentials(null, credential.getUrl(), credential.getUsername(),
				credential.getKey(), credential.getPassword(), userId);

		this.encryptPassword(newCredential);
		return credentialMapper.addCredential(newCredential);
	}

	public List<Credentials> getAllCredentials(int userId) {
		return credentialMapper.getAllCredentials(userId);
	}

	public Credentials getCredentialById(Integer noteId) {
		return credentialMapper.getCredentialById(noteId);
	}
	
	public int delete(Integer credentialId) {
		return credentialMapper.delete(credentialId);
	}
	
	public int update(Credentials credential) {
		this.encryptPassword(credential);
		return credentialMapper.update(credential);
	}

	private void encryptPassword(Credentials credential) {
		SecureRandom random = new SecureRandom();
		byte[] keyArray = new byte[16];
		random.nextBytes(keyArray);
		String key = Base64.getEncoder().encodeToString(keyArray);
		credential.setKey(key);
		credential.setPassword(encryptionService.encryptValue(credential.getPassword(), key));
	}

	public String decryptPassword(Credentials credential) {

		System.out.println("2 => "+credential);
		return encryptionService.decryptValue(credential.getPassword(), credential.getKey());
	}
}
