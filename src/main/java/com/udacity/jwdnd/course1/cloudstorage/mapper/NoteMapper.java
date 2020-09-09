package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;


@Mapper
public interface NoteMapper {
	@Select("SELECT * FROM NOTES WHERE noteTitle = #{noteTitle}")
	Notes getNote(String noteTitle);
	
	@Select("SELECT * FROM NOTES")
	List<Notes> getAllNotes();

	@Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId) "
			+ "VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
	@Options(useGeneratedKeys = true, keyProperty = "noteId")
	int addNote(Notes note);
}
