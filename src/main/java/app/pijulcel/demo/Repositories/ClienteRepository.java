package app.pijulcel.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import app.pijulcel.demo.Models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    
}
