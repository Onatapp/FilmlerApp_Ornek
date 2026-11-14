package per.example.filmlerapplication_ornek;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FilmlerAdapter extends RecyclerView.Adapter<FilmlerAdapter.filmCardTasarimTutucu> {
    private Context context;
    private List<Filmler> filmlerList;

    public FilmlerAdapter(Context context, List<Filmler> filmlerList) {
        this.context = context;
        this.filmlerList = filmlerList;
    }

    public class filmCardTasarimTutucu extends RecyclerView.ViewHolder {
        CardView cardView_Film;
        ImageView imgFilmResim, btn_CrudMenu;
        TextView txtFilmBaslik;

        public filmCardTasarimTutucu(@NonNull View itemView) {
            super(itemView);
            cardView_Film = itemView.findViewById(R.id.cardView_Film);
            imgFilmResim = itemView.findViewById(R.id.imgFilmResim);
            txtFilmBaslik = itemView.findViewById(R.id.txtFilmBaslik);
            btn_CrudMenu = itemView.findViewById(R.id.btn_CrudMenu);
        }
    }

    @NonNull
    @Override
    public filmCardTasarimTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.filmler_card, parent, false);
        return new filmCardTasarimTutucu(v);
    }

    @Override
    public void onBindViewHolder(@NonNull filmCardTasarimTutucu holder, int position) {

        final Filmler film = filmlerList.get(position);
        holder.txtFilmBaslik.setText(film.getFilm_Ad());

        //** Statik olarak veritabanından uygulamaya resim yüklemek için bir yöntem.
        // holder.imgFilmResim.setImageResource(context.getResources().getIdentifier(film.getFilm_Resim(), "drawable", context.getPackageName()));

        //** Picasso kütüphanesiyle dinamik olarak online veritabanından resim yükleme yöntemi.
        String url = "https://restfuldb.onatsomer.com/webservices-site/images/" + film.getFilmResim();
        Picasso.get().load(url).resize(800, 1080).into(holder.imgFilmResim);

        holder.cardView_Film.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("DEBUG ERROR 1", "Yönetmen boş mu? - " + (film.getYonetmen() == null));
                Log.e("DEBUG ERROR 2", "Kategori boş mu? - " + (film.getKategori() == null));

                Intent intent = new Intent(context, FilmDetayActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("filmBilgi", film);
                context.startActivity(intent);
            }
        });

        /**
         * Film güncelleme ve silme için Popup Menu - ÖRNEK ŞABLON OLARAK
         */
        /* holder.btn_CrudMenu.setOnClickListener(view -> {

            PopupMenu popupMenu = new PopupMenu(context, holder.btn_CrudMenu);
            popupMenu.getMenuInflater().inflate(R.menu.crud_popup_menu, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    if (item.getItemId() == R.id.action_movieUpdate){

                    } else if (item.getItemId() == R.id.action_movieDelete) {

                    }
                    return true;
                }
            });
            popupMenu.show();
        });
        **/
    }

    @Override
    public int getItemCount() {
        return filmlerList.size();
    }
}