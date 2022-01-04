package com.mediscreen.note.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Note {

    @Id
    private String id;

    @NotNull
    private Integer ownerId;

    @NotBlank
    private String date;

    @NotBlank
    private String observation;
}
