package sky.pro.Animals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sky.pro.Animals.entity.Pet;
import sky.pro.Animals.model.PetVariety;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findAllByPetVariety(PetVariety petVariety);
}
