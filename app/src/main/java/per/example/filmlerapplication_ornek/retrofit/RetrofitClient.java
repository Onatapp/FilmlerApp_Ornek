package per.example.filmlerapplication_ornek.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    /**
     * RETROFIT kurulum aşaması parçası.
     * RetrofitClient: Web servis ile bağlantıyı sağlayan sınıftır.
     * ApiUtils sınıfını oluşturmadan önce bu sınıf oluşturulmalı.
     */
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseURL) {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}