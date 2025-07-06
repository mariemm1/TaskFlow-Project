package org.example.taskflow.RestControllers;

import org.example.taskflow.DTO.Request.EquipeAssignmentRequest;
import org.example.taskflow.Models.*;
import org.example.taskflow.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipes")
@CrossOrigin(origins = "http://localhost:4200")
public class EquipeController {

    @Autowired
    private EquipeService service;

    @PostMapping("/addEquipe")
    public ResponseEntity<Equipe> add(@RequestBody Equipe e) {
        return new ResponseEntity<>(service.createEquipe(e), HttpStatus.CREATED);
    }

    @GetMapping("/allEquipe")
    public ResponseEntity<List<Equipe>> getAll() {
        return new ResponseEntity<>(service.getAllEquipes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipe> get(@PathVariable Long id) {
        Equipe e = service.getEquipeById(id);
        return e != null ? ResponseEntity.ok(e) : ResponseEntity.notFound().build();
    }

    @PutMapping("/updateEquipe/{id}")
    public ResponseEntity<Equipe> update(@PathVariable Long id, @RequestBody Equipe e) {
        Equipe updated = service.updateEquipe(id, e);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deleteEquipe/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean deleted = service.deleteEquipe(id);
        if (deleted) {
            return ResponseEntity.ok("Equipe deleted successfully.");
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Cannot delete equipe: it has assigned members or a chef.");
        }
    }

    @PutMapping("/{id}/assign")
    public ResponseEntity<Equipe> assignMembersAndChef(
            @PathVariable Long id,
            @RequestBody EquipeAssignmentRequest request) {
        Equipe updatedEquipe = service.assignOrUpdateChefAndMembres(id, request.chefId, request.membreIds);
        return updatedEquipe != null
                ? ResponseEntity.ok(updatedEquipe)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{id}/remove-chef")
    public ResponseEntity<Equipe> removeChef(@PathVariable Long id) {
        Equipe equipe = service.removeChef(id);
        return equipe != null ? ResponseEntity.ok(equipe) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/remove-membre/{membreId}")
    public ResponseEntity<Equipe> removeMembre(@PathVariable Long id, @PathVariable Long membreId) {
        Equipe equipe = service.removeMembre(id, membreId);
        return equipe != null ? ResponseEntity.ok(equipe) : ResponseEntity.notFound().build();
    }
}