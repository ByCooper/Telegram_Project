package sky.pro.Animals.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import sky.pro.Animals.entity.Volunteer;
import sky.pro.Animals.repository.VolunteerRepository;

import java.util.Collection;

/**
 * Service for working with volunteers data
 * <p>
 * <hr>
 * <p>
 * Сервис для работы с данными волонтеров
 */
@Service
public class VolunteerServiceImpl implements VolunteerService {
    private final VolunteerRepository volunteerRepository;

    public VolunteerServiceImpl(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    /**
     * Method for getting collection with all volunteers. <br>
     * Used repository method {@link JpaRepository#findAll()}
     * <hr>
     * Метод для получения коллекции, содержащей всех волонтеров. <br>
     * Используется метод репозитория {@link JpaRepository#findAll()}
     * <hr>
     *
     * @return Collection with all volunteers / Коллекцию со всеми волонтерами
     * @see JpaRepository#findAll()
     */
    @Override
    public Collection<Volunteer> getAll() {
        return volunteerRepository.findAll();
    }

    /**
     * Method for getting volunteer with this id. <br>
     * Used repository method {@link JpaRepository#findById(Object)}
     * <hr>
     * Метод для получения волонтера, имеющего данный id. <br>
     * Используется метод репозитория {@link JpaRepository#findById(Object)}
     * <hr>
     *
     * @param id of volunteer / id волонтера
     * @return Volunteer with this id / Клиента с данным id
     * @see JpaRepository#findById(Object)
     */
    @Override
    public Volunteer getById(Long id) {
        return volunteerRepository.findById(id).get();
    }

    /**
     * Method for saving volunteer in DB. <br>
     * Used repository method {@link JpaRepository#save(Object)}
     * <hr>
     * Метод для сохранения волонтера в БД. <br>
     * Используется метод репозитория {@link JpaRepository#save(Object)}
     * <hr>
     *
     * @param volunteer / волонтер
     * @return saved volunteer / сохраненного волонтера
     * @see JpaRepository#save(Object)
     */
    @Override
    public Volunteer save(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    /**
     * Method for deleting volunteer with this id from DB. <br>
     * Used repository method {@link JpaRepository#delete(Object)} <br>
     * Also used repository method {@link JpaRepository#findById(Object)}
     * <hr>
     * Метод для удаления волонтера с данным id из БД. <br>
     * Используется метод репозитория {@link JpaRepository#delete(Object)} <br>
     * Также используется метод репозитория {@link JpaRepository#findById(Object)}
     * <hr>
     *
     * @param id of volunteer / id волонтера
     * @return deleted volunteer / удаленного волонтера
     * @see JpaRepository#delete(Object)
     * @see JpaRepository#findById(Object)
     */
    @Override
    public Volunteer delete(Long id) {
        Volunteer volunteer = volunteerRepository.getById(id);
        volunteerRepository.delete(volunteer);
        return volunteer;
    }
}
