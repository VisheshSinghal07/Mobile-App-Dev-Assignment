package com.example.cameragalleryapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList<String> imageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        gridView = findViewById(R.id.gridView);

        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                null,
                null,
                null
        );

        if (cursor != null) {

            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);

            while (cursor.moveToNext()) {

                long id = cursor.getLong(columnIndex);

                Uri contentUri = Uri.withAppendedPath(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "" + id
                );

                imageList.add(contentUri.toString());
            }

            cursor.close();
        }

        ImageAdapter adapter = new ImageAdapter(this, imageList);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener((AdapterView<?> parent, android.view.View view, int position, long id) -> {

            Intent intent = new Intent(GalleryActivity.this, ImageDetailActivity.class);
            intent.putExtra("path", imageList.get(position));
            startActivity(intent);

        });
    }
}