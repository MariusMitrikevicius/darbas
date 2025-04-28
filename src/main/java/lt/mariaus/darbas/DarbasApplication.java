package lt.mariaus.darbas;

import lt.mariaus.darbas.service.DetaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DarbasApplication implements CommandLineRunner {
    @Autowired
    private DetaleService dataService;

    public static void main(String[] args) {
        SpringApplication.run(DarbasApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        dataService.loadTestData();
    }
}
