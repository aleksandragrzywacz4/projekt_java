
package pl.cinemahub.model;

public class Film {
    private Long id;
    private String tytul;
    private String gatunek;
    private int czasTrwaniaMinuty;
    private boolean aktywny;

    public Film() {
    }

    public Film(Long id, String tytul, String gatunek,
                int czasTrwaniaMinuty, boolean aktywny) {
        this.id = id;
        this.tytul = tytul;
        this.gatunek = gatunek;
        this.czasTrwaniaMinuty = czasTrwaniaMinuty;
        this.aktywny = aktywny;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public String getGatunek() {
        return gatunek;
    }

    public void setGatunek(String gatunek) {
        this.gatunek = gatunek;
    }

    public int getCzasTrwaniaMinuty() {
        return czasTrwaniaMinuty;
    }

    public void setCzasTrwaniaMinuty(int czasTrwaniaMinuty) {
        this.czasTrwaniaMinuty = czasTrwaniaMinuty;
    }

    public boolean isAktywny() {
        return aktywny;
    }

    public void setAktywny(boolean aktywny) {
        this.aktywny = aktywny;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", tytul='" + tytul + '\'' +
                ", gatunek='" + gatunek + '\'' +
                ", czasTrwaniaMinuty=" + czasTrwaniaMinuty +
                ", aktywny=" + aktywny +
                '}';
    }
}
