package org.example.taskflow.RestControllers;



import org.example.taskflow.Models.Membre;
import org.example.taskflow.Services.MembreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/membres")
@CrossOrigin(origins = "http://localhost:4200")
public class MembreController {

    @Autowired
    private MembreService membreService;

    @PostMapping("/add")
    public ResponseEntity<Membre> createMembre(@RequestBody Membre membre) {
        try {
            Membre saved = membreService.createMembre(membre);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Membre>> getAllMembres() {
        try {
            List<Membre> list = membreService.getAllMembres();
            if (list.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Membre> getMembreById(@PathVariable Long id) {
        Membre membre = membreService.getMembreById(id);
        if (membre != null) {
            return new ResponseEntity<>(membre, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Membre> updateMembre(@PathVariable Long id, @RequestBody Membre membre) {
        Membre updated = membreService.updateMembre(id, membre);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteMembre(@PathVariable Long id) {
        try {
            membreService.deleteMembre(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
