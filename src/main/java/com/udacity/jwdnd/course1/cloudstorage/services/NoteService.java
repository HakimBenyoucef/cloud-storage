package com.udacity.jwdnd.course1.cloudstorage.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;

@Service
public class NoteService {

	@Autowired
	NoteMapper noteMapper;

	public int createNote(Notes note, int userId) throws IOException {
		Notes newNote = new Notes(null, note.getNoteTitle(), note.getNoteDescription(), userId);

		return noteMapper.addNote(newNote);
	}

	public List<Notes> getAllNotes(int userId) {
		return noteMapper.getAllNotes(userId);
	}

	public Notes getNoteById(Integer noteId) {
		return noteMapper.getNoteById(noteId);
	}

	public int delete(Integer noteId) {
		return noteMapper.delete(noteId);
	}

	public int update(Notes note) {
		return noteMapper.update(note);
	}

}
