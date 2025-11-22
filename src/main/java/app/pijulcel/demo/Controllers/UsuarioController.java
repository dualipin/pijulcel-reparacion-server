package app.pijulcel.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.pijulcel.demo.DTOs.ApiResponse;
import app.pijulcel.demo.Models.Usuario;
import app.pijulcel.demo.Services.UsuarioService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> postMethodName(@RequestBody Usuario usuario) {
        try {
            ApiResponse<Void> response = usuarioService.register(usuario);
            if(response == null){
                return ResponseEntity.status(500).body(new ApiResponse<Void>(false, "Error al registrar el usuario", null));
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<Void>(false, "Error interno del servidor", null));
        }
    }

}
