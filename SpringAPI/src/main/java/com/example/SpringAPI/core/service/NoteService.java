package com.example.SpringAPI.core.service;

import com.example.SpringAPI.core.dto.NoteRequest;
import com.example.SpringAPI.core.model.Note;
import com.example.SpringAPI.core.model.User;
import com.example.SpringAPI.core.repository.NoteRepository;
import com.example.SpringAPI.core.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepo;
    private final UserRepository userRepo;

    public NoteService(NoteRepository noteRepo, UserRepository userRepo) {
        this.noteRepo = noteRepo;
        this.userRepo = userRepo;
    }

    @Transactional(readOnly = true)
    public List<Note> getAll() {
        return noteRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Note getById(Long id) {
        return noteRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found: " + id));
    }

    @Transactional
    public Note create(NoteRequest req) {
        if (req.userId == null)
            throw new RuntimeException("userId is required");

        User user = userRepo.findById(req.userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + req.userId));

        Note n = new Note();
        n.setTitle(req.title);
        n.setContent(req.content);
        n.setUser(user);

        return noteRepo.save(n);
    }

    @Transactional
    public Note update(Long id, NoteRequest req) {
        Note n = getById(id);

        if (req.title != null) n.setTitle(req.title);
        if (req.content != null) n.setContent(req.content);

        if (req.userId != null) {
            User user = userRepo.findById(req.userId)
                    .orElseThrow(() -> new RuntimeException("User not found: " + req.userId));
            n.setUser(user);
        }

        return noteRepo.save(n);
    }

    @Transactional
    public void delete(Long id) {
        noteRepo.deleteById(id);
    }
}
