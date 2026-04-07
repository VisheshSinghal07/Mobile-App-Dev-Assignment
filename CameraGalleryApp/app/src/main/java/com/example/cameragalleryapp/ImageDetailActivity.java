package com.example.cameragalleryapp;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;
import androidx.exifinterface.media.ExifInterface;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Date;

public class ImageDetailActivity extends AppCompatActivity {

    ImageView imageView;
    TextView tvName, tvPath, tvSize, tvDate;
    Button btnDelete;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        imageView = findViewById(R.id.imageView);
        tvName = findViewById(R.id.tvName);
        tvPath = findViewById(R.id.tvPath);
        tvSize = findViewById(R.id.tvSize);
        tvDate = findViewById(R.id.tvDate);
        btnDelete = findViewById(R.id.btnDelete);

        String uriString = getIntent().getStringExtra("path");
        imageUri = Uri.parse(uriString);
        imageView.setImageURI(imageUri);

        applyRotation(imageUri);

        DocumentFile file = DocumentFile.fromSingleUri(this, imageUri);
        if (file != null) {
            tvName.setText("Name: " + file.getName());
            tvPath.setText("Path: " + imageUri.toString());
            tvSize.setText("Size: " + file.length() + " bytes");
            String date = DateFormat.getDateTimeInstance().format(new Date(file.lastModified()));
            tvDate.setText("Date Taken: " + date);
        }

        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Image")
                    .setMessage("Are you sure you want to delete this image?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        if (file != null) file.delete();
                        Toast.makeText(ImageDetailActivity.this, "Image deleted successfully", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    private void applyRotation(Uri uri) {
        try (InputStream is = getContentResolver().openInputStream(uri)) {
            ExifInterface exif = new ExifInterface(is);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            switch (orientation) {
                case 6: imageView.setRotation(90); break;
                case 3: imageView.setRotation(180); break;
                case 8: imageView.setRotation(270); break;
                default: imageView.setRotation(0); break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}