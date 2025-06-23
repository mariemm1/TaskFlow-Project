package org.example.taskflow.Services;


import org.example.taskflow.Models.Membre;
import org.example.taskflow.Repositories.MembreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembreService {

    @Autowired
    private MembreRepository membreRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Membre createMembre(Membre membre) {
        membre.setMotDePasse(passwordEncoder.encode(membre.getMotDePasse()));
        return membreRepository.save(membre);
    }

    public List<Membre> getAllMembres() {
        return membreRepository.findAll();
    }

    public Membre getMembreById(Long id) {
        return membreRepository.findById(id).orElse(null);
    }

    public Membre updateMembre(Long id, Membre updatedMembre) {
        Membre existing = membreRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setNom(updatedMembre.getNom());
            existing.setPrenom(updatedMembre.getPrenom());
            existing.setEmail(updatedMembre.getEmail());

            if (!updatedMembre.getMotDePasse().equals(existing.getMotDePasse())) {
                existing.setMotDePasse(passwordEncoder.encode(updatedMembre.getMotDePasse()));
            }

            existing.setCompetences(updatedMembre.getCompetences());
            existing.setPostMembre(updatedMembre.getPostMembre());
            existing.setEquipe(updatedMembre.getEquipe());

            return membreRepository.save(existing);
        }
        return null;
    }

    public void deleteMembre(Long id) {
        membreRepository.deleteById(id);
    }
}
