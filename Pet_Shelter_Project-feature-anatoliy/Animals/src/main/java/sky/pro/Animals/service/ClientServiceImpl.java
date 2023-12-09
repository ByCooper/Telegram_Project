package sky.pro.Animals.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import sky.pro.Animals.entity.Client;
import sky.pro.Animals.repository.ClientRepository;

import java.util.Collection;

/**
 * Service for working with clients data
 * <p>
 * <hr>
 * <p>
 * Сервис для работы с данными клиентов
 */
@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Method for getting collection with all clients. <br>
     * Used repository method {@link JpaRepository#findAll()}
     * <hr>
     * Метод для получения коллекции, содержащей всех клиентов. <br>
     * Используется метод репозитория {@link JpaRepository#findAll()}
     * <hr>
     *
     * @return Collection with all clients / Коллекцию со всеми клиентами
     * @see JpaRepository#findAll()
     */
    @Override
    public Collection<Client> getAll() {
        return clientRepository.findAll();
    }

    /**
     * Method for getting client with this id. <br>
     * Used repository method {@link JpaRepository#findById(Object)}
     * <hr>
     * Метод для получения клиента, имеющего данный id. <br>
     * Используется метод репозитория {@link JpaRepository#findById(Object)}
     * <hr>
     *
     * @param id of client / id клиента
     * @return Client with this id / Клиента с данным id
     * @see JpaRepository#findById(Object)
     */
    @Override
    public Client getById(Long id) {
        return clientRepository.findById(id).get();
    }

    /**
     * Method for saving client in DB. <br>
     * Used repository method {@link JpaRepository#save(Object)}
     * <hr>
     * Метод для сохранения клиента в БД. <br>
     * Используется метод репозитория {@link JpaRepository#save(Object)}
     * <hr>
     *
     * @param client / клиент
     * @return saved client / сохраненного клиента
     * @see JpaRepository#save(Object)
     */
    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    /**
     * Method for deleting client with this id from DB. <br>
     * Used repository method {@link JpaRepository#delete(Object)} <br>
     * Also used repository method {@link JpaRepository#findById(Object)}
     * <hr>
     * Метод для удаления клиента с данным id из БД. <br>
     * Используется метод репозитория {@link JpaRepository#delete(Object)} <br>
     * Также используется метод репозитория {@link JpaRepository#findById(Object)}
     * <hr>
     *
     * @param id of client / id клиента
     * @return deleted client / удаленного клиента
     * @see JpaRepository#delete(Object)
     * @see JpaRepository#findById(Object)
     */
    @Override
    public Client delete(Long id) {
        Client client = clientRepository.getById(id);
        clientRepository.delete(client);
        return client;
    }

    @Override
    public Client getByChatId(Long chatId) {
        return clientRepository.findByChatId(chatId).get();
    }
}
