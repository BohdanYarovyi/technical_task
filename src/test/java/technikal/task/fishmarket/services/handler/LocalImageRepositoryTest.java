package technikal.task.fishmarket.services.handler;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import technikal.task.fishmarket.exception.ImagePersistException;
import technikal.task.fishmarket.properties.ImageProperties;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LocalImageRepositoryTest {
    private static final String IMAGE_DIRECTORY = "src/test/images";
    private static final String TEST_FILE_EXTENSION = ".jpeg";

    @Mock
    private ImageProperties properties;
    @InjectMocks
    private LocalImageRepository localImageRepository;

    @BeforeEach
    void createDir() throws IOException {
        Path imgDir = Path.of(IMAGE_DIRECTORY);
        Files.createDirectories(imgDir);
    }

    @AfterEach
    void clearDir() throws IOException {
        Path imgDir = Path.of(IMAGE_DIRECTORY);

        // delete each file in the image directory
        if (Files.exists(imgDir)) {
            Files.list(imgDir)
                    .forEach(file -> {
                        try {
                            Files.delete(file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
    }


    // persistImage(MultipartFile image, String name)
    @Test
    void persistImage_whenMethodCalled_thenPersistAnImage() throws Exception {
        // given
        MultipartFile file = getEmptyMultipartFileWithRandomName();
        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // mockito
        Mockito.when(properties.getDir())
                .thenReturn(IMAGE_DIRECTORY);

        // when ? then
        assertDoesNotThrow(() -> localImageRepository.persistImage(file, filename));
        assertTrue(Files.exists(Path.of(IMAGE_DIRECTORY, filename)));
    }

    private MultipartFile getEmptyMultipartFileWithRandomName() {
        String filename = getRandomFilename();

        try {
            return new MockMultipartFile(filename, filename, null, (InputStream) null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // deleteImage(String name)
    @Test
    void deleteImage_whenAnImageExists_thenDeleteAnImage() throws Exception {
        // given
        String filename = createFileForTest();

        // mockito
        Mockito.when(properties.getDir())
                .thenReturn(IMAGE_DIRECTORY);

        // when & then
        assertDoesNotThrow(() -> localImageRepository.deleteImage(filename));
        assertFalse(Files.exists(Path.of(IMAGE_DIRECTORY, filename)));
    }

    private String createFileForTest() throws IOException {
        String fileName = getRandomFilename();
        Path filePath = Path.of(IMAGE_DIRECTORY, fileName);

        Files.createFile(filePath);

        return fileName;
    }

    @Test
    void deleteImage_whenFileDoesntExists_thenThrowException() {
        // given
        String filename = getRandomFilename();

        // mockito
        Mockito.when(properties.getDir())
                .thenReturn(IMAGE_DIRECTORY);

        // when + then
        assertThrows(ImagePersistException.class, () -> localImageRepository.deleteImage(filename));
    }

    private String getRandomFilename() {
        return UUID.randomUUID() + TEST_FILE_EXTENSION;
    }

}