package per.example.filmlerapplication_ornek;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import per.example.filmlerapplication_ornek.databinding.ActivityFilmDetayBinding;

public class FilmDetayActivity extends AppCompatActivity {
    private Filmler film;
    private ActivityFilmDetayBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityFilmDetayBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());


        film = (Filmler) getIntent().getSerializableExtra("filmBilgi");

        bind.txtFilmAd.setText(film.getFilm_Ad());
        bind.txtFilmYil.setText(String.valueOf(film.getFilmYil()));

        if (film.getYonetmen() != null) {
            bind.txtFilmYonetmen.setText("Yönetmen: " + film.getYonetmen().getYonetmen_ad());
        } else {
            bind.txtFilmYonetmen.setText("Yönetmen: Bilinmiyor!");
        }

        //* Picasso kütüphanesiyle dinamik şekilde online veritabanından resim yükleme yöntemi.
        String url = "https://restfuldb.onatsomer.com/webservices-site/images/" + film.getFilmResim();
        Picasso.get().load(url).resize(800, 1080).into(bind.imgDetayResim);

        if (TextUtils.isEmpty(bind.txtFilmAciklama.toString())) {
            bind.txtFilmAciklama.setText("Film Açıklaması Bulunamadı.");
        } else {
            bind.txtFilmAciklama.setText(film.getFilmAciklama());
        }
    }
}

// imgFilmResim.setImageResource(getResources().getIdentifier(film.getFilm_Resim(), "drawable", getPackageName()));