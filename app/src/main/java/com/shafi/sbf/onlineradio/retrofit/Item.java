
package com.shafi.sbf.onlineradio.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("alt_url")
    @Expose
    private String altUrl;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("alt_image")
    @Expose
    private String altImage;
    @SerializedName("apk")
    @Expose
    private String apk;
    @SerializedName("yitid")
    @Expose
    private String yitid;
    @SerializedName("id")
    @Expose
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAltUrl() {
        return altUrl;
    }

    public void setAltUrl(String altUrl) {
        this.altUrl = altUrl;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAltImage() {
        return altImage;
    }

    public void setAltImage(String altImage) {
        this.altImage = altImage;
    }

    public String getApk() {
        return apk;
    }

    public void setApk(String apk) {
        this.apk = apk;
    }

    public String getYitid() {
        return yitid;
    }

    public void setYitid(String yitid) {
        this.yitid = yitid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
