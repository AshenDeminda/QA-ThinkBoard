package com.thinkboard.controller;

import com.thinkboard.model.Note;
import com.thinkboard.model.User;
import com.thinkboard.repository.NoteRepository;
import com.thinkboard.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteController(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Note> list(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        Long userId = user.getId();
        return noteRepository.findByUserId(userId);
    }

    @PostMapping
    public Note create(@RequestBody Note note, Authentication authentication) {
        note.setId(null);
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        note.setUserId(user.getId());
        return noteRepository.save(note);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> get(@PathVariable Long id, Authentication authentication) {
        Optional<Note> note = noteRepository.findById(id);
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        Long userId = user.getId();
        return note.filter(n -> n.getUserId().equals(userId))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> update(@PathVariable Long id, @RequestBody Note updated, Authentication authentication) {
        Optional<Note> existing = noteRepository.findById(id);
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        Long userId = user.getId();
        if (existing.isEmpty() || !existing.get().getUserId().equals(userId)) {
            return ResponseEntity.notFound().build();
        }
        Note note = existing.get();
        note.setTitle(updated.getTitle());
        note.setContent(updated.getContent());
        return ResponseEntity.ok(noteRepository.save(note));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Authentication authentication) {
        Optional<Note> existing = noteRepository.findById(id);
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        Long userId = user.getId();
        if (existing.isEmpty() || !existing.get().getUserId().equals(userId)) {
            return ResponseEntity.notFound().build();
        }
        noteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}


