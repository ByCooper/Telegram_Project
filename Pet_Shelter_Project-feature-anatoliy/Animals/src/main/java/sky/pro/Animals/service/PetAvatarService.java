package sky.pro.Animals.service;

import org.springframework.web.multipart.MultipartFile;
import sky.pro.Animals.entity.PetAvatar;

import java.awt.*;
import java.io.IOException;

public interface PetAvatarService {
    PetAvatar findAvatar(Long id);

    void uploadAvatar(Long id, MultipartFile file) throws IOException;
}
