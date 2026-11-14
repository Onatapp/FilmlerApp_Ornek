package per.example.filmlerapplication_ornek.retrofit;

import java.util.List;
import javax.annotation.processing.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import per.example.filmlerapplication_ornek.Kategoriler;
public class KategoriCevap {

    @SerializedName("kategoriler")
    @Expose
    private List<Kategoriler> kategoriler;

    @SerializedName("success")
    @Expose
    private Integer success;

    public List<Kategoriler> getKategoriler() {
        return kategoriler;
    }

    public void setKategoriler(List<Kategoriler> kategoriler) {
        this.kategoriler = kategoriler;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }
}