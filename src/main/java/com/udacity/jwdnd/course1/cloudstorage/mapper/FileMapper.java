package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;

@Mapper
public interface FileMapper {
	@Select("SELECT * FROM FILES WHERE fileName = #{fileName}")
	Files getFile(String fileName);
	
	@Select("SELECT * FROM FILES")
	List<Files> getAllFiles();

	@Insert("INSERT INTO FILES (fileName, contentType, fileSize, userId, filedata) "
			+ "VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{filedata})")
	@Options(useGeneratedKeys = true, keyProperty = "fileId")
	int addFile(Files file);

}
