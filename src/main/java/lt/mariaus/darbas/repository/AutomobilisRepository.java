package lt.mariaus.darbas.repository;

import lt.mariaus.darbas.entity.Automobilis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AutomobilisRepository extends JpaRepository<Automobilis, Long> {
    Optional<Automobilis> findByVinKodas(String vinKodas);
}