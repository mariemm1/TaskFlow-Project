package org.example.taskflow.RestControllers;

import org.example.taskflow.Models.*;
import org.example.taskflow.Services.*;
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

    @PostMapping("/addTache")
    public ResponseEntity<Tache> add(@RequestBody Tache t) {
        return new ResponseEntity<>(service.create(t), HttpStatus.CREATED);
    }

    @GetMapping("/allTache")
    public ResponseEntity<List<Tache>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tache> get(@PathVariable Long id) {
        Tache t = service.getById(id);
        return t != null ? ResponseEntity.ok(t) : ResponseEntity.notFound().build();
    }

    @PutMapping("/updateTache/{id}")
    public ResponseEntity<Tache> update(@PathVariable Long id, @RequestBody Tache t) {
        Tache updated = service.update(id, t);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deleteTache/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Deleted");
    }
}
