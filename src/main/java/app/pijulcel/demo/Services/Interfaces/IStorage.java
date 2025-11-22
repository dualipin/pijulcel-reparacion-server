package app.pijulcel.demo.Services.Interfaces;

import java.io.IOException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IStorage {

    void init() throws IOException;

    String store(MultipartFile file, String prefix);
    String store(MultipartFile file, String prefix, String name);
    Resource loadAsResource(String filename, String prefix);

    void delete(String filename);
    
}
