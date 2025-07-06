package org.example.taskflow.Services;

import org.example.taskflow.Models.Conge;
import org.example.taskflow.Repositories.CongeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CongeService {

    @Autowired
    private CongeRepository congeRepository;

    public Conge createConge(Conge conge) {
        return congeRepository.save(conge);
    }

    public List<Conge> getAllConges() {
        return congeRepository.findAll();
    }

    public Conge getCongeById(Long id) {
        return congeRepository.findById(id).orElse(null);
    }

    public Conge updateConge(Long id, Conge conge) {
        return congeRepository.findById(id)
                .map(existing -> {
                    existing.setStartDate(conge.getStartDate());
                    existing.setEndDate(conge.getEndDate());
                    existing.setTheCause(conge.getTheCause());
                    return congeRepository.save(existing);
                }).orElse(null);
    }

    public void deleteConge(Long id) {
        congeRepository.deleteById(id);
    }
}

