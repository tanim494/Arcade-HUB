package com.tanim.arcadehub;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GuessTheNumber extends AppCompatActivity {
    private int maxLimit;
    boolean computerGuessGenerated = false;
    int computerGuess;
    int tries = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_the_number);

        EditText max = findViewById(R.id.maxLimit);
        EditText user = findViewById(R.id.userInput);
        Button check = findViewById(R.id.checkButton);
        TextView res = findViewById(R.id.resultText);

        check.setOnClickListener(view -> {
            String userInputString = user.getText().toString().trim(); // Trim to remove leading/trailing whitespaces

            if (!userInputString.isEmpty()) {
                try {
                    int userInput = Integer.parseInt(userInputString);

                    if (!computerGuessGenerated) {
                        // Generate the initial computerGuess value only once
                        maxLimit = Integer.parseInt(max.getText().toString());
                        computerGuess = (int) ((Math.random() * maxLimit) + 1);
                        computerGuessGenerated = true;
                    }

                    if (computerGuess == userInput) {
                        check.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                        res.setText("Correct, you took " + tries + " tries.");
                        computerGuess = (int) ((Math.random() * maxLimit) + 1);
                        tries = 0;
                    } else {
                        check.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        if (computerGuess > userInput) {
                            res.setText("Wrong, Number is higher");
                        } else {
                            res.setText("Wrong, Number is lower");
                        }
                        tries++;
                    }
                    user.setText("");
                } catch (NumberFormatException e) {
                    // Handle the case where the input is not a valid integer
                    e.printStackTrace(); // Log the exception for debugging
                }
            } else {
                Toast.makeText(GuessTheNumber.this, "Empty Input", Toast.LENGTH_SHORT).show();
            }
        });


    }
}