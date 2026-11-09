package pl.cinemahub.service;
import org.springframework.stereotype.Service;

@Service
public class Vigenere {
    private static final String KEY = "CINEMA";

    public String encrypt(String text) {
        StringBuilder result = new StringBuilder();
        text = text.toUpperCase();
        int keyLength = KEY.length();

        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                result.append((char) ((c + KEY.charAt(j) - 2 * 'A') % 26 + 'A'));
                j = (j + 1) % keyLength;
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    public String decrypt(String text) {
        StringBuilder result = new StringBuilder();
        text = text.toUpperCase();
        int keyLength = KEY.length();

        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                result.append((char) ((c - KEY.charAt(j) + 26) % 26 + 'A'));
                j = (j + 1) % keyLength;
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
