package app.pijulcel.demo.Controllers;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.sql.DataSource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.pijulcel.demo.DTOs.ApiResponse;
import app.pijulcel.demo.DTOs.HealthResponseDTO;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    private final DataSource dataSource;

    public HealthController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<HealthResponseDTO>> healthcheck() {
        boolean databaseConnected = isDatabaseConnected();
        HealthResponseDTO data = new HealthResponseDTO(
                ZonedDateTime.now(ZoneId.of("America/Mexico_City")).toString(),
                databaseConnected);

        if (!databaseConnected) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new ApiResponse<>(false, "Servicio disponible, pero la base de datos no responde", data));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "Servicio saludable", data));
    }

    private boolean isDatabaseConnected() {
        try (Connection connection = dataSource.getConnection()) {
            return connection.isValid(2);
        } catch (SQLException ex) {
            return false;
        }
    }

}
