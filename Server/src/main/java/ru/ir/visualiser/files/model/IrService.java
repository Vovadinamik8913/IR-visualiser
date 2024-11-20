package ru.ir.visualiser.files.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IrService {

    @Autowired
    private IrRepository irRepository;

    public void create(Ir ir) {
        irRepository.save(ir);
    }

    public Ir getById(Long id) {
        return irRepository.findById(id).orElse(null);
    }

    public void update(Ir ir) {
        irRepository.save(ir);
    }

    public void deleteById(long id) {
        irRepository.deleteById(id);
    }
}