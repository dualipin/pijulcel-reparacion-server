package app.pijulcel.demo.Services;

import org.springframework.stereotype.Service;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.UUID;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import app.pijulcel.demo.Services.Interfaces.IStorage;
import jakarta.annotation.PostConstruct;

@Service
public class StorageService implements IStorage {

    @Value("${media.location}")
    private String mediaLocation;

    private Path rootLocation;

    @Override
    @PostConstruct
    public void init() throws IOException {
        rootLocation = Paths.get(mediaLocation);
        Files.createDirectories(rootLocation);
    }

    @Override
    public String store(MultipartFile file, String prefix, String name) {
        try {

            validateFile(file);
            
            String filename = name;
            Path destinationFile = buildDestinationPath(filename, prefix);

            if (file.getSize() > 100 * 1024) {
                compressAndSaveImage(file, destinationFile);
            } else {
                saveFileDirectly(file, destinationFile);
            }

            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    @Override
    public String store(MultipartFile file, String prefix) {
        try {
            validateFile(file);

            String filename = generateFileName();
            Path destinationFile = buildDestinationPath(filename, prefix);

            if (file.getSize() > 100 * 1024) {
                compressAndSaveImage(file, destinationFile);
            } else {
                saveFileDirectly(file, destinationFile);
            }

            return filename;

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    public String saveAudio(MultipartFile file, String filename) {
        try {
            validateFile(file);
            Path destinationFile = buildDestinationPath(filename, "audios/");

            if (file.getSize() > 100 * 1024) {
                compressAndSaveImage(file, destinationFile);
            } else {
                saveFileDirectly(file, destinationFile);
            }

            return filename;

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    /*
     * ============================
     * Métodos privados de apoyo
     * ============================
     */

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Cannot store empty file");
        }

        if (file.getOriginalFilename() == null) {
            throw new IllegalArgumentException("File original filename is null");
        }
    }

    public String generateFileName() {
        // 🔥 Siempre guardamos como JPG
        return UUID.randomUUID().toString() + ".jpg";
    }

    public String generateFileAudio() {
        return UUID.randomUUID().toString() + ".webm";
    }

    private Path buildDestinationPath(String filename, String prefix) {
        return rootLocation
                .resolve(prefix + Paths.get(filename))
                .normalize()
                .toAbsolutePath();
    }

    private void compressAndSaveImage(MultipartFile file, Path destinationFile) throws IOException {
        BufferedImage originalImage = ImageIO.read(file.getInputStream());
        if (originalImage == null) {
            throw new IllegalArgumentException("El archivo no es una imagen válida");
        }

        // 🔥 Convertir a RGB (quita canal alpha si existe)
        BufferedImage rgbImage = new BufferedImage(
                originalImage.getWidth(),
                originalImage.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        rgbImage.createGraphics().drawImage(originalImage, 0, 0, java.awt.Color.WHITE, null);

        // Guardar como JPG comprimido
        try (OutputStream os = Files.newOutputStream(destinationFile)) {
            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
            if (!writers.hasNext()) {
                throw new IllegalStateException("No writers found for jpg");
            }

            ImageWriter writer = writers.next();
            try (ImageOutputStream ios = ImageIO.createImageOutputStream(os)) {
                writer.setOutput(ios);

                ImageWriteParam param = writer.getDefaultWriteParam();
                if (param.canWriteCompressed()) {
                    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                    param.setCompressionQuality(0.6f); // calidad ~60%
                }

                writer.write(null, new IIOImage(rgbImage, null, null), param);
            }
            writer.dispose();
        }
    }

    private void saveFileDirectly(MultipartFile file, Path destinationFile) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    @Override
    public Resource loadAsResource(String filename, String prefix) {

        try {
            System.out.println("rootLocation: " + rootLocation.toString());
            Path file = rootLocation.resolve(prefix + filename);
            System.out.println("Buscando archivo en: " + file.toAbsolutePath());
            Resource resource = new UrlResource((file.toUri()));

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + filename, e);
        }

    }

    @Override
    public void delete(String filename) {
        try {
            Path fileToDelete = rootLocation.resolve(filename).normalize().toAbsolutePath();
            if (Files.exists(fileToDelete)) {
                Files.delete(fileToDelete);
                System.out.println("Archivo eliminado: " + fileToDelete.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar el archivo: " + filename, e);
        }
    }

}