package com.example.SpringAPI.core.dto;

import com.example.SpringAPI.core.model.Note;

public class NoteResponse {
    public Long id;
    public String title;
    public String content;
    public Long userId;

    public static NoteResponse fromEntity(Note n) {
        NoteResponse r = new NoteResponse();
        r.id = n.getId();
        r.title = n.getTitle();
        r.content = n.getContent();
        r.userId = (n.getUser() != null) ? n.getUser().getId() : null;
        return r;
    }
}
