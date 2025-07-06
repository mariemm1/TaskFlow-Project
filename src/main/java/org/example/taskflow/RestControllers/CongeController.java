package org.example.taskflow.RestControllers;


import org.example.taskflow.Models.Conge;
import org.example.taskflow.Services.CongeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conges")
@CrossOrigin(origins = "http://localhost:4200")
public class CongeController {

    @Autowired
    private CongeService congeService;

    @PostMapping("/addConge")
    public ResponseEntity<Conge> addConge(@RequestBody Conge conge) {
        return new ResponseEntity<>(congeService.createConge(conge), HttpStatus.CREATED);
    }

    @GetMapping("/allConge")
    public ResponseEntity<List<Conge>> getAllConges() {
        return new ResponseEntity<>(congeService.getAllConges(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conge> getCongeById(@PathVariable Long id) {
        Conge conge = congeService.getCongeById(id);
        return conge != null ? ResponseEntity.ok(conge) : ResponseEntity.notFound().build();
    }

    @PutMapping("/updateConge/{id}")
    public ResponseEntity<Conge> updateConge(@PathVariable Long id, @RequestBody Conge conge) {
        Conge updated = congeService.updateConge(id, conge);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deleteConge/{id}")
    public ResponseEntity<String> deleteConge(@PathVariable Long id) {
        congeService.deleteConge(id);
        return ResponseEntity.ok("Conge deleted successfully");
    }
}

