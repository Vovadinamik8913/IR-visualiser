package ru.ir.visualiser.files.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ir.visualiser.parser.ModuleIR;

@Service
public class IrService {

    @Autowired
    private IrRepository irRepository;
    @Autowired
    private ModuleRepository moduleRepository;

    public void create(Ir ir, ModuleIR module) {
        ir.setModule(module);
        irRepository.save(ir);
        moduleRepository.save(module);
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