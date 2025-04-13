package lt.mariaus.darbas.converter;

import lt.mariaus.darbas.dto.SandelysDTO;
import lt.mariaus.darbas.entity.Sandelys;
import org.springframework.stereotype.Component;

@Component
public class SandelysConverter {

    public SandelysDTO toDTO(Sandelys sandelys) {
        SandelysDTO dto = new SandelysDTO();
        dto.setId(sandelys.getId());
        dto.setPavadinimas(sandelys.getPavadinimas());
        dto.setAdresas(sandelys.getAdresas());
        return dto;
    }

    public Sandelys toEntity(SandelysDTO dto) {
        Sandelys sandelys = new Sandelys();
        sandelys.setId(dto.getId());
        sandelys.setPavadinimas(dto.getPavadinimas());
        sandelys.setAdresas(dto.getAdresas());
        return sandelys;
    }
}



