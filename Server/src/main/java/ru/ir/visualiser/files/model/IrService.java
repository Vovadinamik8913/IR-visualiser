package ru.ir.visualiser.files.model;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Map;
import ru.ir.visualiser.parser.ModuleIR;

@Service
@RequiredArgsConstructor
public class IrService {

    private  final IrRepository irRepository;

    /**
     * Temporary replacement, storing parsed modules in memory for now.
     */
    private final Map<Long, ModuleIR> modules;

    public void create(Ir ir, ModuleIR module) {
        irRepository.save(ir);
        modules.put(ir.getId(), module);
    }

    /**
     * Get ir by id
     *
     * @param id - id of ir
     * @return - ir
     */
    public Ir get(Long id) {
        return irRepository.findById(id).orElse(null);
    }

    /**
     * Get module by the id of ir
     *
     * @param id - id of module
     * @return - module
     */
    public ModuleIR getModule(Long id) {
        return modules.get(id);
    }

    public void update(Ir ir) {
        irRepository.save(ir);
    }

    public void deleteById(long id) {
        irRepository.deleteById(id);
    }
}