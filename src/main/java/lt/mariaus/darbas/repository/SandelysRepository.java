package lt.mariaus.darbas.repository;

import lt.mariaus.darbas.entity.Sandelys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SandelysRepository extends JpaRepository<Sandelys, Long> {
    Optional<Sandelys> findByAdresas(String adresas);
}