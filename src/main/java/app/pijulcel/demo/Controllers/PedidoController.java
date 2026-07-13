package app.pijulcel.demo.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import app.pijulcel.demo.DTOs.ApiResponse;
import app.pijulcel.demo.DTOs.PedidoPostDTO;
import app.pijulcel.demo.Models.Pedido;
import app.pijulcel.demo.Services.PedidoService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<ApiResponse<Pedido>> crearPedido(
            @Valid @RequestPart("pedido") PedidoPostDTO pedido,
            @RequestPart(value = "imagenes", required = false) List<MultipartFile> imagenes,
            @RequestPart(value = "audio", required = false) MultipartFile audio,
            @RequestPart(value = "video", required = false) MultipartFile video) {

        log.info("POST /api/pedidos - Solicitud de registro de pedido recibida. barCode={}, imagenes={}, audio={}, video={}",
                pedido.getBarCode(),
                imagenes != null ? imagenes.size() : 0,
                audio != null && !audio.isEmpty(),
                video != null && !video.isEmpty());

        ApiResponse<Pedido> response = pedidoService.registerPedido(pedido, imagenes, audio, video);

        if (response.isSuccess()) {
            log.info("Pedido registrado con éxito. barCode={}", response.getData() != null ? response.getData().getBarCode() : null);
        } else {
            log.warn("Fallo al registrar pedido: {}", response.getMessage());
        }

        return ResponseEntity.status(200).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Pedido>>> getPedidos() {
        return ResponseEntity.status(200).body(pedidoService.getPedidos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Pedido>> getPedidoById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(pedidoService.getPedidoById(id));
    }

    @PutMapping("/update-estatus/{id}/{estatus}")
    public ResponseEntity<ApiResponse<Void>> updateEstatusPedido(@PathVariable Integer id, @PathVariable String estatus) {
        ApiResponse<Void> res = pedidoService.updateEstatusPedido(id, estatus);
        if(res.isSuccess())
            return ResponseEntity.status(200).body(res);

        return ResponseEntity.status(400).body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePedido(@PathVariable Integer id) {
        ApiResponse<Void> res = pedidoService.deletePedido(id);
        if(res.isSuccess())
            return ResponseEntity.status(200).body(res);

        return ResponseEntity.status(400).body(null);
    }

}
