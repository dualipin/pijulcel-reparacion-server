package app.pijulcel.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.pijulcel.demo.DTOs.LoginPostDTO;
import app.pijulcel.demo.DTOs.LoginResponseDTO;
import app.pijulcel.demo.Repositories.UsuarioRepository;
import app.pijulcel.demo.Services.Interfaces.IAuth;
import app.pijulcel.demo.utils.JwtUtil;

@Service
public class AuthService implements IAuth {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public LoginResponseDTO authenticate(LoginPostDTO loginDTO) {
        Boolean res = usuarioRepository.existsByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());
        if (res) {
            return new LoginResponseDTO(jwtUtil, loginDTO.getUsername());
        } else {
            return null;
        }
    }
    
}
