package com.mediscreen.note.services;

import java.util.List;
import java.util.Map;

import com.mediscreen.note.models.Note;

public interface NoteService {
    public Note create(Note note, Integer ownerID) throws Exception;
    public Map<String, String> validate(Note note);
    public List<Note> getAll();
    public List<Note> getAllByOwnerId(Integer ownerID)throws Exception;
    public Note getById(String noteId) throws Exception;
    public Note deleteById(String noteId) throws Exception;
}
