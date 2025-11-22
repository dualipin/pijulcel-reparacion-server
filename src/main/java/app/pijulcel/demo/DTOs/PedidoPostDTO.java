package app.pijulcel.demo.DTOs;

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

}
