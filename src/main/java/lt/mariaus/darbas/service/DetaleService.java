package lt.mariaus.darbas.service;

import jakarta.transaction.Transactional;
import lt.mariaus.darbas.entity.Automobilis;
import lt.mariaus.darbas.entity.Detale;
import lt.mariaus.darbas.entity.Sandelys;
import lt.mariaus.darbas.repository.AutomobilisRepository;
import lt.mariaus.darbas.repository.DetaleRepository;
import lt.mariaus.darbas.repository.SandelysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DetaleService {
    @Autowired
    private AutomobilisRepository automobilisRepository;
    @Autowired
    private DetaleRepository detaleRepository;
    @Autowired
    private SandelysRepository sandelysRepository;

    @Transactional
    public void loadTestData() {
        Sandelys[] sandelysArray = new Sandelys[5];
        for (int i = 0; i < 5; i++) {
            Sandelys sandelys = new Sandelys();
            sandelys.setPavadinimas("Sandelys " + (i + 1));
            sandelys.setAdresas("Adresas " + (i + 1));
            sandelysRepository.save(sandelys);
            sandelysArray[i] = sandelys;
        }
        Automobilis[] automobiliaiArray = new Automobilis[10];
        for (int i = 0; i < 10; i++) {
            Automobilis automobilis = new Automobilis();
            automobilis.setVinKodas("VIN" + (i + 1));
            automobilis.setMarke("Marke " + (i + 1));
            automobilisRepository.save(automobilis);
            automobiliaiArray[i] = automobilis;
        }
        for (int i = 0; i < 20; i++) {
            Detale detale = new Detale();
            detale.setPavadinimas("Detale " + (i + 1));
            detale.setKaina(BigDecimal.valueOf(50 + (i * 10))); // Kaina kas kartą padidėja
            detale.setKiekis(10L + (i % 10)); // Kiekis su pasikartojimais
            detale.setAutomobilis(automobiliaiArray[i % 10]); // Susiejame su automobiliu pagal indeksą
            detale.setSandelys(sandelysArray[i % 5]); // Susiejame su sandėliu pagal indeksą
            detaleRepository.save(detale);
        }
    }

}
