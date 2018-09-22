package com.gfb.albumapp.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gfb.albumapp.R;
import com.gfb.albumapp.adapter.AlbumAdapter;
import com.gfb.albumapp.entity.Album;
import com.gfb.albumapp.service.AlbumService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AlbumsActivity extends BaseActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);
        setupToolbar();

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        getAlbums();
    }

    private void getAlbums() {
        Observable<List<Album>> observable = AlbumService.getAlbums();
        observable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Album>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Album> albums) {
                        AlbumAdapter adapter = new AlbumAdapter(albums, AlbumsActivity.this);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        handleException(e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
