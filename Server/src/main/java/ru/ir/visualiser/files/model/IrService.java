package ru.ir.visualiser.files.model;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IrService {

    private  final IrRepository irRepository;

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