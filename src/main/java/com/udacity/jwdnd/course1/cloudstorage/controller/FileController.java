package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
public class FileController {

	@Autowired
	FileService fileService;

	@Autowired
	UserService userService;

	@PostMapping("/files")
	public String handleFileUpload(@RequestParam("fileUpload") MultipartFile fileUpload, Model model,
			Authentication auth) throws IOException {

		String uploadError = null;

		if (!fileService.isFileAvailable(fileUpload.getOriginalFilename())) {
			uploadError = "Indeed, The file name already exists.";
			System.out.println("2 => " + fileUpload.getOriginalFilename());
			model.addAttribute("uploadError", uploadError);
		}

		if (uploadError == null) {
			String username = auth.getName();
			Users user = userService.getUser(username);
			int rowsAdded = fileService.createFile(fileUpload, user.getUserId());
			if (rowsAdded < 0) {
				uploadError = "There was an error uploading your file. Please try again.";
				model.addAttribute("uploadError", uploadError);
			} else {
				model.addAttribute("uploadSuccess", true);
			}
		}
		return "/result";

	}
}
