package tripagramex.domain.image.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tripagramex.domain.image.dto.ImageUploadReq;
import tripagramex.domain.image.dto.ImageUploadRes;
import tripagramex.domain.image.service.ImageService;

import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("image-files")
public class ImageController {

    @Value("${dir}")
    private String path;

    private final ImageService imageService;

    @GetMapping("{imageName}")
    public ResponseEntity<Resource> fileDetails(@PathVariable String imageName) throws MalformedURLException {

        UrlResource urlResource = new UrlResource("file:" + path + imageName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE)
                .body(urlResource);
    }

    @PostMapping
    public ResponseEntity<ImageUploadRes> imageUpload(@Valid @ModelAttribute ImageUploadReq imageUploadReq) {

        List<String> imagePaths = imageService.uploadImages(imageUploadReq, path);

        return new ResponseEntity<>(ImageUploadRes.of(imagePaths), HttpStatus.OK);
    }
}
