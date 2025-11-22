package app.pijulcel.demo.DTOs;

import app.pijulcel.demo.utils.JwtUtil;
import lombok.Data;

@Data
public class LoginResponseDTO {

    private String token;

    public LoginResponseDTO(JwtUtil jwtUtil, String username) {
        this.token = jwtUtil.generateToken(username);
    }

}
