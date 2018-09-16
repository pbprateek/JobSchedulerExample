package com.prateek.flickr.flickrphotos;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prateek.flickr.flickrphotos.model.Photo;

public class ImageAdapter extends ListAdapter<Photo,ImageViewHolder> {
    protected ImageAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        Photo photo=getItem(position);
        holder.bind(photo);


    }

    private static final DiffUtil.ItemCallback<Photo> DIFF_CALLBACK=new DiffUtil.ItemCallback<Photo>() {
        @Override
        public boolean areItemsTheSame(Photo oldItem, Photo newItem) {
            return (oldItem.getUrlS()).equals(newItem.getUrlS());
        }

        @Override
        public boolean areContentsTheSame(Photo oldItem, Photo newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    };
}
