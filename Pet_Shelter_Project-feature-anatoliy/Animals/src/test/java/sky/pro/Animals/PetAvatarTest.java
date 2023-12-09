package sky.pro.Animals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import sky.pro.Animals.entity.Pet;
import sky.pro.Animals.entity.PetAvatar;
import sky.pro.Animals.model.PetVariety;
import sky.pro.Animals.repository.PetAvatarRepository;
import sky.pro.Animals.service.PetAvatarServiceImpl;
import sky.pro.Animals.service.PetServiceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetAvatarServiceImplTest {

    @Mock
    private PetServiceImpl petService;

    @Mock
    private PetAvatarRepository petAvatarRepository;

    @InjectMocks
    private PetAvatarServiceImpl petAvatarService;

    private final String petAvatarsDir = "src/test/resources/petAvatars";

    @Test
    void findAvatar() {
        // Arrange
        Long petAvatarId = 1L;
        PetAvatar expectedPetAvatar = new PetAvatar();
        when(petAvatarRepository.findById(petAvatarId)).thenReturn(Optional.of(expectedPetAvatar));

        // Act
        PetAvatar actualPetAvatar = petAvatarService.findAvatar(petAvatarId);

        // Assert
        assertEquals(expectedPetAvatar, actualPetAvatar);
    }

    @Test
    void uploadAvatar() throws IOException {
        // Arrange
        Long petId = 1L;
        MultipartFile file = new MockMultipartFile("testFile", "test.txt", "text/plain", "Hello, World!".getBytes());
        Pet pet = new Pet(1L, "Fluffy", new Date(2023, 11, 20), true, PetVariety.cat, null);
        when(petService.getById(eq(petId))).thenReturn(pet);

        // Act
        petAvatarService.uploadAvatar(petId, file);

        // Assert
        verify(petAvatarRepository, times(1)).save(any());
    }

    @Test
    void getExtension() {
        // Arrange
        PetAvatarServiceImpl petAvatarService = new PetAvatarServiceImpl(null, null);
        String filename = "test.txt";

        // Act
        String extension = petAvatarService.getExtension(filename);

        // Assert
        assertEquals("txt", extension);
    }

    @Test
    void getPetAvatar() {
        // Arrange
        Long petId = 1L;
        PetAvatar expectedPetAvatar = new PetAvatar();
        when(petAvatarRepository.findByPetId(petId)).thenReturn(Optional.of(expectedPetAvatar));

        // Act
        PetAvatar actualPetAvatar = petAvatarService.getPetAvatar(petId);

        // Assert
        assertEquals(expectedPetAvatar, actualPetAvatar);
    }

    @Test
    void generateImagePreview() throws IOException {
        // Arrange
        Path filePath = Paths.get("src/test/resources/testImage.jpg");
        byte[] previewImageData = Files.readAllBytes(filePath);

        // Act
        byte[] generatedImagePreview = petAvatarService.generateImagePreview(filePath);

        // Assert
        assertEquals(previewImageData.length, generatedImagePreview.length);
    }
}