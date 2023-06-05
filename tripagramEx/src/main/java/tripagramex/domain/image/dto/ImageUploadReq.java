package tripagramex.domain.image.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ImageUploadReq {

    @NotEmpty
    List<MultipartFile> images;
}
