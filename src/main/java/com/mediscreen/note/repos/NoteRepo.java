package com.mediscreen.note.repos;

import java.util.List;

import com.mediscreen.note.models.Note;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepo extends MongoRepository<Note, String> {
    List<Note> getByOwnerId(Integer id);
}
