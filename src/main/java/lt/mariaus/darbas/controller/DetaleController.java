package lt.mariaus.darbas.controller;

import lombok.RequiredArgsConstructor;
import lt.mariaus.darbas.converter.DetaleConverter;
import lt.mariaus.darbas.dto.DetaleDTO;
import lt.mariaus.darbas.entity.Detale;
import lt.mariaus.darbas.service.DetaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/detales")
@RequiredArgsConstructor
public class DetaleController {

    private final DetaleService detaleService;
    private final DetaleConverter detaleConverter;

    @GetMapping
    public ResponseEntity<List<DetaleDTO>> getAllDetales() {
        List<DetaleDTO> dtoList = detaleService.getAllDetales()
                .stream()
                .map(detaleConverter::convertToDto)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

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


//    @GetMapping("/search")
//    public ResponseEntity<List<DetaleDTO>> searchDetales(
//            @RequestParam(required = false) String adresas,
//            @RequestParam(required = false) String marke,
//            @RequestParam(required = false) String vinKodas
//    ) {
//        List<Detale> filteredDetales = detaleService.searchDetales(adresas, marke, vinKodas);
//        List<DetaleDTO> dtoList = filteredDetales.stream()
//                .map(detaleConverter::convertToDto)
//                .toList();
//        return ResponseEntity.ok(dtoList);
//    }


}
















