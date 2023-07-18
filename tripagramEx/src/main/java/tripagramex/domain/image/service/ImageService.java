package tripagramex.domain.image.service;

import org.springframework.web.multipart.MultipartFile;
import tripagramex.domain.image.dto.ImageUploadReq;

import java.util.List;

public interface ImageService {

    public String upload(MultipartFile image, String path);

    public List<String> uploadImages(ImageUploadReq imageUploadReq, String path);
}
