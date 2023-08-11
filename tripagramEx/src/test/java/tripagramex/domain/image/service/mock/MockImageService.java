package tripagramex.domain.image.service.mock;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tripagramex.domain.image.dto.ImageUploadReq;
import tripagramex.domain.image.service.ImageService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@ConditionalOnProperty(value = "mode.test", havingValue = "true")
public class MockImageService implements ImageService {

    @Override
    public String upload(MultipartFile image, String path) {
        return path + image.getOriginalFilename();
    }

    @Override
    public List<String> uploadImages(ImageUploadReq imageUploadReq, String path) {
        List<MultipartFile> images = imageUploadReq.getImages();
        return images.stream()
                .map(image -> upload(image, path))
                .collect(Collectors.toList());
    }
}
