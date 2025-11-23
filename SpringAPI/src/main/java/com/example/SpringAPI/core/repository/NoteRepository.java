package com.example.SpringAPI.core.repository;

import com.example.SpringAPI.core.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {}
