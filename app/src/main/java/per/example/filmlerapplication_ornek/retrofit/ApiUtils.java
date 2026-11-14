package per.example.filmlerapplication_ornek.retrofit;

public class ApiUtils {
    /**
     * Retrofit kurulum aşaması parçası.
     * ApiUtils, BaseURL ile web servis arasında bağlantıyı sağlayıp KategorilerInterface sınıfının alındığı sınıftır.
     * BaseURL olarak yazılacak link, mutlaka çalışan web servisin ana linki olmalı.
     **/

    public static final String BaseUrl = "https://restfuldb.onatsomer.com";

    public static FilmlerInterface getFilmlerInterface(){

        return RetrofitClient.getClient(BaseUrl).create(FilmlerInterface.class);
    }

    public static KategorilerInterface getKategorilerInterface(){

        return RetrofitClient.getClient(BaseUrl).create(KategorilerInterface.class);
    }
}