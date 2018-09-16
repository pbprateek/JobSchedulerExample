package com.prateek.flickr.flickrphotos.network;

import com.prateek.flickr.flickrphotos.model.FlickrResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FlickrService {


    @GET("services/rest/?method=flickr.photos.getRecent&api_key=6f102c62f41998d151e5a1b48713cf13&format=json&nojsoncallback=1&extras=url_s")
    Call<FlickrResponse> getRecentPhotos();


}
