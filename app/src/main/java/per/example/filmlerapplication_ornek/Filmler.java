package per.example.filmlerapplication_ornek;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Filmler implements Serializable {
    @SerializedName("film_id")
    @Expose
    private int film_id;
    @SerializedName("film_ad")
    @Expose
    private String film_ad;
    @SerializedName("film_yil")
    @Expose
    private int film_yil;
    @SerializedName("film_resim")
    @Expose
    private String film_resim;
    @SerializedName("film_aciklama")
    @Expose
    private String film_aciklama;
    @SerializedName("kategoriler")
    @Expose
    private Kategoriler kategori;
    @SerializedName("yonetmenler")
    @Expose
    private Yonetmenler yonetmen;

    public Filmler() {
    }

    public Filmler(int filmId, String filmAd, int filmYil, String filmResim,
                   String filmAciklama, Kategoriler kategori, Yonetmenler yonetmen) {
        super();
        this.film_id = filmId;
        this.film_ad = filmAd;
        this.film_yil = filmYil;
        this.film_resim = filmResim;
        this.film_aciklama = filmAciklama;
        this.kategori = kategori;
        this.yonetmen = yonetmen;
    }

    public int getFilm_id() {
        return film_id;
    }

    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }

    public String getFilm_Ad() {
        return film_ad;
    }

    public void setFilm_Ad(String film_Ad) {
        this.film_ad = film_Ad;
    }

    public int getFilmYil() {
        return film_yil;
    }

    public void setFilmYil(int filmYil) {
        this.film_yil = filmYil;
    }

    public String getFilmResim() {
        return film_resim;
    }

    public void setFilmResim(String filmResim) {
        this.film_resim = filmResim;
    }

    public String getFilmAciklama() {
        return film_aciklama;
    }

    public void setFilmAciklama(String filmAciklama) {
        this.film_aciklama = filmAciklama;
    }

    public Kategoriler getKategori() {
        return kategori;
    }

    public void setKategori(Kategoriler kategori) {
        this.kategori = kategori;
    }

    public Yonetmenler getYonetmen() {
        return yonetmen;
    }

    public void setYonetmen(Yonetmenler yonetmen) {
        this.yonetmen = yonetmen;
    }
}