package org.example.taskflow.Models;

import jakarta.persistence.Entity;
import org.example.taskflow.Models.Enum.Role;

import java.util.List;

@Entity
public class ChefEquipe extends User {
    public ChefEquipe() {
    }

    public ChefEquipe(Long id, String nom, String prenom, String email, String motDePasse, Role Role, List<Conge> conges) {
        super(id, nom, prenom, email, motDePasse, Role, conges);
    }

}
