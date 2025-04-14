package lt.mariaus.darbas.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class DetaleDTO {
    private Long id;
    private String pavadinimas;
    private BigDecimal kaina;
    private Long kiekis;

    private Long automobilisId;
    private String marke;
    private String vinKodas;

    private Long sandelysId;
    private String sandelioAdresas;
}


