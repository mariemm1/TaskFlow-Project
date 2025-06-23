package org.example.taskflow.RestControllers;



import org.example.taskflow.Models.ChefEquipe;
import org.example.taskflow.Services.ChefEquipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chefs")
@CrossOrigin(origins = "http://localhost:4200")
public class ChefEquipeController {

    @Autowired
    private ChefEquipeService chefEquipeService;

    @PostMapping("/add")
    public ResponseEntity<ChefEquipe> createChefEquipe(@RequestBody ChefEquipe chef) {
        try {
            ChefEquipe saved = chefEquipeService.createChefEquipe(chef);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ChefEquipe>> getAllChefs() {
        try {
            List<ChefEquipe> list = chefEquipeService.getAllChefs();
            if (list.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<ChefEquipe> getChefById(@PathVariable Long id) {
        ChefEquipe chef = chefEquipeService.getChefById(id);
        if (chef != null) {
            return new ResponseEntity<>(chef, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<ChefEquipe> updateChefEquipe(@PathVariable Long id, @RequestBody ChefEquipe chef) {
        ChefEquipe updated = chefEquipeService.updateChefEquipe(id, chef);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteChefEquipe(@PathVariable Long id) {
        try {
            chefEquipeService.deleteChefEquipe(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
