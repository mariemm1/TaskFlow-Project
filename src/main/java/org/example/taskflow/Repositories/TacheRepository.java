package org.example.taskflow.Repositories;

import org.example.taskflow.Models.Enum.Statut;
import org.example.taskflow.Models.Membre;
import org.example.taskflow.Models.Tache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TacheRepository extends JpaRepository<Tache,Long> {
    List<Tache> findByMembreAndStatut(Membre membre, Statut statut);

}
