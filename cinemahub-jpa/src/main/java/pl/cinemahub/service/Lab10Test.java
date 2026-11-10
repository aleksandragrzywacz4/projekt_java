package pl.cinemahub.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Lab10Test implements CommandLineRunner {

    private final FileServerService serverService;
    private final FileClientService clientService;

    public Lab10Test(FileServerService serverService, FileClientService clientService) {
        this.serverService = serverService;
        this.clientService = clientService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("TEST LAB 10");

        new Thread(() -> serverService.startServer()).start();

        Thread.sleep(1000);

        String data = clientService.fetchServerData();

        System.out.println("ODEBRANE I ODSZYFROWANE DANE OD KLIENTA:\n" + data);
        System.out.println("KONIEC TESTU LAB 10");
    }
}