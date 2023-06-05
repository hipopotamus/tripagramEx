package tripagramex.domain.image.repository;

import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface ImageRepository {

    String save(MultipartFile image, String filename, String path) throws IOException;

    BufferedImage download(String filename, String path) throws IOException;
}
