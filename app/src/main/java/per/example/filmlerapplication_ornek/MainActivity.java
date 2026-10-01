package per.example.filmlerapplication_ornek;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import per.example.filmlerapplication_ornek.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Kategoriler> kategorilerArrayList;
    private ActivityMainBinding bind;
    private KategoriAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        setSupportActionBar(bind.toolbar);
        // dbc = new DBConnection(this);


        bind.kategoriRv.setHasFixedSize(true);
        bind.kategoriRv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));

        //** KATEGORİ LİSTESİNİ YÜKLEME
        kategorilerArrayList = new ArrayList<>();
        adapter = new KategoriAdapter(this, kategorilerArrayList);
        bind.kategoriRv.setAdapter(adapter);
        kategoriListele();
    }

    /**
     * WEB SERVİS isteğiyle KATEGORİLERİ LİSTELEME - VOLLEY kullanarak
     */
    public void kategoriListele() {

        String url = "https://restfuldb.onatsomer.com/filmler";

        StringRequest request = new StringRequest(Request.Method.GET, url + "/tum_kategoriler.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("KATEGORİ JSON YANITI", response);
                kategorilerArrayList = new ArrayList<>();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray kategoriler = jsonObject.getJSONArray("kategoriler");

                    for (int i = 0; i < kategoriler.length(); i++) {

                        JSONObject k = kategoriler.getJSONObject(i);

                        int kategori_ID = k.getInt("kategori_id");
                        String kategori_Ad = k.getString("kategori_Ad");

                        Kategoriler kategori = new Kategoriler(kategori_ID, kategori_Ad);
                        kategorilerArrayList.add(kategori);
                    }

                    adapter = new KategoriAdapter(MainActivity.this, kategorilerArrayList);
                    bind.kategoriRv.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("VERİ YÜKLENEMEDİ!", "#1001 " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("DİKKAT HATA!!", error.getMessage());
                Toast.makeText(MainActivity.this, "Veriler yüklenirken hata oluştu. #1002", Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(MainActivity.this).add(request);
    }

    /**
     * WEB SERVİS isteğiyle KATEGORİ KAYDETME - VOLLEY ve ALERT DİALOG kullanarak
     */
    void kategoriEkleVolley() {

        String url = "https://restfuldb.onatsomer.com/filmler";

        View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.alert_view_design, null);
        TextInputEditText kategoriAdTxt = v.findViewById(R.id.editTxtKategoriAd);
        TextInputLayout kategoriAdTxtLayout = v.findViewById(R.id.inputLytKategoriAd);

        AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
        ad.setView(v);
        ad.setIcon(R.drawable.rounded_add_icon24);
        ad.setTitle("Yeni Kategori Ekle");

        ad.setPositiveButton("Kaydet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String categoryName = kategoriAdTxt.getText().toString().trim();

                StringRequest saveRequest = new StringRequest(Request.Method.POST, url + "/insert_kategori.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("KAYDETME CEVABI", response);
                        Toast.makeText(getApplicationContext(), "Kategori başarıyla kaydedildi.", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("KAYDEDERKEN HATA", error.toString());
                        Toast.makeText(getApplicationContext(), "Kaydetme işleminde beklenmedik hata oldu. #1003", Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<>();
                        params.put("kategori_Ad", categoryName);
                        return params;
                    }
                };
                Volley.newRequestQueue(MainActivity.this).add(saveRequest);
            }
        });

        ad.setNegativeButton("Vazgeç", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).create().show();
    }

    /**
     * Toolbar üzerine harici MENÜ tasarımı oluşturarak içerisine inflate (import) etme.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Toolbar üzerine yerleştirilen KATEGORİ EKLEME seçeneğinin tıklanma olayı.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_categoryMultiOpt) {

            kategoriEkleVolley();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Eski yöntem olarak SQLİTE ile hazır DB'yi kopyalarak alternatif kullanım.
     */
    public void veritabaniKopyala() {

        DatabaseCopyHelper helper = new DatabaseCopyHelper(this);

        try {
            helper.createDataBase();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        helper.openDataBase();
    }
}

/* -------------- SQLITE DB kullanarak kategori KAYDETME işlemi yapılıyor. ---------------

        if (item.getItemId() == R.id.action_kategoriEkle) {
            View v = LayoutInflater.from(this).inflate(R.layout.alert_view_design, null);

            TextInputLayout inputLytKategoriAd = v.findViewById(R.id.inputLytKategoriAd);
            TextInputEditText editTxtKategoriAd = v.findViewById(R.id.editTxtKategoriAd);

            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setTitle("Yeni Kategori Ekle");
            ad.setView(v);

            ad.setPositiveButton("Kaydet", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String kategoriad = editTxtKategoriAd.getText().toString().trim();

                    if (TextUtils.isEmpty(kategoriad)) {

                        Toast.makeText(MainActivity.this, "Lütfen bu alanı doldurun.", Toast.LENGTH_LONG).show();
                    } else {

                        new KategoriDao().kategoriEkle(dbc, kategoriad);
                        Toast.makeText(getApplicationContext(), "Kategori başarıyla kaydedildi.", Toast.LENGTH_LONG).show();
                    }
                }
            }).show();
        } */