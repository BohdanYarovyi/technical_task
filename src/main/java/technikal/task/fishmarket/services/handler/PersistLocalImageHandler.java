package technikal.task.fishmarket.services.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import technikal.task.fishmarket.exception.FailedOperationException;
import technikal.task.fishmarket.exception.ImagePersistException;
import technikal.task.fishmarket.models.ProductImage;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersistLocalImageHandler implements ImageHandler {
    private final ImageRepository imagePersistence;

    public PersistLocalImageHandler(ImageRepository imagePersistence) {
        this.imagePersistence = imagePersistence;
    }

    // convert an array of multipart file to list of ProductImage and save it locally
    @Override
    public List<ProductImage> handleProductImages(List<MultipartFile> images) {
        if (images == null) {
            throw new IllegalArgumentException("Parameter 'images' is null");
        }
        List<ProductImage> result = new ArrayList<>();

        for (MultipartFile img : images) {
            ProductImage productImage = createProductImage(img);
            try {
                imagePersistence.persistImage(img, productImage.getFileName());
                result.add(productImage);
            } catch (ImagePersistException e) {
                throw new FailedOperationException("Failed to store images, cause: " + e.getMessage());
            }
        }

        return result;
    }

    private ProductImage createProductImage(MultipartFile img) {
        var result = new ProductImage();

        long time = System.currentTimeMillis();
        String storageFileName = time + "_" + img.getOriginalFilename();
        result.setFileName(storageFileName);

        return result;
    }

    @Override
    public void deleteProductImages(List<ProductImage> images) {
        if (images == null) {
            throw new IllegalArgumentException("Parameter 'images' is null");
        }

        for (ProductImage image : images) {
            try {
                imagePersistence.deleteImage(image.getFileName());
            } catch (ImagePersistException e) {
                throw new FailedOperationException("Failed to delete image, cause " + e.getMessage());
            }
        }
    }

}
