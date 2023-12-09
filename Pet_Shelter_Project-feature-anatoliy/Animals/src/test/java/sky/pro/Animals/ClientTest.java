package sky.pro.Animals;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sky.pro.Animals.entity.Client;
import sky.pro.Animals.repository.ClientRepository;
import sky.pro.Animals.service.ClientServiceImpl;

@ExtendWith(MockitoExtension.class)
class ClientImplTests {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    void testGetAllClients() {
        // Mocking the repository behavior
        when(clientRepository.findAll()).thenReturn(Arrays.asList(new Client(), new Client()));

        // Calling the service method
        Collection<Client> clients = clientService.getAll();

        // Assertions
        assertNotNull(clients);
        assertEquals(2, clients.size());
    }

    @Test
    void testGetClientById() {
        Long clientId = 1L;
        // Mocking the repository behavior
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(new Client()));

        // Calling the service method
        Client client = clientService.getById(clientId);

        // Assertions
        assertNotNull(client);
    }

    @Test
    void testSaveClient() {
        Client clientToSave = new Client(); // You can create a client object with necessary data
        // Mocking the repository behavior
        when(clientRepository.save(clientToSave)).thenReturn(clientToSave);

        // Calling the service method
        Client savedClient = clientService.save(clientToSave);

        // Assertions
        assertNotNull(savedClient);
        // Add more assertions as needed
    }

    @Test
    void testDeleteClient() {
        Long clientId = 1L;
        // Mocking the repository behavior
        //when(clientRepository.findById(clientId)).thenReturn(Optional.of(new Client()));

        // Calling the service method
        Client deletedClient = clientService.delete(clientId);
        System.out.println(deletedClient);

        // Assertions
        assertEquals(null, deletedClient);
        // Add more assertions as needed
    }
}
