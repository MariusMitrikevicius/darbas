package lt.mariaus.darbas.converter;

import lt.mariaus.darbas.dto.DetaleDTO;
import lt.mariaus.darbas.entity.Detale;
import org.springframework.stereotype.Component;

@Component
public class DetaleConverter {

    public DetaleDTO convertToDto(Detale detale) {
        DetaleDTO dto = new DetaleDTO();
        dto.setId(detale.getId());
        dto.setPavadinimas(detale.getPavadinimas());
        dto.setKaina(detale.getKaina());
        dto.setKiekis(detale.getKiekis());
        if (detale.getAutomobilis() != null) {
            dto.setAutomobilisId(detale.getAutomobilis().getId());
        }
        if (detale.getSandelys() != null) {
            dto.setSandelysId(detale.getSandelys().getId());
        }
        return dto;
    }

    public Detale toEntity(DetaleDTO dto) {
        Detale detale = new Detale();
        detale.setId(dto.getId());
        detale.setPavadinimas(dto.getPavadinimas());
        detale.setKaina(dto.getKaina());
        detale.setKiekis(dto.getKiekis());
        return detale;
    }
}

