package com.tanim.arcadehub;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicBoolean;

public class GuessTheNumber extends AppCompatActivity {
    private int maxLimit;
    private boolean computerGuessGenerated = false;
    private int computerGuess;
    private int tries = 0;
    private long elapsedTimeInMillis = 0;
    private static final String PREF_VIBRATION_ENABLED = "pref_vibration_enabled";
    private SharedPreferences preferences;
    private TextView guessTimer;
    private CountDownTimer countDownTimer;
    private boolean timerRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_the_number);

        EditText max = findViewById(R.id.maxLimit);
        EditText user = findViewById(R.id.userInput);
        Button check = findViewById(R.id.checkButton);
        Button vibToggle = findViewById(R.id.vibeBT);
        TextView res = findViewById(R.id.resultText);
        guessTimer = findViewById(R.id.guessTimer);

        preferences = getSharedPreferences(PREF_VIBRATION_ENABLED, Context.MODE_PRIVATE);
        AtomicBoolean isVibrationEnabled = new AtomicBoolean(preferences.getBoolean(PREF_VIBRATION_ENABLED, true));

        vibToggle.setBackgroundResource(isVibrationEnabled.get() ? R.drawable.ic_vibrate : R.drawable.ic_not_vibrate);
        vibToggle.setOnClickListener(view -> {
            isVibrationEnabled.set(!isVibrationEnabled.get());
            preferences.edit().putBoolean(PREF_VIBRATION_ENABLED, isVibrationEnabled.get()).apply();
            vibToggle.setBackgroundResource(isVibrationEnabled.get() ? R.drawable.ic_vibrate : R.drawable.ic_not_vibrate);
            Toast.makeText(GuessTheNumber.this, isVibrationEnabled.get() ? "Vibration ON" : "Vibration OFF", Toast.LENGTH_SHORT).show();
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
                        // Start the timer when the computer's guess is generated
                        startTimer();
                    }

                    if (computerGuess == userInput) {
                        check.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));

                        // Stop the timer
                        countDownTimer.cancel();
                        timerRunning = false;

                        // Display result with tries and time
                        res.setText("Correct, you took " + (tries+1) + " tries and " + formatTime(elapsedTimeInMillis));

                        // Reset variables for a new game
                        computerGuessGenerated = false;
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
                res.setText("Enter Between 1 - " + max.getText());
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(100, 255));
            } else {
                // Deprecated in API 26
                vibrator.vibrate(100);
            }
        }
    }
    private void startTimer() {
        // Reset elapsed time
        elapsedTimeInMillis = 0;
        countDownTimer = new CountDownTimer(Long.MAX_VALUE, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                elapsedTimeInMillis += 1000; // Increment elapsed time by 1 second
                guessTimer.setText(formatTime(elapsedTimeInMillis));
            }

            @Override
            public void onFinish() {
                // Handle the timer finish event if needed
            }
        }.start();

        timerRunning = true;
    }
    private void resetTimer() {
        countDownTimer.cancel();
        elapsedTimeInMillis = 0; // Reset elapsed time
        guessTimer.setText(formatTime(elapsedTimeInMillis));
        timerRunning = false;
    }
    private String formatTime(long millis) {
        int minutes = (int) (millis / 1000) / 60;
        int seconds = (int) (millis / 1000) % 60;
        if (minutes == 0) {
            return String.format("%d Second", seconds);
        }
        return String.format("%d Minutes : %d Second", minutes, seconds);
    }
}
