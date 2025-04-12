package lt.mariaus.darbas.controller;

import lt.mariaus.darbas.entity.Automobilis;
import lt.mariaus.darbas.repository.AutomobilisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DetaleController {

    @Autowired
    private AutomobilisRepository automobilisRepository;

    @GetMapping("/automobiliai")
    public List<Automobilis> getAutomobiliai() {
        return automobilisRepository.findAll();
    }
}


















