package com.gfb.albumapp.service;

import com.gfb.albumapp.entity.Album;
import com.gfb.albumapp.service.endpoint.AlbumEndpoint;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public class AlbumService extends Service {

    public static Observable<List<Album>> getAlbums() {
        return createService(AlbumEndpoint.class).getAlbums();
    }

}
