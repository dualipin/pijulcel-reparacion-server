package app.pijulcel.demo.DTOs;

import java.time.LocalDateTime;

import app.pijulcel.demo.Models.Cliente;
import app.pijulcel.demo.Models.Dispositivo;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PedidoPostDTO {

    @NotNull(message = "El código de barras es obligatorio")
    private Integer barCode;

    @NotNull(message = "El cliente es obligatorio")
    private Cliente cliente;

    @NotNull(message = "El dispositivo es obligatorio")
    private Dispositivo dispositivo;
    private String descrip;
    private String recibio;

    @NotNull(message = "La fecha de entrega es obligatoria")
    private LocalDateTime deliveryAt;

}
