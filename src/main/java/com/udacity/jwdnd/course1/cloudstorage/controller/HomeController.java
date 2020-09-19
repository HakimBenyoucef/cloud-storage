package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("/home")
public class HomeController {

	@Autowired
	FileService fileService;
	
	@Autowired
	NoteService noteService;

	@Autowired
	UserService userService;
	
	@Autowired
	CredentialService credentialService;

	@GetMapping
	public String getHome(Model model, Authentication auth) {
		try {

			String username = auth.getName();
			int userId = userService.getUser(username).getUserId();
			
			List<Files> files = fileService.getAllFiles(userId);
			model.addAttribute("files", files);
			
			List<Notes> notes = noteService.getAllNotes(userId);
			model.addAttribute("notes", notes);
			
			List<Credentials> credentials = credentialService.getAllCredentials(userId);
			model.addAttribute("credentials", credentials);
			
			model.addAttribute("credentialService",credentialService);

			return "home";
		} catch (Exception e) {
			return "login";
		}
	}

}
