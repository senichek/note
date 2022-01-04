package com.mediscreen.note.controllers;

import java.util.List;

import com.mediscreen.note.models.Note;
import com.mediscreen.note.services.NoteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest")
public class NoteRestController {

    @Autowired
    private NoteService noteService;

    @GetMapping(value = "/patHistory/{userId}")
    public ResponseEntity<List<Note>> getAllNotesByUserId(@PathVariable("userId") Integer id) throws Exception {
        return new ResponseEntity<>(noteService.getAllByOwnerId(id), HttpStatus.OK);
    }

    @GetMapping(value = "/patHistory/note/{noteId}")
    public ResponseEntity<Note> getNoteById(@PathVariable("noteId") String id) throws Exception {
        return new ResponseEntity<>(noteService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/patHistory/add/{userId}")
    public ResponseEntity<Note> createNote(@PathVariable("userId") Integer id, @RequestBody Note note) throws Exception {
        return new ResponseEntity<>(noteService.create(note, id), HttpStatus.OK);
    }

    @PostMapping(value = "/patHistory/update/{userId}")
    public ResponseEntity<Note> updateNote(@PathVariable("userId") Integer userId, @RequestBody Note note) throws Exception {
        // If we "create" a Note with ID that is present in DB, the Note will be updated,
        // the duplicate will not be created, this is why we can use <noteService.create>
        // for updating the Notes.
        return new ResponseEntity<>(noteService.update(note), HttpStatus.OK);
    }

    @GetMapping(value = "/patHistory/delete/{noteId}")
    public ResponseEntity<Note> deleteNote(@PathVariable("noteId") String noteId) throws Exception {
        return new ResponseEntity<>(noteService.deleteById(noteId), HttpStatus.OK);
    }
}