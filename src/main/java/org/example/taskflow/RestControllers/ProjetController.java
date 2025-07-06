package org.example.taskflow.RestControllers;

import org.example.taskflow.Models.*;
import org.example.taskflow.Services.*;
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

    @PostMapping("/addProjet")
    public ResponseEntity<Projet> add(@RequestBody Projet p) {
        return new ResponseEntity<>(service.create(p), HttpStatus.CREATED);
    }

    @GetMapping("/allProjet")
    public ResponseEntity<List<Projet>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Projet> get(@PathVariable Long id) {
        Projet p = service.getById(id);
        return p != null ? ResponseEntity.ok(p) : ResponseEntity.notFound().build();
    }

    @PutMapping("/updateProjet/{id}")
    public ResponseEntity<Projet> update(@PathVariable Long id, @RequestBody Projet p) {
        Projet updated = service.update(id, p);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deleteProjet/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Deleted");
    }
}
