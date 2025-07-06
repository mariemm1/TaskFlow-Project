package org.example.taskflow.Services;

import org.example.taskflow.Models.Equipe;
import org.example.taskflow.Models.Projet;
import org.example.taskflow.Models.Enum.Statut;
import org.example.taskflow.Repositories.EquipeRepository;
import org.example.taskflow.Repositories.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetService {

    @Autowired
    private ProjetRepository projetRepo;

    @Autowired
    private EquipeRepository equipeRepo;

    // Create project (can be without équipe)
    public Projet create(Projet p) {
        p.setStatut(Statut.EN_ATTENTE); //  Always set to EN_ATTENTE when creating
        return projetRepo.save(p);
    }


    // Get all
    public List<Projet> getAll() {
        return projetRepo.findAll();
    }

    // Get by ID
    public Projet getById(Long id) {
        return projetRepo.findById(id).orElse(null);
    }

    // Update project
    public Projet update(Long id, Projet p) {
        Projet old = getById(id);
        if (old != null) {
            old.setNom(p.getNom());
            old.setDescription(p.getDescription());
            old.setDateDebut(p.getDateDebut());
            old.setDateFin(p.getDateFin());
            old.setStatut(p.getStatut()); // Optionally allow updating statut
            old.setEquipe(p.getEquipe()); // Can update équipe if assigned
            return projetRepo.save(old);
        }
        return null;
    }

    // Assign équipe & change statut to EN_COURS
    public Projet assignEquipe(Long projetId, Long equipeId) {
        Projet projet = getById(projetId);
        Equipe equipe = equipeRepo.findById(equipeId).orElse(null);

        if (projet != null && equipe != null) {
            projet.setEquipe(equipe);
            projet.setStatut(Statut.EN_COURS);
            return projetRepo.save(projet);
        }
        return null;
    }

    // Delete only if statut is EN_ATTENTE
    public boolean delete(Long id) {
        Projet p = getById(id);
        if (p != null && p.getStatut() == Statut.EN_ATTENTE) {
            projetRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
