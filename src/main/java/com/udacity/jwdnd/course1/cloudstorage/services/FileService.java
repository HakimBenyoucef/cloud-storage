package com.udacity.jwdnd.course1.cloudstorage.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;

@Service
public class FileService {

	@Autowired
	FileMapper fileMapper;

	public int createFile(MultipartFile fileUpload, int userId) throws IOException {
		Files file = new Files(null, fileUpload.getOriginalFilename(), fileUpload.getContentType(),
				fileUpload.getSize(), userId, fileUpload.getBytes());
		return fileMapper.addFile(file);
	}

	public boolean isFileAvailable(String fileName, Integer userId) {
		return fileMapper.getFile(fileName, userId) == null;
	}

	public Files getFile(String fileName, Integer userId) {
		return fileMapper.getFile(fileName, userId);
	}

	public Files getFileById(Integer fileId) {
		return fileMapper.getFileById(fileId);
	}

	public List<Files> getAllFiles() {
		return fileMapper.getAllFiles();
	}

	public int deleteFile(Integer fileId) {
		return fileMapper.delete(fileId);
	}
}
