
CREATE TABLE IF NOT EXISTS filmy (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     tytul VARCHAR(150) NOT NULL,
    gatunek VARCHAR(60) NOT NULL,
    czas_trwania_minuty INT NOT NULL,
    aktywny BOOLEAN NOT NULL DEFAULT TRUE
    );

CREATE TABLE IF NOT EXISTS sale (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    nazwa VARCHAR(80) NOT NULL,
    liczba_miejsc INT NOT NULL,
    aktywna BOOLEAN NOT NULL DEFAULT TRUE
    );
