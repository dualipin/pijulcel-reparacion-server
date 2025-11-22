package app.pijulcel.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import app.pijulcel.demo.Models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    Boolean existsByUsername(String username);
    Boolean existsByUsernameAndPassword(String username, String password);

}
