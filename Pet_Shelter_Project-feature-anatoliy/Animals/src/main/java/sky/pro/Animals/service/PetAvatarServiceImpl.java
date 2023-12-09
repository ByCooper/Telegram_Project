package sky.pro.Animals.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sky.pro.Animals.entity.Pet;
import sky.pro.Animals.entity.PetAvatar;
import sky.pro.Animals.repository.PetAvatarRepository;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class PetAvatarServiceImpl implements PetAvatarService {
    @Value("${pet.avatar.dir.path}")
    private String petAvatarsDir;
    private final PetServiceImpl petService;
    private final PetAvatarRepository petAvatarRepository;
    private final Logger logger = LoggerFactory.getLogger(PetAvatarServiceImpl.class);

    public PetAvatarServiceImpl(PetServiceImpl petService, PetAvatarRepository petAvatarRepository) {
        this.petService = petService;
        this.petAvatarRepository = petAvatarRepository;
    }

    @Override
    public PetAvatar findAvatar(Long id) {
        logger.info("Searching avatar by id {}", id);
        PetAvatar answer = petAvatarRepository.findById(id).get();
        logger.debug("Getting answer {}", answer);
        return answer;
    }

    @Override
    public void uploadAvatar(Long id, MultipartFile file) throws IOException {
        logger.info("Trying to upload avatar for student with id {}", id);
        Pet pet = petService.getById(id);

        Path filePath = Path.of(petAvatarsDir, id + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = file.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }

        PetAvatar petAvatar = getPetAvatar(id);
        petAvatar.setPet(pet);
        petAvatar.setFilePath(filePath.toString());
        petAvatar.setFileSize(file.getSize());
        petAvatar.setMediaType(file.getContentType());
        petAvatar.setData(generateImagePreview(filePath));

        petAvatarRepository.save(petAvatar);
    }

    public String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    public PetAvatar getPetAvatar(Long id) {
        return petAvatarRepository.findByPetId(id).orElse(new PetAvatar());
    }

    public byte[] generateImagePreview(Path filePath) throws IOException {
        try (
                InputStream is = Files.newInputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ) {
            BufferedImage image = ImageIO.read(bis);
            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();
            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }
}
