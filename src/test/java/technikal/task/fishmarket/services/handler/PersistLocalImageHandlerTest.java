package technikal.task.fishmarket.services.handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import technikal.task.fishmarket.models.ProductImage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PersistLocalImageHandlerTest {
    public static final String TEST_FILE_EXTENSION = ".jpeg";
    @Mock
    private ImageRepository imageRepository;
    @InjectMocks
    private PersistLocalImageHandler imageHandler;


    // handleProductImages(MultipartFile[] images)
    @Test
    void handleProductImages_whenCalledWithParameter_thenReturnListOfProductImages() throws Exception {
        // given
        List<MultipartFile> images = getFakeMultipartFiles(10);

        // mockito
        // no need to mock imageRepository, cause methods return void

        // when
        List<ProductImage> result = imageHandler.handleProductImages(images);

        // then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(images.size(), result.size());
        result.forEach(item -> assertNotNull(item.getFileName()));
    }

    private List<MultipartFile> getFakeMultipartFiles(int count) throws IOException {
        List<MultipartFile> result = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            var filename = getFilenameWithNumber(i);
            var file = new MockMultipartFile(filename, (InputStream) null);

            result.add(file);
        }

        return result;
    }

    private static String getFilenameWithNumber(int number) {
        return "multipart_file_name_" + number + TEST_FILE_EXTENSION;
    }

    @Test
    void handleProductImages_whenParameterImagesIsNull_thenThrowException() {
        // given
        List<MultipartFile> images = null;

        // when & then
        assertThrows(IllegalArgumentException.class, () -> imageHandler.handleProductImages(images));
    }


    // deleteProductImages(List<ProductImage> images)
    @Test
    void deleteProductImages_whenCalledWithParameter_thenDeleteGivenImages() {
        // given
        List<ProductImage> images = Stream
                .iterate(0, prev -> prev + 1)
                .limit(10)
                .map(PersistLocalImageHandlerTest::getFilenameWithNumber)
                .map(filename -> {
                    var productImage = new ProductImage();
                    productImage.setFileName(filename);

                    return productImage;
                })
                .toList();

        // when & then
        assertDoesNotThrow(() -> imageHandler.deleteProductImages(images));
    }

    @Test
    void deleteProductImages_whenParameterImagesIsNull_thenThrowException() {
        // given
        List<ProductImage> images = null;

        // when & then
        assertThrows(IllegalArgumentException.class, () -> imageHandler.deleteProductImages(images));
    }

}