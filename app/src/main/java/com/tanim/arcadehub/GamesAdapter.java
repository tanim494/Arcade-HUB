package com.tanim.arcadehub;// GamesAdapter.java

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.GameViewHolder> {

    private Context context;
    private ArrayList<GameModel> gameList;

    public GamesAdapter(Context context, ArrayList<GameModel> gameList) {
        this.context = context;
        this.gameList = gameList;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_item, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, final int position) {
        final GameModel game = gameList.get(position);

        // Set game icon/image
        holder.gameIcon.setImageResource(game.getIconResourceId());

        // Set game name
        holder.gameName.setText(game.getName());

        // Handle item click
        holder.itemView.setOnClickListener(view -> openGameActivity(game.getName()));

    }

    private void openGameActivity(String gameName) {
        switch (gameName) {
            case "Tic Tac Toe" :
                Intent in1 = new Intent(context, TicTacToe.class);
                context.startActivity(in1);
                Toast.makeText(context,"Tic Tac Toe", Toast.LENGTH_SHORT).show();
                break;
            case "Guess The Number" :
                Intent in2 = new Intent(context, GuessTheNumber.class);
                context.startActivity(in2);
                Toast.makeText(context,"Guess The Number", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public int getItemCount() {
        return gameList.size();
    }

    static class GameViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView gameIcon;
        TextView gameName;

        GameViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            gameIcon = itemView.findViewById(R.id.gameIcon);
            gameName = itemView.findViewById(R.id.gameName);
        }
    }
}
