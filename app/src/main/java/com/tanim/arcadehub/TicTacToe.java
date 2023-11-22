package com.tanim.arcadehub;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TicTacToe extends AppCompatActivity {

    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;
    TextView resText;
    Button again;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        resText = findViewById(R.id.ticResText);
        again = findViewById(R.id.ticAgainBt);

        again.setOnClickListener(v -> {
            // Reset the game
            resetGame();
            // Hide the "Play Again" button
            again.setVisibility(View.GONE);
        });

        // Initialize buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(v -> onGridButtonClick((Button) v));
            }
        }
    }

    private void onGridButtonClick(Button button) {
        if (button.getText().toString().equals("")) {
            if (player1Turn) {
                button.setText("X");
            } else {
                button.setText("O");
            }
            if (checkForWin()) {
                if (player1Turn) {
                    resText.setText("Player 1 wins!");
                    vibrate();
                    resText.startAnimation(AnimationUtils.loadAnimation(TicTacToe.this, R.anim.shake));
                    again.setVisibility(View.VISIBLE);
                } else {
                    resText.setText("Player 2 wins!");
                    vibrate();
                    resText.startAnimation(AnimationUtils.loadAnimation(TicTacToe.this, R.anim.shake));
                    again.setVisibility(View.VISIBLE);
                }
            } else if (checkForDraw()) {
                resText.setText("It's a draw");
                resText.startAnimation(AnimationUtils.loadAnimation(TicTacToe.this, R.anim.shake));
                vibrate();
                again.setVisibility(View.VISIBLE);
            } else {
                player1Turn = !player1Turn;
            }
        }
    }

    private boolean checkForWin() {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            if (checkRowCol(buttons[i][0], buttons[i][1], buttons[i][2]) ||
                    checkRowCol(buttons[0][i], buttons[1][i], buttons[2][i])) {
                return true;
            }
        }

        return checkRowCol(buttons[0][0], buttons[1][1], buttons[2][2]) ||
                checkRowCol(buttons[0][2], buttons[1][1], buttons[2][0]);
    }

    private boolean checkRowCol(Button b1, Button b2, Button b3) {
        return b1.getText().equals(b2.getText()) &&
                b2.getText().equals(b3.getText()) &&
                !b1.getText().toString().equals("");
    }

    private boolean checkForDraw() {
        // Check if all buttons are filled
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().toString().equals("")) {
                    return false; // There's an empty cell, the game is not a draw yet
                }
            }
        }
        return true; // All cells are filled, the game is a draw
    }
    private void resetGame() {
        // Clear the text on all buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        // Reset other game-related variables
        player1Turn = true;
        resText.setText(""); // Clear the result text
    }
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Check if the device has a vibrator
        if (vibrator != null && vibrator.hasVibrator()) {
            // Vibrate for 300 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(100, 200));
            } else {
                // Deprecated in API 26
                vibrator.vibrate(100);
            }
        }
    }
}
