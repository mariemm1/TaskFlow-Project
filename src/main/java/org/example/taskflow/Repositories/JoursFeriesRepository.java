package org.example.taskflow.Repositories;

import org.example.taskflow.Models.JoursFeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoursFeriesRepository extends JpaRepository<JoursFeries,Long> {
}
