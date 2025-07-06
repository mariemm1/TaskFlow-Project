package org.example.taskflow.Services;

import org.example.taskflow.Models.*;
import org.example.taskflow.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JoursFeriesService {

    @Autowired
    private JoursFeriesRepository repo;

    public JoursFeries create(JoursFeries j) {
        return repo.save(j);
    }

    public List<JoursFeries> getAll() {
        return repo.findAll();
    }

    public JoursFeries getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public JoursFeries update(Long id, JoursFeries j) {
        JoursFeries old = getById(id);
        if (old != null) {
            old.setDate(j.getDate());
            old.setDescription(j.getDescription());
            return repo.save(old);
        }
        return null;
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
