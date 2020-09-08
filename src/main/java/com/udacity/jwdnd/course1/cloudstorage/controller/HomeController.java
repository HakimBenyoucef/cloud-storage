package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("/home")
public class HomeController {

	@Autowired
	FileService fileService;

	@Autowired
	UserService userService;

	@GetMapping
	public String getHome(Model model) {

		List<Files> files = fileService.getAllFiles();
		model.addAttribute("files", files);

		return "home";
	}

}
