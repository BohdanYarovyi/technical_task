package technikal.task.fishmarket.services.handler;

import org.springframework.web.multipart.MultipartFile;
import technikal.task.fishmarket.models.ProductImage;

import java.util.List;

public interface ImageHandler {

    List<ProductImage> handleProductImages(List<MultipartFile> images);
    void deleteProductImages(List<ProductImage> images);

}
