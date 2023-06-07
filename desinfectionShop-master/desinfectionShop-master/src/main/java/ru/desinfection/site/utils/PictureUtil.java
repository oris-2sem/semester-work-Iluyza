package ru.desinfection.site.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.desinfection.site.exception.ImageSaveFailException;

@Slf4j
@Component
public class PictureUtil {

    @Value("${imageCart-path}")
    private String imageCartPath;
    @Value("${imageCart-width}")
    private int imageCartWidth;
    @Value("${imageCart-height}")
    private int imageCartHeight;

    @Value("${imageCatalog-path}")
    private String imageCatalogPath;
    @Value("${imageCatalog-width}")
    private int imageCatalogWidth;
    @Value("${imageCatalog-height}")
    private int imageCatalogHeight;

    @Value("${imageDetails-path}")
    private String imageDetailsPath;
    @Value("${imageDetails-width}")
    private int imageDetailsWidth;
    @Value("${imageDetails-height}")
    private int imageDetailsHeight;

    public String saveItemDetailsImageAndGetPath(MultipartFile image, Long itemId) {
        log.info("Trying to save image with item id: {}", itemId);

        Optional<String> ext = Optional.ofNullable(image.getOriginalFilename())
                .filter(f -> f.contains("."))
                .map(f -> f.substring(image.getOriginalFilename().lastIndexOf(".") + 1));

        String fileName = "item_" + itemId + "." + ext.get();

        try {
            saveImageResized(image.getInputStream(), fileName, imageCartWidth, imageCartHeight, imageCartPath);
            saveImageResized(image.getInputStream(), fileName, imageCatalogWidth, imageCatalogHeight, imageCatalogPath);
            saveImageResized(image.getInputStream(), fileName, imageDetailsWidth, imageDetailsHeight, imageDetailsPath);
        } catch (IOException e) {
            throw new ImageSaveFailException(fileName);
        }

        log.info("Image successfully saved to path: {}", fileName);
        return fileName;
    }

    private void saveImageResized(InputStream imageStream, String fileName, int width, int height, String path) throws IOException {
        Thumbnails.of(imageStream)
                .size(width, height)
                .allowOverwrite(true)
                .toFile(new File(path + fileName));
    }
}
