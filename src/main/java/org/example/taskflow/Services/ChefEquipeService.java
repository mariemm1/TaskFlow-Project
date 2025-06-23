package org.example.taskflow.Services;

import org.example.taskflow.Models.ChefEquipe;
import org.example.taskflow.Repositories.ChefEquipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChefEquipeService {

    @Autowired
    private ChefEquipeRepository chefEquipeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ChefEquipe createChefEquipe(ChefEquipe chefEquipe) {
        chefEquipe.setMotDePasse(passwordEncoder.encode(chefEquipe.getMotDePasse()));
        return chefEquipeRepository.save(chefEquipe);
    }

    public List<ChefEquipe> getAllChefs() {
        return chefEquipeRepository.findAll();
    }

    public ChefEquipe getChefById(Long id) {
        return chefEquipeRepository.findById(id).orElse(null);
    }

    public ChefEquipe updateChefEquipe(Long id, ChefEquipe updatedChef) {
        ChefEquipe existing = chefEquipeRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setNom(updatedChef.getNom());
            existing.setPrenom(updatedChef.getPrenom());
            existing.setEmail(updatedChef.getEmail());

            if (!updatedChef.getMotDePasse().equals(existing.getMotDePasse())) {
                existing.setMotDePasse(passwordEncoder.encode(updatedChef.getMotDePasse()));
            }

            return chefEquipeRepository.save(existing);
        }
        return null;
    }

    public void deleteChefEquipe(Long id) {
        chefEquipeRepository.deleteById(id);
    }
}
