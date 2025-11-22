package app.pijulcel.demo.Services.Interfaces;

import app.pijulcel.demo.DTOs.ApiResponse;
import app.pijulcel.demo.Models.Usuario;

public interface IUsuario {

    ApiResponse<Void> register(Usuario usuario);
    
}
