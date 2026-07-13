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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> postMethodName(@RequestBody Usuario usuario) {
        log.info("POST /api/usuarios - Solicitud de registro de usuario recibida. username={}", usuario.getUsername());
        try {
            ApiResponse<Void> response = usuarioService.register(usuario);
            if(response == null){
                log.error("Registro de usuario username={} devolvió una respuesta nula", usuario.getUsername());
                return ResponseEntity.status(500).body(new ApiResponse<Void>(false, "Error al registrar el usuario", null));
            }
            if (response.isSuccess()) {
                log.info("Usuario registrado con éxito. username={}", usuario.getUsername());
            } else {
                log.warn("Fallo al registrar usuario username={}: {}", usuario.getUsername(), response.getMessage());
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error interno al registrar usuario username={}", usuario.getUsername(), e);
            return ResponseEntity.status(500).body(new ApiResponse<Void>(false, "Error interno del servidor", null));
        }
    }

}
