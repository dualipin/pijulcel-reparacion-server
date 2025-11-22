package app.pijulcel.demo.Controllers;

import java.io.IOException;
import java.nio.file.Files;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.pijulcel.demo.Services.StorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.core.io.Resource;

@RestController
@RequestMapping("api/storage")
public class StorageController {

    @Autowired
    private StorageService storageService;

    @GetMapping("/pedidos/{filename}")
    public ResponseEntity<Resource> getFilePedido(@PathVariable String filename) throws IOException {
        Resource file = storageService.loadAsResource(filename, "pedidos/");
        String contentType = Files.probeContentType(file.getFile().toPath());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(file);
    }

    @GetMapping("/audios/{filename}")
    public ResponseEntity<Resource> getFileAudio(@PathVariable String filename) throws IOException {
        Resource file = storageService.loadAsResource(filename, "audios/");
        String contentType = Files.probeContentType(file.getFile().toPath());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(file);
    }

}
