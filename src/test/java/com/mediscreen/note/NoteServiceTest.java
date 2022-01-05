package com.mediscreen.note;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import com.mediscreen.note.models.Note;
import com.mediscreen.note.services.NoteService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;


@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD) // Tests change the collection, this is why we have to reset the collection
public class NoteServiceTest {

    @Autowired
    private NoteService noteService;

    @Test
    public void createTest() throws Exception {
        Note note = new Note();
        note.setDate(LocalDate.now().toString());
        note.setObservation("Test");
        Note created = noteService.create(note, 1);
        assertEquals(1, created.getOwnerId());
        assertEquals("Test", created.getObservation());
        assertEquals(LocalDate.now().toString(), created.getDate());
    }

    @Test
    public void createWithExceptionTest() {
        Note note = new Note();
        note.setDate(LocalDate.now().toString());
        note.setObservation("");
        Exception exception = assertThrows(Exception.class, () -> noteService.create(note, 1));
        assertTrue(exception.getMessage().contains("observation=must not be blank"));
    }

    @Test
    public void updateTest() throws Exception {
        // We know that the noteId "61d1ba0f72618f5a3c322bba" is present in DB.
        Note toUpdate = noteService.getById("61d1ba0f72618f5a3c322bba");
        toUpdate.setObservation("Test");
        toUpdate.setDate("1022-11-05");
        Note updated = noteService.update(toUpdate);
        assertEquals("Test", updated.getObservation());
        assertEquals("1022-11-05", updated.getDate());
    }

    @Test
    public void updateWithExceptionTest() throws Exception {
        // We know that the noteId "61d1ba0f72618f5a3c322bba" is present in DB.
        Note toUpdate = noteService.getById("61d1ba0f72618f5a3c322bba");
        toUpdate.setObservation("");
        Exception exception = assertThrows(Exception.class, () -> noteService.update(toUpdate));
        assertTrue(exception.getMessage().contains("observation=must not be blank"));
    }

    @Test
    public void getAllTest() {
        List<Note> notes = noteService.getAll();
        assertEquals(9, notes.size());

    }

    @Test
    public void getAllByOwnerIdTest() throws Exception {
        // User with ID=4 has 4 notes.
        List<Note> result = noteService.getAllByOwnerId(4);
        assertEquals(4, result.size());

        result.forEach(r -> {
            assertEquals(4, r.getOwnerId());
        });
    }

    @Test
    public void getByIdTest() throws Exception {
        // We know that the noteId "61d1b70cb1f500126871bc4e" is present in DB.
        Note result = noteService.getById("61d1b70cb1f500126871bc4e");
        assertEquals("61d1b70cb1f500126871bc4e", result.getId());
    }

    @Test
    public void getByIdWithExceptionTest() throws Exception {
        // We know that the noteId "555" is NOT present in DB.
        Exception exception = assertThrows(Exception.class, () -> noteService.getById("555"));
        assertTrue(exception.getMessage().contains("Entity with id 555 does not exist."));
    }

    @Test
    public void deleteByIdTest() throws Exception {
        // We know that the noteId "61d1ba0f72618f5a3c322bba" is present in DB.
        noteService.deleteById("61d1ba0f72618f5a3c322bba");
        // If the Note is deleted successfully, the method <getById> will throw Exception.
        Exception exception = assertThrows(Exception.class, () -> noteService.getById("61d1ba0f72618f5a3c322bba"));
        assertTrue(exception.getMessage().contains("Entity with id 61d1ba0f72618f5a3c322bba does not exist."));
    }

    @Test
    public void deleteByIdWithExceptionTest() throws Exception {
        // We know that the noteId "555" is NOT present in DB.
        Exception exception = assertThrows(Exception.class, () -> noteService.deleteById("555"));
        assertTrue(exception.getMessage().contains("Entity with id 555 does not exist."));
    }
}
