package pl.cinemahub.service;

import org.springframework.stereotype.Service;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileServerService {
    private static final int PORT = 5000;
    private static final String DATA_FILE_PATH = "server_data.txt";

    public void startServer() {
        System.out.println("Uruchamianie serwera plików na porcie " + PORT);
        createSampleFileIfNotExists();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    System.out.println("Klient połączył się: " + clientSocket.getInetAddress());
                    String fileContent = Files.readString(Path.of(DATA_FILE_PATH));
                    writer.println(fileContent);
                    System.out.println("Dane zostały przesłane do klienta");

                } catch (IOException e) {
                    System.err.println("Błąd obsługi klienta: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Serwer nie mógł wystartować: " + e.getMessage());
        }
    }

    private void createSampleFileIfNotExists() {
        Path path = Path.of(DATA_FILE_PATH);
        if (!Files.exists(path)) {
            try {
                Files.writeString(path, "Film 1: Incepcja\nFilm 2: Interstellar");
                System.out.println("Utworzono plik danych serwera.");
            } catch (IOException e) {
                System.err.println("Błąd tworzenia pliku: " + e.getMessage());
            }
        }
    }
}
