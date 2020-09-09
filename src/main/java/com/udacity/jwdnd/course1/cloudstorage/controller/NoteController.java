package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
public class NoteController {

	@Autowired
	NoteService noteService;

	@Autowired
	UserService userService;

	@PostMapping("/notes")
	public String handleAddNote(Notes newNote, Model model, Authentication auth) throws IOException {

		String uploadError = null;

		if (!noteService.isNoteAvailable(newNote.getNoteTitle())) {
			uploadError = "The note title already exists.";
			model.addAttribute("uploadError", uploadError);
		}

		if (uploadError == null) {
			String username = auth.getName();
			Users user = userService.getUser(username);
			int rowsAdded = noteService.createNote(newNote, user.getUserId());
			if (rowsAdded < 0) {
				uploadError = "There was an error adding your note. Please try again.";
				model.addAttribute("uploadError", uploadError);
			} else {
				model.addAttribute("uploadSuccess", true);
			}
		}
		return "/result";

	}

}
