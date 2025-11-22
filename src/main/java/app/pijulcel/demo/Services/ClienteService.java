package app.pijulcel.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.pijulcel.demo.DTOs.ApiResponse;
import app.pijulcel.demo.Models.Cliente;
import app.pijulcel.demo.Repositories.ClienteRepository;
import app.pijulcel.demo.Services.Interfaces.ICliente;

@Service
public class ClienteService implements ICliente {

    @Autowired
    private ClienteRepository clienteRep;

    @Override
    public ApiResponse<Cliente> register(Cliente cliente) {
        cliente = clienteRep.save(cliente);
        if(cliente == null){
            return new ApiResponse<>(false, "Error al registrar el cliente.", null);
        }
        return new ApiResponse<>(true, "Cliente registrado con exito.", cliente);
    }

}
