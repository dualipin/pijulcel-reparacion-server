package app.pijulcel.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.pijulcel.demo.DTOs.ApiResponse;
import app.pijulcel.demo.DTOs.LoginPostDTO;
import app.pijulcel.demo.DTOs.LoginResponseDTO;
import app.pijulcel.demo.Services.AuthService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/test")
    public ResponseEntity<Void> test() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@Valid @RequestBody LoginPostDTO loginDTO) {
        
        LoginResponseDTO res = authService.authenticate(loginDTO);
        if (res == null) 
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ApiResponse<>(false, "Credenciales inválidas", null));
        
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(true, "Login exitoso", res));
    }
    
}
