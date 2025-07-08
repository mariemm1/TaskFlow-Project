package org.example.taskflow.RestControllers;

import org.example.taskflow.Models.Tache;
import org.example.taskflow.Services.TacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taches")
@CrossOrigin(origins = "http://localhost:4200")
public class TacheController {

    @Autowired
    private TacheService service;

    // Create
    @PostMapping("/addTache")
    public ResponseEntity<Tache> create(@RequestBody Tache t) {
        return new ResponseEntity<>(service.create(t), HttpStatus.CREATED);
    }

    // Read all
    @GetMapping("/allTaches")
    public ResponseEntity<List<Tache>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // Read one
    @GetMapping("/{id}")
    public ResponseEntity<Tache> get(@PathVariable Long id) {
        Tache t = service.getById(id);
        return t != null ? ResponseEntity.ok(t) : ResponseEntity.notFound().build();
    }

    // Update
    @PutMapping("/updateTache/{id}")
    public ResponseEntity<Tache> update(@PathVariable Long id, @RequestBody Tache t) {
        Tache updated = service.update(id, t);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // Delete
    @DeleteMapping("/deleteTache/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean deleted = service.delete(id);
        if (deleted) {
            return ResponseEntity.ok("Tache deleted successfully.");
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Cannot delete task: only tasks with statut EN_ATTENTE can be deleted.");
        }
    }


    // Assign membre
    @PutMapping("/{tacheId}/assign/{membreId}")
    public ResponseEntity<Tache> assignMembre(
            @PathVariable Long tacheId,
            @PathVariable Long membreId) {
        Tache updated = service.assignMembre(tacheId, membreId);
        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Mark as terminee
    @PutMapping("/{id}/terminee")
    public ResponseEntity<Tache> terminerTache(@PathVariable Long id) {
        Tache updated = service.markAsTerminee(id);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }
}
