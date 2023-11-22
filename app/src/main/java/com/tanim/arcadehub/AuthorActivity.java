package com.tanim.arcadehub;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AuthorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);

        LinearLayout githubButton = findViewById(R.id.githubButton);
        githubButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/tanim494"));
            startActivity(intent);
        });

        LinearLayout facebookButton = findViewById(R.id.facebookButton);
        facebookButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.facebook.com/tanim494"));
            startActivity(intent);
        });

        LinearLayout websiteButton = findViewById(R.id.websiteButton);
        websiteButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://tanim.codes"));
            startActivity(intent);
        });

        LinearLayout authorAddress = findViewById(R.id.authorAddress);
        authorAddress.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.app.goo.gl/KimSYJ3GMz9F6QfMA"));
            startActivity(intent);
        });

        LinearLayout authorMail = findViewById(R.id.authorMail);
        authorMail.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822"); // Set the MIME type for email

            intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"Tanim494@gmail.com"}); // Replace with the recipient's email address
            intent.putExtra(Intent.EXTRA_SUBJECT, "Request Feature or Suggestion for Arcade HUB"); // Set the email subject
            intent.putExtra(Intent.EXTRA_TEXT, "I have some suggestion for Arcade HUB. ........."); // Set the email body

            try {
                startActivity(Intent.createChooser(intent, "Send Email"));
            } catch (ActivityNotFoundException e) {
                // Handle the case where no email client is installed on the device
                Toast.makeText(AuthorActivity.this, "No email app installed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}