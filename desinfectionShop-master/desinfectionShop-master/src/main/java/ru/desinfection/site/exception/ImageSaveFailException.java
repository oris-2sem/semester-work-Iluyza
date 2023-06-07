package ru.desinfection.site.exception;

public class ImageSaveFailException extends RuntimeException {

    public ImageSaveFailException(String path) {
        super(String.format("Не получилось сохранить картинку с путем: %s", path));
    }
}
