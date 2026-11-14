package per.example.filmlerapplication_ornek.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import per.example.filmlerapplication_ornek.Yonetmenler;

public class YonetmenCevap {

    @SerializedName("yonetmenler")
    @Expose
    private List<Yonetmenler> yonetmenler;
    @SerializedName("success")
    @Expose
    private Integer success;

    public List<Yonetmenler> getYonetmenler() {
        return yonetmenler;
    }

    public void setYonetmenler(List<Yonetmenler> yonetmenler) {
        this.yonetmenler = yonetmenler;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }
}