package per.example.filmlerapplication_ornek.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface KategorilerInterface {
    /**
     * Web servis ana linkinin devamının yazıldığı ve web servislerin Retrofit2 ile projeye dahil edildiği interface sınıfıdır.
     * Projeye Retrofit2 kurulum aşamasının adımı.
     */

    @GET("/filmler/tum_kategoriler.php")
    Call<KategoriCevap> tumKategoriler();

    @POST("/filmler/update_kategori.php")
    @FormUrlEncoded
    Call<CrudCevap> kategoriGuncelle(@Field("kategori_id") int kategori_id
            , @Field("kategori_ad") String kategori_ad);
}