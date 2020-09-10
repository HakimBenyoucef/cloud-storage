package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

		String username = auth.getName();
		Users user = userService.getUser(username);
		if (newNote.getNoteId() == null) {
			noteService.createNote(newNote, user.getUserId());
		} else {
			noteService.update(newNote);
		}
		model.addAttribute("uploadSuccess", true);

		return "/result";

	}

	@GetMapping(value = "/delete-note/{id}")
	public String deleteNote(@PathVariable("id") Integer id, Model model) {
		int result = noteService.delete(id);

		String uploadError = null;
		if (result < 0) {
			uploadError = "There was an error deleting your note. Please try again.";
			model.addAttribute("uploadError", uploadError);
		} else {
			model.addAttribute("uploadSuccess", true);
		}
		return "/result";
	}

}
