package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
public class CredentialController {

	@Autowired
	CredentialService credentialService;

	@Autowired
	UserService userService;

	@PostMapping("/credentials")
	public String handleAddCredentials(Credentials credential, Model model, Authentication auth) throws IOException {

		String username = auth.getName();
		Users user = userService.getUser(username);
		if (credential.getCredentialId() == null) {
			credentialService.createCredential(credential, user.getUserId());
		}
		else {
			credentialService.update(credential);
		}
		model.addAttribute("uploadSuccess", true);

		return "/result";
	}

	@GetMapping(value = "/delete-credential/{id}")
	public String deleteCredentials(@PathVariable("id") Integer id, Model model) {
		int result = credentialService.delete(id);

		String uploadError = null;
		if (result < 0) {
			uploadError = "There was an error deleting your credentials. Please try again.";
			model.addAttribute("uploadError", uploadError);
		} else {
			model.addAttribute("uploadSuccess", true);
		}
		return "/result";
	}

}
