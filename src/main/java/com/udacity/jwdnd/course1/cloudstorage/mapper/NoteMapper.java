package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
	
	@Select("SELECT * FROM NOTES WHERE noteId = #{id}")
	Notes getNoteById(Integer id);

    @Delete("DELETE FROM NOTES WHERE noteId = #{id}")
    Integer delete(Integer id);

    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteId}")
    Integer update(Notes note);
}
