package technikal.task.fishmarket.services.handler;

import org.springframework.web.multipart.MultipartFile;
import technikal.task.fishmarket.exception.ImagePersistException;

public interface ImageRepository {

    void persistImage(MultipartFile image, String name) throws ImagePersistException;
    void deleteImage(String name) throws ImagePersistException;
}
