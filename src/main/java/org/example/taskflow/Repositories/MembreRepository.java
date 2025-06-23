package org.example.taskflow.Repositories;

import org.example.taskflow.Models.Membre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembreRepository extends JpaRepository<Membre,Long> {
}
