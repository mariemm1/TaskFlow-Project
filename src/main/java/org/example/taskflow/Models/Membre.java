package org.example.taskflow.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.example.taskflow.Models.Enum.PostMembre;
import org.example.taskflow.Models.Enum.Role;

import java.util.List;

@Entity
public class Membre extends User {

    @ElementCollection
    private List<String> competences;

    @Enumerated(EnumType.STRING)
    private PostMembre postMembre;

    @ManyToOne
    @JoinColumn(name = "equipe_id")
    @JsonBackReference
    private Equipe equipe;


    public Membre() {
    }

    public Membre(List<String> competences, PostMembre postMembre, Equipe equipe) {
        this.competences = competences;
        this.postMembre = postMembre;
        this.equipe = equipe;
    }

    public Membre(Long id, String nom, String prenom, String email, String motDePasse, Role Role, List<Conge> conges, List<String> competences, PostMembre postMembre, Equipe equipe) {
        super(id, nom, prenom, email, motDePasse, Role, conges);
        this.competences = competences;
        this.postMembre = postMembre;
        this.equipe = equipe;
    }

    public List<String> getCompetences() {
        return competences;
    }

    public void setCompetences(List<String> competences) {
        this.competences = competences;
    }

    public PostMembre getPostMembre() {
        return postMembre;
    }

    public void setPostMembre(PostMembre postMembre) {
        this.postMembre = postMembre;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }
}
