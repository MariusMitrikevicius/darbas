package lt.mariaus.darbas.service;

import jakarta.transaction.Transactional;
import lt.mariaus.darbas.DetalesTipas;
import lt.mariaus.darbas.converter.DetaleConverter;
import lt.mariaus.darbas.dto.DetaleDTO;
import lt.mariaus.darbas.entity.Automobilis;
import lt.mariaus.darbas.entity.Detale;
import lt.mariaus.darbas.entity.Sandelys;
import lt.mariaus.darbas.repository.AutomobilisRepository;
import lt.mariaus.darbas.repository.DetaleRepository;
import lt.mariaus.darbas.repository.SandelysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Random;
import java.util.List;

@Service
public class DetaleService {
    @Autowired
    private AutomobilisRepository automobilisRepository;
    @Autowired
    private DetaleRepository detaleRepository;
    @Autowired
    private SandelysRepository sandelysRepository;
    @Autowired
    private DetaleConverter detaleConverter;

    public Detale getDetaleById(Long id) {
        return detaleRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteDetale(Long id) {
        Detale detale = detaleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalė nerasta ID: " + id));
        detaleRepository.delete(detale);
    }

    @Transactional
    public Detale updateDetale(Long id, DetaleDTO dto) {
        Detale existing = detaleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalė nerasta ID: " + id));
        if (dto.getPavadinimas() != null) {
            existing.setPavadinimas(dto.getPavadinimas());
        }
        if (dto.getKaina() != null) {
            existing.setKaina(dto.getKaina());
        }
        if (dto.getKiekis() != null) {
            existing.setKiekis(dto.getKiekis());
        }
        if (dto.getAutomobilisId() != null) {
            Automobilis automobilis = automobilisRepository.findById(dto.getAutomobilisId())
                    .orElseThrow(() -> new RuntimeException("Automobilis nerastas ID: " + dto.getAutomobilisId()));
            existing.setAutomobilis(automobilis);
        }
        if (dto.getSandelysId() != null) {
            Sandelys sandelys = sandelysRepository.findById(dto.getSandelysId())
                    .orElseThrow(() -> new RuntimeException("Sandėlys nerastas ID: " + dto.getSandelysId()));
            existing.setSandelys(sandelys);
        }
        if (dto.getTipas() != null) {
            existing.setTipas(dto.getTipas());
        }
        return detaleRepository.save(existing);
    }

    @Transactional
    public void loadTestData() {
        if (detaleRepository.count() > 0) {
            return;
        }
        String[] sandeliuPavadinimai = {"Vilniaus Sandėlis", "Kauno Centras", "Klaipėdos Sandėlis", "Šiaulių Terminalas", "Panevėžio Baze"};
        String[] sandeliuAdresai = {"Vilniaus g. 1", "Kauno g. 5", "Taikos pr. 10", "Tilžės g. 7", "Respublikos g. 3"};
        Sandelys[] sandelysArray = new Sandelys[5];
        for (int i = 0; i < 5; i++) {
            Sandelys sandelys = new Sandelys();
            sandelys.setPavadinimas(sandeliuPavadinimai[i]);
            sandelys.setAdresas(sandeliuAdresai[i]);
            sandelysRepository.save(sandelys);
            sandelysArray[i] = sandelys;
        }
        String[] markes = {"Audi", "BMW", "Volkswagen", "Toyota", "Mercedes-Benz", "Ford", "Honda", "Nissan", "Peugeot", "Volvo"};
        Automobilis[] automobiliaiArray = new Automobilis[10];
        for (int i = 0; i < 10; i++) {
            Automobilis automobilis = new Automobilis();
            automobilis.setVinKodas(generateRandomVin());
            automobilis.setMarke(markes[i]);
            automobilisRepository.save(automobilis);
            automobiliaiArray[i] = automobilis;
        }
        String[] detaliuPavadinimai = {
                "Alyvos filtras", "Oro filtras", "Kuro siurblys", "Stabdžių diskas", "Radiatorius",
                "Akumuliatorius", "Sankaba", "Diržas", "Amortizatorius", "Lemputė",
                "Generatorius", "Starteris", "Vandens pompa", "Žvakių rinkinys", "Stabdžių kaladėlės",
                "Vairo traukė", "Variklio pagalvė", "Turbo", "EGR vožtuvas", "Termostatas"
        };
        for (int i = 0; i < 20; i++) {
            Detale detale = new Detale();
            detale.setPavadinimas(detaliuPavadinimai[i]);
            detale.setKaina(BigDecimal.valueOf(25 + (i * 7))); // Pvz: nuo 25 iki 160
            detale.setKiekis(5L + (i % 10)); // Pvz: 5–14 vnt
            detale.setAutomobilis(automobiliaiArray[i % 10]);
            detale.setSandelys(sandelysArray[i % 5]);
            DetalesTipas[] tipai = DetalesTipas.values();
            DetalesTipas randomTipas = tipai[new Random().nextInt(tipai.length)];
            detale.setTipas(randomTipas);
            detaleRepository.save(detale);
        }
    }
    private static final String VIN_SYMBOLS = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789"; // Be I, O, Q
    private static final Random random = new Random();
    private String generateRandomVin() {
        StringBuilder vin = new StringBuilder();
        for (int i = 0; i < 17; i++) {
            vin.append(VIN_SYMBOLS.charAt(random.nextInt(VIN_SYMBOLS.length())));
        }
        return vin.toString();
    }

    public List<Detale> searchDetales(String adresas, String marke, String vinKodas) {
        List<Detale> list = detaleRepository.findByCustomFilter(vinKodas, marke, adresas);
        System.out.println("──────────────────────────────────────────────────────────────────────────────");
        System.out.printf("| %-3s | %-20s | %-10s | %-6s | %-10s | %-15s |\n",
                "ID", "Pavadinimas", "Kaina", "Kiekis", "Markė", "Sandėlys");
        System.out.println("──────────────────────────────────────────────────────────────────────────────");
        for (Detale d : list) {
            System.out.printf("| %-3d | %-20s | %-10s | %-6d | %-10s | %-15s |\n",
                    d.getId(),
                    d.getPavadinimas(),
                    d.getKaina(),
                    d.getKiekis(),
                    d.getAutomobilis().getMarke(),
                    d.getSandelys().getPavadinimas()
            );
        }
        System.out.println("──────────────────────────────────────────────────────────────────────────────");
        return list;
    }
}



