package per.example.filmlerapplication_ornek;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import per.example.filmlerapplication_ornek.databinding.ActivityFilmlerBinding;

public class FilmlerActivity extends AppCompatActivity {

    ArrayList<Filmler> filmlerArrayList;
    FilmlerAdapter adapter;
    Kategoriler kategori;
    private ActivityFilmlerBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityFilmlerBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        // dbc = new DBConnection(this);

        kategori = (Kategoriler) getIntent().getSerializableExtra("kategoriNesne");

        bind.toolbar2.setTitle(kategori.getKategori_Ad() + " Filmleri");
        setSupportActionBar(bind.toolbar2);

        bind.filmlerRv.setHasFixedSize(true);
        bind.filmlerRv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        //-- SQLİTE KULLANIMI YÖNTEMİ: filmlerArrayList = new FilmlerDao().tumFilmlerByKategoriID(dbc, kategori.getKategori_id());

        //** FİLM LİSTESİNİ YÜKLEME
        filmlerArrayList = new ArrayList<>();
        adapter = new FilmlerAdapter(this, filmlerArrayList);
        bind.filmlerRv.setAdapter(adapter);
        filmGetir(kategori.getKategori_id());
    }

    /**
     * Kategori Adapter'dan taşınan kategori_ID'ye göre WEB SERVİS isteğiyle filmleri LİSTELEME. - VOLLEY kullanarak
     * @param kategoriID
     */
    public void filmGetir(int kategoriID) {

        String url = "https://restfuldb.onatsomer.com/filmler";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url + "/filmler_by_kategori_id.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("FİLM JSON YANITI", response);
                filmlerArrayList = new ArrayList<>();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jArray = jsonObject.getJSONArray("filmler");

                    for (int i = 0; i < jArray.length(); i++) {

                        JSONObject flm = jArray.getJSONObject(i);

                        int filmID = flm.getInt("film_id");
                        String filmAd = flm.getString("film_Ad");
                        int filmYil = flm.getInt("film_Yil");
                        String filmResim = flm.getString("film_Resim");
                        String filmAciklama = flm.getString("film_Aciklama");

                        JSONObject ktg = flm.getJSONObject("kategori");
                        int kategoriID = ktg.getInt("kategori_id");
                        String kategoriAd = ktg.getString("kategori_Ad");

                        JSONObject ytm = flm.getJSONObject("yonetmen");
                        int yonetmenID = ytm.getInt("yonetmen_id");
                        String yonetmenAd = ytm.getString("yonetmen_Ad");

                        Yonetmenler yonetmen = new Yonetmenler(yonetmenID, yonetmenAd);
                        Kategoriler kategori = new Kategoriler(kategoriID, kategoriAd);
                        Filmler film = new Filmler(filmID, filmAd, filmYil, filmResim, filmAciklama, kategori, yonetmen);
                        filmlerArrayList.add(film);
                    }

                    adapter = new FilmlerAdapter(FilmlerActivity.this, filmlerArrayList);
                    bind.filmlerRv.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("VERİ YÜKLENEMEDİ!", "#1004 " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("HATA!", error.getMessage());
                Toast.makeText(getApplicationContext(), "Veriler yüklenirken hata oluştu. #1005", Toast.LENGTH_LONG).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("kategori_id", String.valueOf(kategoriID));

                return params;
            }
        };
        Volley.newRequestQueue(FilmlerActivity.this).add(postRequest);
    }

    /**
     * VOLLEY ile kategori GÜNCELLEME işlemi
     * @param kategoriID
     */
    void kategoriGuncelle(int kategoriID) {

        try {

            View v = getLayoutInflater().inflate(R.layout.alert_view_design, null);
            TextInputLayout inputLytKategoriAd = v.findViewById(R.id.inputLytKategoriAd);
            TextInputEditText editTxtKategoriAd = v.findViewById(R.id.editTxtKategoriAd);

            AlertDialog.Builder ad = new AlertDialog.Builder(FilmlerActivity.this);
            ad.setTitle("Kategoriyi Güncelle");
            ad.setView(v);

            ad.setPositiveButton("Kaydet", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    if (editTxtKategoriAd.getText().toString().isEmpty()) {

                        inputLytKategoriAd.setError("Lütfen bu alanı doldurun.");
                    } else {

                        String url = "https://restfuldb.onatsomer.com/filmler";
                        String categoryName = editTxtKategoriAd.getText().toString();

                        //** GÜNCELLEME VOLLEY WEB SERVİS İSTEĞİ
                        StringRequest postReq = new StringRequest(Request.Method.POST, url + "/update_kategori.php", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.e("GELEN CEVAP - UPDATE", response);
                               // startActivity(new Intent(FilmlerActivity.this, MainActivity.class));
                                Toast.makeText(getApplicationContext(), "Kategori başarıyla güncellendi.", Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(getApplicationContext(), "Güncelleme işleminde beklenmedik hata oldu. #1006", Toast.LENGTH_LONG).show();
                            }
                        }) {
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {

                                Map<String, String> params = new HashMap<>();
                                params.put("kategori_id", String.valueOf(kategoriID));
                                params.put("kategori_Ad", categoryName);
                                return params;
                            }
                        };
                        Volley.newRequestQueue(FilmlerActivity.this).add(postReq);
                    }
                }
            });
            ad.setNegativeButton("Vazgeç", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            }).show();
            //ALTERNATİF : ad.create().show();

        } catch (Exception e){

            Log.e("KATEGORİ GÜNCELLERKEN HATA!", e.getMessage().toString());
        }
    }

    /**
     * VOLLEY ile kategori SİLME işlemi - AlertDialog kullanarak
     * @param kategori_ID
     */
    void kategoriSil(int kategori_ID) {

        String url = "https://restfuldb.onatsomer.com/filmler";

        StringRequest req = new StringRequest(Request.Method.POST, url + "/delete_kategori.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("GELEN CEVAP - DELETE", response);

                AlertDialog.Builder ad = new AlertDialog.Builder(FilmlerActivity.this);
                ad.setTitle("Kategoriyi Sil");
                ad.setIcon(R.drawable.roundicon_warning_24);
                ad.setMessage("Kategoriler silinemez. Ancak silme işlemi başarılı çalışmaktadır.");

                /* ad.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                       // startActivity(new Intent(FilmlerActivity.this, MainActivity.class));
                       // Toast.makeText(getApplicationContext(), "İlgili kategori başarıyla silindi.", Toast.LENGTH_LONG).show();
                    }
                }); */
                ad.setNegativeButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                ad.create().show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Silme işleminde beklenmedik hata oldu. #1007", Toast.LENGTH_LONG).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                 Map<String, String> params = new HashMap<>();

                return params;
            }
        };
        Volley.newRequestQueue(FilmlerActivity.this).add(req);
    }

    /**
     * Toolbar üzerine harici MENÜ tasarımı oluşturarak içerisine inflate (import) etme.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_kategori_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Toolbar üzerine yerleştirilen KATEGORİ MENÜ seçeneklerinin tıklanma olayları.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_categoryMultiOpt){

            kategoriGuncelle(kategori.getKategori_id());

        } else if (item.getItemId() == R.id.action_categoryDelete) {

            kategoriSil(kategori.getKategori_id());
        }
        return super.onOptionsItemSelected(item);
    }

    /* ---------- SQLİTE İLE GÜNCELLEME VE SİLME KULLANIMLARI ----------
        btnGuncelle.setOnClickListener(view -> {

            String kategoriAd = editKategoriAd2.getText().toString().trim();
            if (TextUtils.isEmpty(kategoriAd)) {

                txtLytKategoriAd.setError("Lütfen bu alanı doldurun.");
            } else {

                new KategoriDao().kategoriGuncelle(dbc, kategoriAd, kategori.getKategori_id());
                startActivity(new Intent(FilmlerActivity.this, MainActivity.class));
                finish();
            }
        }); */

        /*
        btnSil.setOnClickListener(view -> {

            String kategoriAd = editKategoriAd2.getText().toString().trim();

            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setIcon(R.drawable.roundicon_warning_24);
            ad.setTitle("Kategori Sil");
            ad.setMessage("Seçtiğiniz " + kategori.getKategori_Ad() + " kategorisini silmek istiyor musunuz?\nBu işlem geri alınamaz!");

            ad.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    new KategoriDao().kategoriSil(dbc, kategori.getKategori_id());
                    startActivity(new Intent(FilmlerActivity.this, MainActivity.class));
                    finish();
                }
            });

            ad.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Toast.makeText(FilmlerActivity.this, "İptal edildi. :)", Toast.LENGTH_SHORT).show();
                }
            }).show();
        });  */
}