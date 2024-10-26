package vn.edu.fpt.fa24;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

public class ImageViewActivity extends AppCompatActivity {

    PhotoView photoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        photoView = (PhotoView) findViewById(R.id.imageView);
        String uri = getIntent().getStringExtra("uri");

        Picasso.get().load(uri).into(photoView);
    }
}