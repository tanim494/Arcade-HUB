package com.tanim.arcadehub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button author = findViewById(R.id.authorBtn);

        author.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AuthorActivity.class);
            startActivity(intent);
        });

        // Set up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.gamesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new GamesAdapter(this, getGameList()));
    }

    private ArrayList<GameModel> getGameList() {
        ArrayList<GameModel> games = new ArrayList<>();
        games.add(new GameModel("Guess The Number", R.drawable.ic_guess_the_number));
        games.add(new GameModel("Tic Tac Toe", R.drawable.ic_tic_tac_toe));
        // Add more games as needed
        return games;
    }
}
