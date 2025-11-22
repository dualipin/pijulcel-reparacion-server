package app.pijulcel.demo.Services.Interfaces;

import app.pijulcel.demo.DTOs.ApiResponse;
import app.pijulcel.demo.Models.Dispositivo;

public interface IDispositivo {

    ApiResponse<Dispositivo> register(Dispositivo dispositivo);

}