package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;

@Mapper
public interface FileMapper {
	@Select("SELECT * FROM FILES WHERE fileName = #{fileName} AND userId = #{userId}")
	Files getFile(String fileName, Integer userId);

	@Select("SELECT * FROM FILES WHERE fileId = #{id}")
	Files getFileById(Integer id);

	@Select("SELECT * FROM FILES")
	List<Files> getAllFiles();

	@Insert("INSERT INTO FILES (fileName, contentType, fileSize, userId, filedata) "
			+ "VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{filedata})")
	@Options(useGeneratedKeys = true, keyProperty = "fileId")
	int addFile(Files file);

	@Delete("DELETE FROM FILES WHERE fileId = #{id}")
	Integer delete(Integer id);

}
