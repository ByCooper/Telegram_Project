package sky.pro.Animals.service;

import sky.pro.Animals.entity.Pet;
import sky.pro.Animals.model.PetVariety;

import java.sql.Date;
import java.util.Collection;

public interface PetService {
    Collection<Pet> getAll();

    Pet getById(Long id);

    Pet save(Pet pet);

    Pet delete(Long id);

    String getPetListByVariety(PetVariety petVariety);
}
