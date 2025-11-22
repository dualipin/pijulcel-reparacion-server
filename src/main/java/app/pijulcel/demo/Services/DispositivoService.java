package app.pijulcel.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.pijulcel.demo.DTOs.ApiResponse;
import app.pijulcel.demo.Models.Dispositivo;
import app.pijulcel.demo.Repositories.DispositivoRepository;
import app.pijulcel.demo.Services.Interfaces.IDispositivo;

@Service
public class DispositivoService implements IDispositivo {

    @Autowired
    private DispositivoRepository dispRep;

    @Override
    public ApiResponse<Dispositivo> register(Dispositivo dispositivo) {
        dispositivo = dispRep.save(dispositivo);
        if(dispositivo == null){
            return new ApiResponse<>(false, "Error al registrar el dispositivo.", null);
        }
        return new ApiResponse<>(true, "Dispositivo registrado con exito.", dispositivo);
    }
    
}
