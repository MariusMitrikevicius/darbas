package lt.mariaus.darbas.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lt.mariaus.darbas.DetalesTipas;
import java.math.BigDecimal;
@Getter
@Setter
@Entity
@ToString
public class Detale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String pavadinimas;
    @Column(nullable = false)
    private BigDecimal kaina;
    @Column(nullable = false)
    private Long kiekis;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "automobilis_id", nullable = false)
    private Automobilis automobilis;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sandelys_id")
    private Sandelys sandelys;
    @Enumerated(EnumType.STRING)
    private DetalesTipas tipas;
}

