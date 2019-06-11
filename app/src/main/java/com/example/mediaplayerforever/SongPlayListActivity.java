package com.example.mediaplayerforever;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.mediaplayerforever.adapters.SongAdapter;

import java.util.ArrayList;

public class SongPlayListActivity extends AppCompatActivity {

    RecyclerView recyclerViewPlayList;
    ArrayList<Song> songs = new ArrayList<>();

    private SongAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_play_list);
        recyclerViewPlayList = findViewById(R.id.recyclerViewPlayList);
        recyclerViewPlayList.setLayoutManager(new LinearLayoutManager(this));
        loadSongs();
        adapter = new SongAdapter(songs);
        recyclerViewPlayList.setAdapter(adapter);
        adapter.setOnSongClickListener(new SongAdapter.OnSongClickListener() {
            @Override
            public void onSongClick(int position) {
                Song song = adapter.getSongs().get(position);
                Intent intent = new Intent(SongPlayListActivity.this, MainActivity.class);
                intent.putExtra("id", song.getId());
                intent.putExtra("title", song.getTitle());
                intent.putExtra("artist", song.getArtist());
                startActivity(intent);
            }
        });

    }

    public void loadSongs() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor == null) {
            Toast.makeText(this, "Cursor = null", Toast.LENGTH_SHORT).show(); // query failed, handle error.
        } else if (!cursor.moveToFirst()) {
            Toast.makeText(this, "media not found", Toast.LENGTH_SHORT).show(); // no media on the device
        } else {
            int titleColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST);
            do {
                long thisId = cursor.getLong(idColumn);
                String thisTitle = cursor.getString(titleColumn);
                String thisArtist = cursor.getString(artistColumn);
                songs.add(new Song(thisId, thisTitle, thisArtist));
            } while (cursor.moveToNext());
            cursor.close();
        }
    }
}
