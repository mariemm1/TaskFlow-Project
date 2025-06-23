package org.example.taskflow.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;

    @OneToOne
    @JoinColumn(name = "chef_id", referencedColumnName = "id")
    private ChefEquipe chefDequipe;

    @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Membre> membres;

    public Equipe() {
    }

    public Equipe(Long id, String nom, String description, ChefEquipe chefDequipe, List<Membre> membres) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.chefDequipe = chefDequipe;
        this.membres = membres;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ChefEquipe getChefDequipe() {
        return chefDequipe;
    }

    public void setChefDequipe(ChefEquipe chefDequipe) {
        this.chefDequipe = chefDequipe;
    }

    public List<Membre> getMembres() {
        return membres;
    }

    public void setMembres(List<Membre> membres) {
        this.membres = membres;
    }
}
