package lt.mariaus.darbas.converter;

import lt.mariaus.darbas.dto.DetaleDTO;
import lt.mariaus.darbas.entity.Automobilis;
import lt.mariaus.darbas.entity.Detale;
import lt.mariaus.darbas.entity.Sandelys;
import org.springframework.stereotype.Component;

@Component
public class DetaleConverter {
    public DetaleDTO convertToDto(Detale detale) {
        DetaleDTO dto = new DetaleDTO();
        dto.setId(detale.getId());
        dto.setPavadinimas(detale.getPavadinimas());
        dto.setKaina(detale.getKaina());
        dto.setKiekis(detale.getKiekis());
        dto.setAutomobilisId(detale.getAutomobilis().getId());
        dto.setMarke(detale.getAutomobilis().getMarke());
        dto.setVinKodas(detale.getAutomobilis().getVinKodas());
        if (detale.getSandelys() != null) {
            dto.setSandelysId(detale.getSandelys().getId());
            dto.setSandelioAdresas(detale.getSandelys().getAdresas());
        }
        dto.setTipas(detale.getTipas());
        return dto;
    }

    public Detale convertToEntity(DetaleDTO detaleDTO, Automobilis automobilis, Sandelys sandelys) {

        Detale detale = new Detale();
        detale.setPavadinimas(detaleDTO.getPavadinimas());
        detale.setKaina(detaleDTO.getKaina());
        detale.setKiekis(detaleDTO.getKiekis());
        detale.setAutomobilis(automobilis);
        detale.setSandelys(sandelys);
        detale.setTipas(detaleDTO.getTipas());
        return detale;
    }
}