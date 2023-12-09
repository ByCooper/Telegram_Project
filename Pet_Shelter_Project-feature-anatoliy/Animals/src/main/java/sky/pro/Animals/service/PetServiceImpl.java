package sky.pro.Animals.service;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import sky.pro.Animals.entity.Pet;
import sky.pro.Animals.model.PetVariety;
import sky.pro.Animals.repository.PetRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for working with pets data
 * <p>
 * <hr>
 * <p>
 * Сервис для работы с данными питомцев
 */
@Service
public class PetServiceImpl implements PetService {
    private final PetRepository petRepository;

    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    /**
     * Method for getting collection with all pets. <br>
     * Used repository method {@link JpaRepository#findAll()}
     * <hr>
     * Метод для получения коллекции, содержащей всех питомцев. <br>
     * Используется метод репозитория {@link JpaRepository#findAll()}
     * <hr>
     *
     * @return Collection with all pets / Коллекцию со всеми питомцами
     * @see JpaRepository#findAll()
     */
    @Override
    public Collection<Pet> getAll() {
        return petRepository.findAll();
    }

    /**
     * Method for getting pet with this id. <br>
     * Used repository method {@link JpaRepository#findById(Object)}
     * <hr>
     * Метод для получения питомца, имеющего данный id. <br>
     * Используется метод репозитория {@link JpaRepository#findById(Object)}
     * <hr>
     *
     * @param id of pet / id питомца
     * @return Pet with this id / Питомца с данным id
     * @see JpaRepository#findById(Object)
     */
    @Override
    public Pet getById(Long id) {
        return petRepository.findById(id).get();
    }


    /**
     * Method for saving pet in DB. <br>
     * Used repository method {@link JpaRepository#save(Object)}
     * <hr>
     * Метод для сохранения питомца в БД. <br>
     * Используется метод репозитория {@link JpaRepository#save(Object)}
     * <hr>
     *
     * @param pet / питомец
     * @return saved pet / сохраненного питомца
     * @see JpaRepository#save(Object)
     */
    @Override
    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

    /**
     * Method for deleting pet with this id from DB. <br>
     * Used repository method {@link JpaRepository#delete(Object)} <br>
     * Also used repository method {@link JpaRepository#findById(Object)}
     * <hr>
     * Метод для удаления питомца с данным id из БД. <br>
     * Используется метод репозитория {@link JpaRepository#delete(Object)} <br>
     * Также используется метод репозитория {@link JpaRepository#findById(Object)}
     * <hr>
     *
     * @param id of pet / id питомца
     * @return deleted pet / удаленного питомца
     * @see JpaRepository#delete(Object)
     * @see JpaRepository#findById(Object)
     */
    @Override
    public Pet delete(Long id) {
        Pet pet = petRepository.getById(id);
        petRepository.delete(pet);
        return pet;
    }

    /**
     * Method for forming a string with a list of animals of a certain species <br>
     * <hr>
     * Метод для формирования строки со списком животных определенного вида <br>
     * <hr>
     *
     * @param petVariety
     * @return string with a list of animals / строку со списком животных
     */
    @Override
    public String getPetListByVariety(PetVariety petVariety) {
        List<Pet> sortedPetList = petRepository.findAllByPetVariety(petVariety);
        String answer = "По заданным критериям получен следующий результат: " + " \n\n";
        for (Pet pet : sortedPetList) {
            answer += " ID : " + pet.getId() + " , имя : " + pet.getName() + " , дата рождения : " + pet.getBirthday() + " \n\n";
        }
        return answer;

    }

    public List<Pet> getAll(PetVariety petVariety) {
        return petRepository.findAllByPetVariety(petVariety);
    }

    public List<Pet> getCat() {
        return  petRepository.findAllByPetVariety(PetVariety.cat);
    }
    public List<Pet> getDog() {
        return  petRepository.findAllByPetVariety(PetVariety.dog);
    }
}
