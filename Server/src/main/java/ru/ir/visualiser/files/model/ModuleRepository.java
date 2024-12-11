package ru.ir.visualiser.files.model;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ir.visualiser.parser.ModuleIR;

public interface ModuleRepository extends JpaRepository<ModuleIR, Long> {
}
