package org.example.taskflow.Repositories;

import org.example.taskflow.Models.ChefEquipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChefEquipeRepository extends JpaRepository<ChefEquipe,Long> {
}
