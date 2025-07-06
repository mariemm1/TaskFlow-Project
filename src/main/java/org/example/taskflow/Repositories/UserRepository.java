package org.example.taskflow.Repositories;

import org.example.taskflow.Models.ChefEquipe;
import org.example.taskflow.Models.Membre;
import org.example.taskflow.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    //User findByEmail(String email);
    Optional<User> findByEmail(String email);

    @Query("SELECT c FROM ChefEquipe c WHERE c.id = :id")
    ChefEquipe findChefById(@Param("id") Long id);

    @Query("SELECT m FROM Membre m WHERE m.id IN :ids")
    List<Membre> findMembresByIds(@Param("ids") List<Long> ids);
}
