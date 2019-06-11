package com.example.mediaplayerforever;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Song> songsPlay = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id") && intent.hasExtra("title") && intent.hasExtra("artist")) {
            long id = intent.getLongExtra("id", -1);
            String title = intent.getStringExtra("title");
            String artist = intent.getStringExtra("artist");
            songsPlay.add(new Song(id, title, artist));
        }
    }

    public void onClickButton(View view) {
        Intent intent = new Intent(MainActivity.this, SongPlayListActivity.class);
        startActivity(intent);
    }
}
