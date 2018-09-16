package com.prateek.flickr.flickrphotos;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.prateek.flickr.flickrphotos.model.Photo;
import com.squareup.picasso.Picasso;

public class ImageViewHolder extends RecyclerView.ViewHolder {
    private ImageView imageView;
    public ImageViewHolder(View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.image);
    }
    public void bind(Photo photo){

        Picasso.get()
                .load(photo.getUrlS())
                .resize(500,500)
                .centerCrop()
                .into(imageView);

    }
}
