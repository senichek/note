package com.mediscreen.note;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.note.controllers.NoteRestController;
import com.mediscreen.note.models.Note;
import com.mediscreen.note.services.NoteService;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(controllers = NoteRestController.class)
public class NoteRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getAllNotesByUserIdTest() throws Exception {
        mockMvc.perform(get("/rest/patHistory/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getNoteByIdTest() throws Exception {
        mockMvc.perform(get("/rest/patHistory/note/61d1b70cb1f500126871bc4e"))
                .andExpect(status().isOk());
    }

    @Test
    public void createNoteTest() throws JsonProcessingException, Exception {
        Note note = new Note();
        mockMvc.perform(post("/rest/patHistory/add/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(note)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void updateNoteTest() throws JsonProcessingException, Exception {
        Note note = new Note();
        mockMvc.perform(post("/rest/patHistory/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(note)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void deleteNoteTest() throws Exception {
        mockMvc.perform(get("/rest/patHistory/delete/61d1b70cb1f500126871bc4e"))
                .andExpect(status().isOk());
    }
}
