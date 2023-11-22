package com.tanim.arcadehub;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicBoolean;

public class GuessTheNumber extends AppCompatActivity {
    private int maxLimit;
    boolean computerGuessGenerated = false;
    int computerGuess;
    int tries = 0;
    private static final String PREF_VIBRATION_ENABLED = "pref_vibration_enabled";
    private Button vibTog;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_the_number);

        EditText max = findViewById(R.id.maxLimit);
        EditText user = findViewById(R.id.userInput);
        Button check = findViewById(R.id.checkButton);
        TextView res = findViewById(R.id.resultText);

        vibTog = findViewById(R.id.vibToggle);
        preferences = getSharedPreferences(PREF_VIBRATION_ENABLED, Context.MODE_PRIVATE);


        AtomicBoolean isVibrationEnabled = new AtomicBoolean(preferences.getBoolean(PREF_VIBRATION_ENABLED, true));
        vibTog.setText(isVibrationEnabled.get() ? "On" : "Off");
        vibTog.setOnClickListener(view -> {
            isVibrationEnabled.set(!isVibrationEnabled.get()); // Toggle the boolean value

            preferences.edit().putBoolean(PREF_VIBRATION_ENABLED, isVibrationEnabled.get()).apply();
            vibTog.setText(isVibrationEnabled.get() ? "On" : "Off");
        });

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
                        if (isVibrationEnabled.get()) {
                            vibrate();
                        }
                        check.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        res.startAnimation(AnimationUtils.loadAnimation(GuessTheNumber.this, R.anim.shake));
                        if (computerGuess > userInput) {
                            res.setText("Wrong, higher than " + userInput);
                        } else {
                            res.setText("Wrong, lower than " + userInput);
                        }
                        tries++;
                    }
                    user.setText("");
                } catch (NumberFormatException e) {
                    // Handle the case where the input is not a valid integer
                    e.printStackTrace(); // Log the exception for debugging
                }
            } else {
                res.setText("Empty Input");
                res.startAnimation(AnimationUtils.loadAnimation(GuessTheNumber.this, R.anim.shake));
                if (isVibrationEnabled.get()) {
                    vibrate();
                }
            }
        });
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
