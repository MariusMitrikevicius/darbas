package lt.mariaus.darbas.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@Entity
@ToString
public class Automobilis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 17, unique = true, nullable = false)
    private String vinKodas;

    @Column(nullable = false, length = 50)
    private String marke;

    @OneToMany(mappedBy = "automobilis", fetch = FetchType.LAZY)
    private List<Detale> detales;
}
