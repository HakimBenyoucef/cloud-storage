package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
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

		String username = auth.getName();
		Users user = userService.getUser(username);

		try {
			String uploadError = null;

			if (fileUpload.getOriginalFilename().isBlank()) {
				return "home";
			}

			if (!fileService.isFileAvailable(fileUpload.getOriginalFilename(), user.getUserId())) {
				uploadError = "The file name already exists.";
				model.addAttribute("uploadError", uploadError);
			}

			if (uploadError == null) {
				int rowsAdded = fileService.createFile(fileUpload, user.getUserId());
				if (rowsAdded < 0) {
					uploadError = "There was an error uploading your file. Please try again.";
					model.addAttribute("uploadError", uploadError);
				} else {
					model.addAttribute("uploadSuccess", true);
				}
			}
			return "/result";
		} catch (Exception e) {
			return "/login";
		}

	}

	@GetMapping("/download-file/{id}")
	public ResponseEntity<Resource> downloadFile(@PathVariable("id") Integer id) {

		Files file = fileService.getFileById(id);

		if (file != null) {
			HttpHeaders headers = new HttpHeaders();

			headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=", file.getFileName()));
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");

			ByteArrayResource resource = new ByteArrayResource(file.getFiledata());

			return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType(file.getContentType()))
					.contentLength(file.getFileSize()).body(resource);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/delete-file/{id}")
	public String deleteFile(@PathVariable("id") Integer id, Model model) {
		int result = fileService.deleteFile(id);

		String uploadError = null;
		if (result < 0) {
			uploadError = "There was an error uploading your file. Please try again.";
			model.addAttribute("uploadError", uploadError);
		} else {
			model.addAttribute("uploadSuccess", true);
		}
		return "/result";
	}
}
