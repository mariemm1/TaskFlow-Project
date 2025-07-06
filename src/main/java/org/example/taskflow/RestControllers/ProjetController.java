package org.example.taskflow.RestControllers;

import org.example.taskflow.Models.Projet;
import org.example.taskflow.Services.ProjetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projets")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjetController {

    @Autowired
    private ProjetService service;

    // Create (can be without équipe)
    @PostMapping("/addProjet")
    public ResponseEntity<Projet> add(@RequestBody Projet p) {
        return new ResponseEntity<>(service.create(p), HttpStatus.CREATED);
    }

    // Get all
    @GetMapping("/allProjet")
    public ResponseEntity<List<Projet>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<Projet> get(@PathVariable Long id) {
        Projet p = service.getById(id);
        return p != null ? ResponseEntity.ok(p) : ResponseEntity.notFound().build();
    }

    // Update
    @PutMapping("/updateProjet/{id}")
    public ResponseEntity<Projet> update(@PathVariable Long id, @RequestBody Projet p) {
        Projet updated = service.update(id, p);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // Assign équipe and set statut to EN_COURS
    @PutMapping("/{projetId}/assignEquipe/{equipeId}")
    public ResponseEntity<Projet> assignEquipe(
            @PathVariable Long projetId,
            @PathVariable Long equipeId) {
        Projet updated = service.assignEquipe(projetId, equipeId);
        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Delete only if statut is EN_ATTENTE
    @DeleteMapping("/deleteProjet/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean deleted = service.delete(id);
        if (deleted) {
            return ResponseEntity.ok("Project Deleted");
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Cannot delete project: Statut must be EN_ATTENTE.");
        }
    }
}
