-- Filmy

INSERT INTO filmy (tytul, gatunek, czas_trwania_minuty, aktywny)
SELECT 'Gladiator II',
       'Akcja',
       148,
       TRUE WHERE NOT EXISTS (SELECT 1 FROM filmy WHERE tytul = 'Gladiator II');

INSERT INTO filmy (tytul, gatunek, czas_trwania_minuty, aktywny)
SELECT 'Dune: Czesc druga',
       'Sci-Fi',
       166,
       TRUE WHERE NOT EXISTS (SELECT 1 FROM filmy WHERE tytul = 'Dune: Czesc druga');

INSERT INTO filmy (tytul, gatunek, czas_trwania_minuty, aktywny)
SELECT 'Joker: Folie a Deux',
       'Dramat',
       138,
       TRUE WHERE NOT EXISTS (SELECT 1 FROM filmy WHERE tytul = 'Joker: Folie a Deux');

INSERT INTO filmy (tytul, gatunek, czas_trwania_minuty, aktywny)
SELECT 'Inside Out 2',
       'Animacja',
       96,
       TRUE WHERE NOT EXISTS (SELECT 1 FROM filmy WHERE tytul = 'Inside Out 2');

-- Sale

INSERT INTO sale (nazwa, liczba_miejsc, aktywna)
SELECT 'Sala IMAX',
       120,
       TRUE WHERE NOT EXISTS (SELECT 1 FROM sale WHERE nazwa = 'Sala IMAX');

INSERT INTO sale (nazwa, liczba_miejsc, aktywna)
SELECT 'Sala VIP',
       40,
       TRUE WHERE NOT EXISTS (SELECT 1 FROM sale WHERE nazwa = 'Sala VIP');

INSERT INTO sale (nazwa, liczba_miejsc, aktywna)
SELECT 'Sala Standard',
       80,
       TRUE WHERE NOT EXISTS (SELECT 1 FROM sale WHERE nazwa = 'Sala Standard');
