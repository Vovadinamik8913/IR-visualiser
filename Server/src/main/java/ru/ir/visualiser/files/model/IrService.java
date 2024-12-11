package ru.ir.visualiser.files.model;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ru.ir.visualiser.parser.ModuleIR;

@Service
@RequiredArgsConstructor
public class IrService {

    private  final IrRepository irRepository;
    private  final ModuleRepository moduleRepository;

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