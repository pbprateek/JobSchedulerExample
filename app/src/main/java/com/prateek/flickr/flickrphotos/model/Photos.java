
package com.prateek.flickr.flickrphotos.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photos {

    @SerializedName("page")
    @Expose
    public Integer page;
    @SerializedName("pages")
    @Expose
    public Integer pages;
    @SerializedName("perpage")
    @Expose
    public Integer perpage;
    @SerializedName("total")
    @Expose
    public Integer total;
    @SerializedName("photo")
    @Expose
    public List<Photo> photo = null;

    public Integer getPage() {
        return page;
    }

    public Integer getPages() {
        return pages;
    }

    public Integer getPerpage() {
        return perpage;
    }

    public Integer getTotal() {
        return total;
    }

    public List<Photo> getPhoto() {
        return photo;
    }
}
