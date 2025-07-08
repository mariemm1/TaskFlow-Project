package org.example.taskflow.Services;

import org.example.taskflow.Models.*;
import org.example.taskflow.Models.Enum.Statut;
import org.example.taskflow.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TacheService {

    @Autowired
    private TacheRepository tacheRepo;

    @Autowired
    private ProjetRepository projetRepo;

    @Autowired
    private MembreRepository membreRepo;

    // Create tache (statut is EN_ATTENTE by default)
    public Tache create(Tache t) {
        t.setStatut(Statut.EN_ATTENTE);
        return tacheRepo.save(t);
    }

    // Get all taches
    public List<Tache> getAll() {
        return tacheRepo.findAll();
    }

    // Get one
    public Tache getById(Long id) {
        return tacheRepo.findById(id).orElse(null);
    }

    // Update (can change name, desc, dates)
    public Tache update(Long id, Tache t) {
        Tache existing = getById(id);
        if (existing != null) {
            existing.setNom(t.getNom());
            existing.setDescription(t.getDescription());
            existing.setDateDebut(t.getDateDebut());
            existing.setDateFin(t.getDateFin());
            existing.setSkills(t.getSkills());
            return tacheRepo.save(existing);
        }
        return null;
    }

    // Delete
    public boolean delete(Long id) {
        Tache t = getById(id);
        if (t != null && t.getStatut() == Statut.EN_ATTENTE) {
            tacheRepo.deleteById(id);
            return true;
        }
        return false;
    }


    // Assign membre to task and set to EN_COURS
    public Tache assignMembre(Long tacheId, Long membreId) {
        Tache t = getById(tacheId);
        Membre m = membreRepo.findById(membreId).orElse(null);

        if (t == null || m == null) return null;

        // 1. Check if member already has a task in progress
        List<Tache> enCoursTasks = tacheRepo.findByMembreAndStatut(m, Statut.EN_COURS);
        if (!enCoursTasks.isEmpty()) {
            throw new IllegalStateException("This member is already assigned to a task in progress.");
        }

        // 2. Parse and normalize skills and competences
        List<String> requiredSkills = List.of(t.getSkills().split(","))
                .stream()
                .map(String::trim)
                .map(String::toLowerCase)
                .toList();

        List<String> memberCompetences = m.getCompetences()
                .stream()
                .map(String::trim)
                .map(String::toLowerCase)
                .toList();

        // 3. Check if all required skills are in the member's competences
        for (String skill : requiredSkills) {
            if (!memberCompetences.contains(skill)) {
                throw new IllegalStateException("Member's competences do not match all required skills.");
            }
        }

        // 4. Assign and update
        t.setMembre(m);
        t.setStatut(Statut.EN_COURS);
        return tacheRepo.save(t);
    }


    // Mark tache as TERMINÉE and check project
    public Tache markAsTerminee(Long id) {
        Tache t = getById(id);
        if (t != null) {
            t.setStatut(Statut.TERMINE);
            t = tacheRepo.save(t);

            Projet p = t.getProjet();
            if (p != null) {
                boolean allDone = p.getTaches().stream().allMatch(tache -> tache.getStatut() == Statut.TERMINE);
                if (allDone) {
                    p.setStatut(Statut.TERMINE);
                    projetRepo.save(p);
                }
            }

            return t;
        }
        return null;
    }
}
