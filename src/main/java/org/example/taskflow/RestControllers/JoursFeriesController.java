package org.example.taskflow.RestControllers;

import org.example.taskflow.Models.*;
import org.example.taskflow.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jours-feries")
@CrossOrigin(origins = "http://localhost:4200")
public class JoursFeriesController {

    @Autowired
    private JoursFeriesService service;

    @PostMapping("/addJoursFeries")
    public ResponseEntity<JoursFeries> add(@RequestBody JoursFeries j) {
        return new ResponseEntity<>(service.create(j), HttpStatus.CREATED);
    }

    @GetMapping("/allJoursFeries")
    public ResponseEntity<List<JoursFeries>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JoursFeries> get(@PathVariable Long id) {
        JoursFeries j = service.getById(id);
        return j != null ? ResponseEntity.ok(j) : ResponseEntity.notFound().build();
    }

    @PutMapping("/updateJoursFeries/{id}")
    public ResponseEntity<JoursFeries> update(@PathVariable Long id, @RequestBody JoursFeries j) {
        JoursFeries updated = service.update(id, j);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deleteJoursFeries/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Jours Feries Deleted");
    }
}

