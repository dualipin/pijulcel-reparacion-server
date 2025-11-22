package app.pijulcel.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.pijulcel.demo.DTOs.ApiResponse;
import app.pijulcel.demo.Models.Usuario;
import app.pijulcel.demo.Repositories.UsuarioRepository;
import app.pijulcel.demo.Services.Interfaces.IUsuario;

@Service
public class UsuarioService implements IUsuario {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public ApiResponse<Void> register(Usuario usuario) {
        
        // Validar que no existe un usuario con ese username
        if(this.usuarioRepository.existsByUsername(usuario.getUsername())){
            return new ApiResponse<>(false, "El username ingresado ya existe.", null);
        }
        // Si no existe entonces podemos proceder a guardarlo
        usuarioRepository.save(usuario);
        return new ApiResponse<>(true, "Usuario registrado con exito.", null);
    }
    
}
