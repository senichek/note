package com.mediscreen.note.services;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.mediscreen.note.models.Note;
import com.mediscreen.note.repos.NoteRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepo noteRepo;

    @Autowired
    private Validator validator;

    @Override
    public Note create(Note note, Integer ownerID) throws Exception {
        note.setOwnerId(ownerID);
        note.setDate(LocalDate.now().toString());
        Note created;
        Map<String, String> validationErrors = validate(note);
        if (validationErrors.size() > 0) {
            throw new Exception(validationErrors.toString());
        } else {
            created = noteRepo.save(note);
            log.info("Created {}", created);
            return created;
        }
    }

    // If we "create" a Note with ID that is present in DB, the Note will be
    // updated, the duplicate will not be created, this is why we can use
    // <noteService.create> for updating the Notes.
    @Override
    public Note update(Note note) throws Exception {
        Note updated;
        Map<String, String> validationErrors = validate(note);
        if (validationErrors.size() > 0) {
            throw new Exception(validationErrors.toString());
        } else {
            updated = noteRepo.save(note);
            log.info("Updated {}", updated);
            return updated;
        }
    }

    @Override
    public List<Note> getAll() {
        return noteRepo.findAll();
    }

    @Override
    public List<Note> getAllByOwnerId(Integer ownerID) throws Exception {
        return noteRepo.getByOwnerId(ownerID);
    }

    @Override
    public Note getById(String noteId) throws Exception {
        Optional<Note> findById = noteRepo.findById(noteId);
        if (findById.isPresent() == false) {
            throw new Exception((String.format("Entity with id %s does not exist.", noteId)));
        } else {
            return findById.get();
        }
    }

    @Override
    public Note deleteById(String noteId) throws Exception {
        Optional<Note> findById = noteRepo.findById(noteId);
        if (findById.isPresent() == false) {
            throw new Exception((String.format("Entity with id %s does not exist.", noteId)));
        } else {
            noteRepo.deleteById(noteId);
            log.info("Deleted {}", findById.get());
            return findById.get();
        }
    }

    @Override
    public Map<String, String> validate(Note note) {
        Set<ConstraintViolation<Note>> validationErrors = validator.validate(note);
        Map<String, String> errorMessages = new HashMap<>();
        /*
         * If there are validation errors we extract them and put them into the Map
         * where the Key is the name of the property and the Value is the error message.
         */
        if (validationErrors.size() > 0) {
            Iterator<ConstraintViolation<Note>> iterator = validationErrors.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<Note> error = iterator.next();
                errorMessages.put(error.getPropertyPath().toString(), error.getMessage());
            }
        }
        return errorMessages;
    }
}
