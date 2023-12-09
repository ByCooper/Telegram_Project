package sky.pro.Animals.service;

import sky.pro.Animals.entity.Client;

import java.util.Collection;

public interface ClientService {
    Collection<Client> getAll();

    Client getById(Long id);

    Client save(Client client);

    Client delete(Long id);

    Client getByChatId(Long chatId);
}
