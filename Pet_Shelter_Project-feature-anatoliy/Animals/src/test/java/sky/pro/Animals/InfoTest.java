package sky.pro.Animals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import sky.pro.Animals.entity.Info;
import sky.pro.Animals.repository.InfoRepository;
import sky.pro.Animals.service.InfoServiceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InfoServiceImplTest {

    @Mock
    private InfoRepository infoRepository;

    @InjectMocks
    private InfoServiceImpl infoService;

    @Test
    void checkInfo() {
        // Arrange
        when(infoRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        infoService.checkInfo();

        // Assert
        verify(infoRepository, times(20)).save(any(Info.class));
    }

    @Test
    void editInfo() {
        // Arrange
        Long infoId = 1L;
        String editedText = "Edited Text";
        Info info = new Info(infoId, "Key", "Original Text");
        when(infoRepository.getById(infoId)).thenReturn(info);

        // Act
        String result = infoService.editInfo(infoId, editedText);

        // Assert
        assertNotNull(result);
        assertEquals("Key изменен(а)", result);
        assertEquals(editedText, info.getText());
        verify(infoRepository, times(21)).save(any(Info.class));
    }

    @Test
    void getAllInfo() {
        // Arrange
        when(infoRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        Collection<Info> result = infoService.getAllInfo();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(infoRepository, times(2)).findAll();
    }

    @Test
    void getInfoTextById() {
        // Arrange
        Long infoId = 1L;
        String expectedText = "Info Text";
        Info info = new Info(infoId, "Key", expectedText);
        when(infoRepository.findById(infoId)).thenReturn(Optional.of(info));

        // Act
        String result = infoService.getInfoTextById(infoId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedText, result);
        verify(infoRepository, times(1)).findById(infoId);
    }
}
