package app.pijulcel.demo.Services.Interfaces;

import app.pijulcel.demo.DTOs.ApiResponse;
import app.pijulcel.demo.Models.Cliente;

public interface ICliente {
    
    ApiResponse<Cliente> register(Cliente cliente);

    ApiResponse<Void> delete(Integer id);

}
