package org.example.taskflow.Repositories;

import org.example.taskflow.Models.Conge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CongeRepository extends JpaRepository<Conge,Long> {
}
