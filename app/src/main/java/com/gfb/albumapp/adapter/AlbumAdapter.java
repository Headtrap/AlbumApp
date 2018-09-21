package com.gfb.albumapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gfb.albumapp.R;
import com.gfb.albumapp.entity.Album;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {
    private List<Album> albums;
    private Context context;

    public AlbumAdapter(List<Album> albums, Context context) {
        this.albums = albums;
        this.context = context;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_album, parent, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        Album album = albums.get(position);
        if (album != null) {
            holder.tvTitle.setText(String.valueOf(album.getTitle()));
            Picasso.get().load(album.getThumbnailUrl()).into(holder.imThumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    class AlbumViewHolder extends RecyclerView.ViewHolder {
        private ImageView imThumbnail;
        private TextView tvTitle;

        AlbumViewHolder(View itemView) {
            super(itemView);
            imThumbnail = itemView.findViewById(R.id.imThumbnail);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }
}
