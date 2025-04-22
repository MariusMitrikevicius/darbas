package lt.mariaus.darbas.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lt.mariaus.darbas.ApiResponse;
import lt.mariaus.darbas.converter.DetaleConverter;
import lt.mariaus.darbas.dto.DetaleDTO;
import lt.mariaus.darbas.entity.Automobilis;
import lt.mariaus.darbas.entity.Detale;
import lt.mariaus.darbas.entity.Sandelys;
import lt.mariaus.darbas.exception.NotFoundException;
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
    public ResponseEntity<ApiResponse<DetaleDTO>> getDetaleById(@PathVariable Long id) {
        Detale detale = detaleService.getDetaleById(id);
        if (detale != null) {
            DetaleDTO dto = detaleConverter.convertToDto(detale);
            ApiResponse<DetaleDTO> response = new ApiResponse<>(true, "Detale rastas", dto);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<DetaleDTO> response = new ApiResponse<>(false, "Detale nerasta", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<DetaleDTO>>> searchDetales(
            @RequestParam(required = false) String adresas,
            @RequestParam(required = false) String marke,
            @RequestParam(required = false) String vinKodas
    ) {
        List<Detale> filteredDetales = detaleService.searchDetales(adresas, marke, vinKodas);
        List<DetaleDTO> dtoList = filteredDetales.stream()
                .map(detaleConverter::convertToDto)
                .toList();
        String message = dtoList.isEmpty() ?
                "Pagal pateiktus filtrus detalės nerastos" :
                "Filtruotos detalės sėkmingai gautos";
        return ResponseEntity.ok(new ApiResponse<>(true, message, dtoList));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DetaleDTO>> createDetale(@RequestBody DetaleDTO detaleDTO) {
        try {
            Automobilis automobilis = automobilisRepository.findById(detaleDTO.getAutomobilisId())
                    .orElseThrow(() -> new NotFoundException("Automobilis nerastas"));

            Sandelys sandelys = sandelysRepository.findById(detaleDTO.getSandelysId())
                    .orElseThrow(() -> new NotFoundException("Sandėlys nerastas"));

            Detale detale = detaleConverter.convertToEntity(detaleDTO, automobilis, sandelys);
            detale = detaleRepository.save(detale);

            DetaleDTO createdDto = detaleConverter.convertToDto(detale);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Detalė sėkmingai sukurta", createdDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Klaida kuriant detalę: " + e.getMessage(), null));
        }
    }

    @PostMapping("/tuščia")
    public ResponseEntity<ApiResponse<DetaleDTO>> createDetaleTuščiojeLenteleje(@RequestBody DetaleDTO detaleDTO) {
        try {
            // Užtikrinam, kad būtų bent vienas automobilis
            Automobilis automobilis = automobilisRepository.findAll().stream().findFirst().orElseGet(() -> {
                Automobilis a = new Automobilis();
                a.setMarke("Nenumatyta");
                a.setVinKodas("DEFAULTVIN");
                return automobilisRepository.save(a);
            });

            // Užtikrinam, kad būtų bent vienas sandėlys
            Sandelys sandelys = sandelysRepository.findAll().stream().findFirst().orElseGet(() -> {
                Sandelys s = new Sandelys();
                s.setPavadinimas("Nenumatytas Sandėlys");
                s.setAdresas("Nenurodytas Adresas");
                return sandelysRepository.save(s);
            });

            // Konvertuojam, saugom, grąžinam
            Detale detale = detaleConverter.convertToEntity(detaleDTO, automobilis, sandelys);
            detale = detaleRepository.save(detale);
            DetaleDTO responseDto = detaleConverter.convertToDto(detale);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Detalė sėkmingai sukurta tuščioje lentelėje", responseDto));
        } catch (Exception e) {
            e.printStackTrace(); // ← pridėk šitą kad matytum klaidą
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Įvyko klaida: " + e.getMessage(), null));
        }
    }








    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DetaleDTO>> updateDetale(@PathVariable Long id, @RequestBody DetaleDTO detaleDTO) {
        try {
            Detale updatedDetale = detaleService.updateDetale(id, detaleDTO);
            DetaleDTO updatedDetaleDTO = detaleConverter.convertToDto(updatedDetale);
            return ResponseEntity.ok(new ApiResponse<>(true, "Detalė sėkmingai atnaujinta", updatedDetaleDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Klaida atnaujinant: " + e.getMessage(), null));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<DetaleDTO>> patchDetale(@PathVariable Long id, @RequestBody DetaleDTO detaleDTO) {
        try {
            Detale detale = detaleService.updateDetale(id, detaleDTO);
            DetaleDTO response = detaleConverter.convertToDto(detale);
            return ResponseEntity.ok(new ApiResponse<>(true, "Detalė sėkmingai atnaujinta (PATCH)", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Klaida PATCH metu: " + e.getMessage(), null));
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDetale(@PathVariable Long id) {
        detaleService.deleteDetale(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Detalė sėkmingai ištrinta", null));
    }
}


















