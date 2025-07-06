package org.example.taskflow.Services;

import org.example.taskflow.Models.*;
import org.example.taskflow.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipeService {

    @Autowired
    private EquipeRepository equipeRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private MembreRepository membreRepo;

    public Equipe createEquipe(Equipe equipe) {
        return equipeRepo.save(equipe);
    }

    public List<Equipe> getAllEquipes() {
        return equipeRepo.findAll();
    }

    public Equipe getEquipeById(Long id) {
        return equipeRepo.findById(id).orElse(null);
    }

    public Equipe updateEquipe(Long id, Equipe equipe) {
        Equipe existing = equipeRepo.findById(id).orElse(null);
        if (existing != null) {
            existing.setNom(equipe.getNom());
            existing.setDescription(equipe.getDescription());
            existing.setChefDequipe(equipe.getChefDequipe());
            existing.setMembres(equipe.getMembres());
            return equipeRepo.save(existing);
        }
        return null;
    }

    //Delete an equipe only if it has no chef and no members

    public boolean deleteEquipe(Long id) {
        Equipe equipe = equipeRepo.findById(id).orElse(null);
        if (equipe == null) return false;

        boolean hasChef = equipe.getChefDequipe() != null;
        boolean hasMembers = equipe.getMembres() != null && !equipe.getMembres().isEmpty();

        if (!hasChef && !hasMembers) {
            equipeRepo.deleteById(id);
            return true;
        }

        return false; // not deleted
    }

    public Equipe assignOrUpdateChefAndMembres(Long equipeId, Long chefId, List<Long> membreIds) {
        Equipe equipe = equipeRepo.findById(equipeId).orElse(null);
        if (equipe == null) return null;

        // 👉 Assign or remove Chef
        ChefEquipe chef = (chefId != null) ? userRepo.findChefById(chefId) : null;
        equipe.setChefDequipe(chef);

        // 👉 Replace or clear membres
        if (membreIds != null) {
            List<Membre> membres = userRepo.findMembresByIds(membreIds);
            for (Membre m : membres) m.setEquipe(equipe);
            equipe.setMembres(membres);
        }

        return equipeRepo.save(equipe);
    }

    public Equipe removeChef(Long equipeId) {
        Equipe equipe = equipeRepo.findById(equipeId).orElse(null);
        if (equipe == null) return null;
        equipe.setChefDequipe(null);
        return equipeRepo.save(equipe);
    }

    public Equipe removeMembre(Long equipeId, Long membreId) {
        Equipe equipe = equipeRepo.findById(equipeId).orElse(null);
        if (equipe == null) return null;

        List<Membre> membres = equipe.getMembres();
        Membre membreToRemove = membres.stream()
                .filter(m -> m.getId().equals(membreId))
                .findFirst()
                .orElse(null);

        if (membreToRemove != null) {
            membres.remove(membreToRemove);
            membreToRemove.setEquipe(null); //  remove reference to equipe
            membreRepo.save(membreToRemove); // save change in member
            equipe.setMembres(membres);
            return equipeRepo.save(equipe); // save updated equipe
        }

        return null;
    }

}

