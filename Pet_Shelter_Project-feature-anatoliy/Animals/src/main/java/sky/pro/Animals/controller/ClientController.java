package sky.pro.Animals.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sky.pro.Animals.entity.Client;
import sky.pro.Animals.entity.Pet;
import sky.pro.Animals.service.ClientServiceImpl;
import sky.pro.Animals.service.InfoServiceImpl;
import sky.pro.Animals.service.PetServiceImpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Controller for CRUD operations with clients data
 * <p>
 * <hr>
 * <p>
 * Контроллер для CRUD операций с данными клиентов
 */
@RestController
@RequestMapping(path = "/client")
public class ClientController {
    private final ClientServiceImpl clientService;
    private final InfoServiceImpl infoService;
    private final PetServiceImpl petService;

    public ClientController(ClientServiceImpl clientService, InfoServiceImpl infoService, PetServiceImpl petService) {
        this.clientService = clientService;
        this.infoService = infoService;
        this.petService = petService;
    }

    @GetMapping(path = "/get")
    public ResponseEntity<Collection<Client>> getAllClients() {
        infoService.checkInfo();
        Collection<Client> clients = clientService.getAll();
        if (clients == null) {
            return ResponseEntity.status(400).build();
        } else {
            return ResponseEntity.ok().body(clients);
        }
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<Client> getClient(@PathVariable Long id) {
        infoService.checkInfo();
        Client client = clientService.getById(id);
        if (client == null) {
            return ResponseEntity.status(400).build();
        } else {
            return ResponseEntity.ok().body(client);
        }
    }

    @PostMapping(path = "/write")
    public ResponseEntity<Client> writeClient(@RequestParam Long id,
                                              @RequestParam String firstName,
                                              @RequestParam String lastName,
                                              @RequestParam String userName,
                                              @RequestParam String address,
                                              @RequestParam Date birthday,
                                              @RequestParam String passport,
                                              @RequestParam Long chatId,
                                              @RequestParam(required = false) Long firstPetId,
                                              @RequestParam(required = false) Long secondPetId,
                                              @RequestParam(required = false) Long thirdPetId
    ) {
        infoService.checkInfo();
        Collection<Pet> clientPets = new ArrayList<>();
        if (firstPetId == null || petService.getById(firstPetId) == null) {
            clientPets = null;
        } else {
            clientPets.add(petService.getById(firstPetId));
        }
        if (secondPetId != null || petService.getById(secondPetId) != null) {
            clientPets.add(petService.getById(secondPetId));
        }
        if (thirdPetId != null || petService.getById(thirdPetId) != null) {
            clientPets.add(petService.getById(thirdPetId));
        }
        Client client = new Client(id, firstName, lastName, userName, address, birthday, passport, chatId, clientPets);
        Client savedClient = clientService.save(client);
        if (savedClient == null) {
            return ResponseEntity.status(400).build();
        } else {
            return ResponseEntity.ok().body(savedClient);
        }
    }

    @PutMapping(path = "/edit")
    public ResponseEntity<Client> editClient(@RequestParam Long id,
                                             @RequestParam String firstName,
                                             @RequestParam String lastName,
                                             @RequestParam String userName,
                                             @RequestParam String address,
                                             @RequestParam Date birthday,
                                             @RequestParam String passport,
                                             @RequestParam Long chatId,
                                             @RequestParam(required = false) Long firstPetId,
                                             @RequestParam(required = false) Long secondPetId,
                                             @RequestParam(required = false) Long thirdPetId) {
        Collection<Pet> clientPets = new ArrayList<>();
        if (firstPetId == null || petService.getById(firstPetId) == null) {
            clientPets = null;
        } else {
            clientPets.add(petService.getById(firstPetId));
        }
        if (secondPetId != null || petService.getById(secondPetId) != null) {
            clientPets.add(petService.getById(secondPetId));
        }
        if (thirdPetId != null || petService.getById(thirdPetId) != null) {
            clientPets.add(petService.getById(thirdPetId));
        }
        Client client = new Client(id, firstName, lastName, userName, address, birthday, passport, chatId, clientPets);
        Client editedClient = clientService.save(client);
        if (editedClient == null) {
            return ResponseEntity.status(400).build();
        } else {
            return ResponseEntity.ok().body(editedClient);
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Client> deleteClient(@PathVariable Long id) {
        Client deletedClient = clientService.delete(id);
        if (deletedClient == null) {
            return ResponseEntity.status(400).build();
        } else {
            return ResponseEntity.ok().body(deletedClient);
        }
    }
}
