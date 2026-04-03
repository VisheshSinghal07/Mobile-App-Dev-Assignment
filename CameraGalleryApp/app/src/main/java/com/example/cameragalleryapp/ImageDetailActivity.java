package com.example.cameragalleryapp;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ImageDetailActivity extends AppCompatActivity {

    ImageView imageView;
    TextView tvDetails;
    Button btnDelete;

    String imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        imageView = findViewById(R.id.imageView);
        tvDetails = findViewById(R.id.tvDetails);
        btnDelete = findViewById(R.id.btnDelete);

        imageUri = getIntent().getStringExtra("path");

        imageView.setImageURI(Uri.parse(imageUri));

        tvDetails.setText(
                "Image URI:\n" + imageUri
        );

        btnDelete.setOnClickListener(v -> {

            new AlertDialog.Builder(this)
                    .setTitle("Delete Image")
                    .setMessage("Are you sure you want to delete this image?")
                    .setPositiveButton("Yes", (dialog, which) -> {

                        getContentResolver().delete(Uri.parse(imageUri), null, null);

                        finish();

                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }
}