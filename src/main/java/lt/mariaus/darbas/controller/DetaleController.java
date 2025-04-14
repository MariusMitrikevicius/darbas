package lt.mariaus.darbas.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lt.mariaus.darbas.converter.DetaleConverter;
import lt.mariaus.darbas.dto.DetaleDTO;
import lt.mariaus.darbas.entity.Automobilis;
import lt.mariaus.darbas.entity.Detale;
import lt.mariaus.darbas.entity.Sandelys;
import lt.mariaus.darbas.repository.AutomobilisRepository;
import lt.mariaus.darbas.repository.DetaleRepository;
import lt.mariaus.darbas.repository.SandelysRepository;
import lt.mariaus.darbas.service.DetaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/detales")
@RequiredArgsConstructor
public class DetaleController {
    @Autowired
    private final DetaleService detaleService;
    private final DetaleConverter detaleConverter;
    private final AutomobilisRepository automobilisRepository;
    private final SandelysRepository sandelysRepository;
    private final DetaleRepository detaleRepository;

    @GetMapping("/{id}")
    public ResponseEntity<DetaleDTO> getDetaleById(@PathVariable Long id) {
        Detale detale = detaleService.getDetaleById(id);
        if (detale != null) {
            DetaleDTO dto = detaleConverter.convertToDto(detale);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<DetaleDTO>> searchDetales(
            @RequestParam(required = false) String adresas,
            @RequestParam(required = false) String marke,
            @RequestParam(required = false) String vinKodas
    ) {
        List<Detale> filteredDetales = detaleService.searchDetales(adresas, marke, vinKodas);
        List<DetaleDTO> dtoList = filteredDetales.stream()
                .map(detaleConverter::convertToDto)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping
    public ResponseEntity<DetaleDTO> createDetale(@RequestBody DetaleDTO detaleDTO) {
        Automobilis automobilis = automobilisRepository.findById(detaleDTO.getAutomobilisId())
                .orElseThrow(() -> new RuntimeException("Automobilis nerastas"));

        Sandelys sandelys = sandelysRepository.findById(detaleDTO.getSandelysId())
                .orElseThrow(() -> new RuntimeException("Sandėlys nerastas"));

        Detale detale = detaleConverter.convertToEntity(detaleDTO, automobilis, sandelys);
        detale = detaleRepository.save(detale);

        DetaleDTO createdDto = detaleConverter.convertToDto(detale);
        return ResponseEntity.ok(createdDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetaleDTO> updateDetale(@PathVariable Long id, @RequestBody DetaleDTO detaleDTO) {
        Detale updatedDetale = detaleService.updateDetale(id, detaleDTO);
        DetaleDTO updatedDetaleDTO = detaleConverter.convertToDto(updatedDetale);
        return ResponseEntity.ok(updatedDetaleDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DetaleDTO> patchDetale(@PathVariable Long id, @RequestBody DetaleDTO detaleDTO) {
        Detale detale = detaleService.updateDetale(id, detaleDTO);
        DetaleDTO response = detaleConverter.convertToDto(detale);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteDetale(@PathVariable Long id) {
        try {
            if (id == null) {
                throw new IllegalArgumentException("ID negali būti null");
            }
            detaleService.deleteDetale(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace(); // laikinai, kad pamatytum klaidą
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}


















