package technikal.task.fishmarket.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ProductImageValidator {

    /*
            When no one images was added in the list, spring creates an empty object
            of StandartMultipartFile and puts it in the list.

            It is impossible to validate with spring-boot-validation,
            so I decided to validate manually
    */
    public static boolean hasListOnlyFakeFiles(List<MultipartFile> files) {
        return files.stream()
                .filter(e -> !e.isEmpty())
                .toList()
                .isEmpty();
    }

}
