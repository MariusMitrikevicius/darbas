package lt.mariaus.darbas.repository;

import lt.mariaus.darbas.entity.Detale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetaleRepository extends JpaRepository<Detale, Long> {
    @Query("SELECT d FROM Detale d WHERE " +
            "(:vinKodas IS NULL OR d.automobilis.vinKodas LIKE %:vinKodas%) AND " +
            "(:marke IS NULL OR d.automobilis.marke LIKE %:marke%) AND " +
            "(:adresas IS NULL OR d.sandelys.adresas LIKE %:adresas%)")
    List<Detale> findByCustomFilter(
            @Param("vinKodas") String vinKodas,
            @Param("marke") String marke,
            @Param("adresas") String adresas);
}