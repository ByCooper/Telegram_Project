package sky.pro.Animals.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sky.pro.Animals.entity.PetAvatar;
import sky.pro.Animals.service.PetAvatarServiceImpl;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping(path = "/pet-avatar")
public class PetAvatarController {
    private final PetAvatarServiceImpl petAvatarService;

    public PetAvatarController(PetAvatarServiceImpl petAvatarService) {
        this.petAvatarService = petAvatarService;
    }
    @GetMapping(path = "/{id}")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        PetAvatar petAvatar = petAvatarService.findAvatar(id);
        Path avatarPath = Path.of(petAvatar.getFilePath());
        try (
                InputStream is = Files.newInputStream(avatarPath);
                OutputStream os = response.getOutputStream()
        ) {
            response.setContentType(petAvatar.getMediaType());
            response.setContentLength((int) petAvatar.getFileSize());
            is.transferTo(os);
        }
    }

    @GetMapping(path = "{id}/preview")
    public ResponseEntity<byte[]> downloadAvatarPreview(@PathVariable Long id) {
        PetAvatar petAvatar = petAvatarService.findAvatar(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(petAvatar.getMediaType()));
        headers.setContentLength(petAvatar.getData().length);
        return ResponseEntity.ok().headers(headers).body(petAvatar.getData());
    }

    @PostMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long id, @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() >= 1024 * 500) {
            return ResponseEntity.status(400).body("File is too big!");
        }
        petAvatarService.uploadAvatar(id, avatar);
        return ResponseEntity.ok().build();
    }
}
