package sky.pro.Animals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sky.pro.Animals.entity.PetAvatar;

import java.util.Optional;

@Repository
public interface PetAvatarRepository extends JpaRepository<PetAvatar, Long> {
    Optional<PetAvatar> findByPetId(Long petId);
}
