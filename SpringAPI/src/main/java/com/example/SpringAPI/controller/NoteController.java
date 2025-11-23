package com.example.SpringAPI.controller;

import com.example.SpringAPI.core.dto.NoteRequest;
import com.example.SpringAPI.core.dto.NoteResponse;
import com.example.SpringAPI.core.model.Note;
import com.example.SpringAPI.core.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService service;

    public NoteController(NoteService service) {
        this.service = service;
    }

    @GetMapping
    public List<NoteResponse> getAll() {
        return service.getAll().stream()
                .map(NoteResponse::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public NoteResponse getOne(@PathVariable Long id) {
        return NoteResponse.fromEntity(service.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NoteResponse create(@RequestBody NoteRequest req) {
        Note created = service.create(req);
        return NoteResponse.fromEntity(created);
    }

    @PutMapping("/{id}")
    public NoteResponse update(@PathVariable Long id, @RequestBody NoteRequest req) {
        Note updated = service.update(id, req);
        return NoteResponse.fromEntity(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
