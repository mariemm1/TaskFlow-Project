package org.example.taskflow.Services;

import org.example.taskflow.Models.*;
import org.example.taskflow.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TacheService {

    @Autowired
    private TacheRepository repo;

    public Tache create(Tache t) {
        return repo.save(t);
    }

    public List<Tache> getAll() {
        return repo.findAll();
    }

    public Tache getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Tache update(Long id, Tache t) {
        Tache old = getById(id);
        if (old != null) {
            old.setNom(t.getNom());
            old.setDescription(t.getDescription());
            old.setDateDebut(t.getDateDebut());
            old.setDateFin(t.getDateFin());
            old.setStatut(t.getStatut());
            old.setSkills(t.getSkills());
            old.setProjet(t.getProjet());
            old.setMembre(t.getMembre());
            return repo.save(old);
        }
        return null;
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
