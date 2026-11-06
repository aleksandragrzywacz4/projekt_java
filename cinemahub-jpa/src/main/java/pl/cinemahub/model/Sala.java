package pl.cinemahub.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sale")
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nazwa;

    @Column(name = "liczba_miejsc")
    private int liczbaMiejsc;

    private boolean aktywna;

    public Sala() {
    }

    public Sala(Long id, String nazwa, int liczbaMiejsc, boolean aktywna) {
        this.id = id;
        this.nazwa = nazwa;
        this.liczbaMiejsc = liczbaMiejsc;
        this.aktywna = aktywna;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNazwa() { return nazwa; }
    public void setNazwa(String nazwa) { this.nazwa = nazwa; }

    public int getLiczbaMiejsc() { return liczbaMiejsc; }
    public void setLiczbaMiejsc(int liczbaMiejsc) { this.liczbaMiejsc = liczbaMiejsc; }

    public boolean isAktywna() { return aktywna; }
    public void setAktywna(boolean aktywna) { this.aktywna = aktywna; }

    @Override
    public String toString() {
        return "Sala{" +
                "id=" + id +
                ", nazwa='" + nazwa + '\'' +
                ", liczbaMiejsc=" + liczbaMiejsc +
                ", aktywna=" + aktywna +
                '}';
    }
}
