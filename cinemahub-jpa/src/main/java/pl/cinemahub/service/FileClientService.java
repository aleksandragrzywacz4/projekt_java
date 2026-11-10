package pl.cinemahub.service;

import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

@Service
public class FileClientService {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int PORT = 5000;
    private final Vigenere cipher = new Vigenere();

    public String fetchServerData() {
        System.out.println("Łączenie z serwerem pod adresem " + SERVER_IP + ":" + PORT + "...");
        StringBuilder response = new StringBuilder();

        try (Socket socket = new Socket(SERVER_IP, PORT);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Połączono z serwerem pomyślnie");
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line).append("\n");
            }
            System.out.println("Pobrano dane z serwera");

        } catch (IOException e) {
            System.err.println("Błąd połączenia z serwerem: " + e.getMessage());
            return "Błąd pobierania danych";
        }

        String encryptedText = response.toString().trim();
        return cipher.decrypt(encryptedText);
    }

}
