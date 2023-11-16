package com.tanim.arcadehub;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.tanim.arcadehub.MainActivity;

public class LoadingScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        // Delay for 3 seconds and then start MainActivity
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(LoadingScreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 3000);
    }
}
