package per.example.filmlerapplication_ornek.retrofit;

import com.google.android.material.listitem.ListItemLayout;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface FilmlerInterface {
    /**
     * Web servis ana linkinin devamının yazıldığı ve web servislerin Retrofit2 ile projeye dahil edildiği interface sınıfıdır.
     * Projeye Retrofit2 kurulum aşamasının adımı.
     */

    @POST("/filmler/filmler_by_kategori_id.php")
    @FormUrlEncoded
    Call<FilmCevap> filmlerByKategoriId(@Field("kategori_id") int kategori_id);
}