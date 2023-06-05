package tripagramex.domain.image.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tripagramex.domain.image.dto.ImageUploadReq;
import tripagramex.domain.image.repository.ImageRepository;
import tripagramex.global.exception.BusinessLogicException;
import tripagramex.global.exception.ExceptionCode;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public String uploadImage(MultipartFile image, String path) {

        validateImage(image);

        String filename = createFilename(image);

        try {
            return imageRepository.save(image, filename, path);
        } catch (IOException e) {
            throw new BusinessLogicException(ExceptionCode.UPLOAD_FAILED);
        }
    }

    public List<String> uploadImages(ImageUploadReq imageUploadReq, String path) {

        return imageUploadReq.getImages().stream()
                .map(image -> uploadImage(image, path))
                .collect(Collectors.toList());
    }

    public String createFilename(MultipartFile image) {

        String originalFilename = image.getOriginalFilename();
        int pos = originalFilename.lastIndexOf(".");
        return UUID.randomUUID() + originalFilename.substring(pos);
    }

    public void validateImage(MultipartFile image) {

        if (image == null || image.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.EMPTY_FILE);
        }

        String imageName = image.getOriginalFilename();

        if (imageName == null) {
            throw new BusinessLogicException(ExceptionCode.ILLEGAL_FILENAME);
        }

        boolean matches = imageName.matches("^\\S.*\\.(jpg|JPG|jpeg|JPEG|png|PNG)$");
        if (!matches) {
            throw new BusinessLogicException(ExceptionCode.ILLEGAL_FILENAME);
        }
    }
}
