package technikal.task.fishmarket.services.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import technikal.task.fishmarket.exception.ImagePersistException;
import technikal.task.fishmarket.properties.ImageProperties;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Component
public class LocalImageRepository implements ImageRepository {
    private final ImageProperties imageProperties;

    public LocalImageRepository(ImageProperties imageProperties) {
        this.imageProperties = imageProperties;
    }

    @Override
    public void persistImage(MultipartFile image, String name) throws ImagePersistException {
        try (InputStream inputStream = image.getInputStream()) {
            createDirIfRequired();

            var path = Path.of(imageProperties.getDir(), name);
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ImagePersistException(e.getMessage());
        }
    }

    private void createDirIfRequired() throws IOException {
        var dir = Path.of(imageProperties.getDir());

        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        }
    }

    @Override
    public void deleteImage(String name) throws ImagePersistException {
        try {
            Path path = Path.of(imageProperties.getDir(), name);

            Files.delete(path);
        } catch (IOException e) {
            throw new ImagePersistException("Failed to remove image " + name);
        }

    }

}
