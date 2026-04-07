package com.example.cameragalleryapp;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import androidx.exifinterface.media.ExifInterface;
import java.io.InputStream;
import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> images;

    public ImageAdapter(Context context, ArrayList<String> images){
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() { return images.size(); }

    @Override
    public Object getItem(int position) { return images.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = (convertView == null) ? new ImageView(context) : (ImageView) convertView;
        imageView.setLayoutParams(new ViewGroup.LayoutParams(350, 350));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Uri uri = Uri.parse(images.get(position));
        imageView.setImageURI(uri);

        try (InputStream is = context.getContentResolver().openInputStream(uri)) {
            ExifInterface exif = new ExifInterface(is);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            switch (orientation) {
                case 6: imageView.setRotation(90); break;
                case 3: imageView.setRotation(180); break;
                case 8: imageView.setRotation(270); break;
                default: imageView.setRotation(0); break;
            }
        } catch (Exception e) {
            imageView.setRotation(0);
        }
        return imageView;
    }
}