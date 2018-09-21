package com.gfb.albumapp.service.endpoint;

import com.gfb.albumapp.entity.Album;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface AlbumEndpoint {

    @GET("photos")
    Observable<List<Album>> getAlbums();
}
