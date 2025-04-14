package lt.mariaus.darbas.converter;

import lt.mariaus.darbas.dto.AutomobilisDTO;
import lt.mariaus.darbas.entity.Automobilis;
import org.springframework.stereotype.Component;

@Component
public class AutomobilisConverter {
    public AutomobilisDTO toDTO(Automobilis automobilis) {
        AutomobilisDTO dto = new AutomobilisDTO();
        dto.setId(automobilis.getId());
        dto.setVinKodas(automobilis.getVinKodas());
        dto.setMarke(automobilis.getMarke());
        return dto;
    }
    public Automobilis toEntity(AutomobilisDTO dto) {
        Automobilis automobilis = new Automobilis();
        automobilis.setId(dto.getId());
        automobilis.setVinKodas(dto.getVinKodas());
        automobilis.setMarke(dto.getMarke());
        return automobilis;
    }
}

