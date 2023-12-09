package sky.pro.Animals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sky.pro.Animals.entity.Info;

@Repository
public interface InfoRepository extends JpaRepository<Info, Long> {
}
