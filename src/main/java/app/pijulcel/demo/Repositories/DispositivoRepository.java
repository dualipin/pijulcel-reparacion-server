package app.pijulcel.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import app.pijulcel.demo.Models.Dispositivo;

public interface DispositivoRepository extends JpaRepository<Dispositivo, Integer> {
    
}
