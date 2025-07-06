package org.example.taskflow.Services;

import org.example.taskflow.Models.*;
import org.example.taskflow.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetService {

    @Autowired
    private ProjetRepository repo;

    @Autowired
    private EquipeRepository equipeRepo;


    public Projet create(Projet p) {
        return repo.save(p);
    }

    public List<Projet> getAll() {
        return repo.findAll();
    }

    public Projet getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Projet update(Long id, Projet p) {
        Projet old = getById(id);
        if (old != null) {
            old.setNom(p.getNom());
            old.setDescription(p.getDescription());
            old.setDateDebut(p.getDateDebut());
            old.setDateFin(p.getDateFin());
            old.setStatut(p.getStatut());
            old.setEquipe(p.getEquipe());
            return repo.save(old);
        }
        return null;
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Projet assignEquipe(Long projetId, Long equipeId) {
        Projet projet = repo.findById(projetId).orElse(null);
        Equipe equipe = equipeRepo.findById(equipeId).orElse(null);

        if (projet != null && equipe != null) {
            projet.setEquipe(equipe);
            return repo.save(projet);
        }

        return null;
    }

}
