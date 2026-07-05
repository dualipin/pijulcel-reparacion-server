package app.pijulcel.demo.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthResponseDTO {

    private String timestamp;
    private boolean databaseConnected;

}
