
package com.prateek.flickr.flickrphotos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FlickrResponse {

    @SerializedName("photos")
    @Expose
    public Photos photos;
    @SerializedName("stat")
    @Expose
    public String stat;

    public Photos getPhotos() {
        return photos;
    }
}
