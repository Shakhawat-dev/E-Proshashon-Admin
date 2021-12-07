package com.metacoders.e_proshashonadmin.Acitivity;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.metacoders.e_proshashonadmin.Adapter.SliderAdapterExample;
import com.metacoders.e_proshashonadmin.R;
import com.smarteist.autoimageslider.SliderView;

public class ImageViewerPage extends AppCompatActivity {

    DownloadManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer_page);

        SliderView sliderView = findViewById(R.id.imageSlider);

        SliderAdapterExample adapter = new SliderAdapterExample(this);
        adapter.renewItems(getIntent().getStringArrayListExtra("URI_LIST_DATA"));
        sliderView.setSliderAdapter(adapter);

        findViewById(R.id.backBtn)
                .setOnClickListener(v -> finish());

        findViewById(R.id.downloadBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getDownload(getIntent().getStringArrayListExtra("URI_LIST_DATA").get(sliderView.getCurrentPagePosition()));
                } catch (Exception e) {
                    getDownload(getIntent().getStringArrayListExtra("URI_LIST_DATA").get(0));
                }

            }
        });


    }

    public void getDownload(String url) {
        DownloadManager downloadmanager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        Toast.makeText(getApplicationContext(), "Downloading...", Toast.LENGTH_LONG).show();
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(System.currentTimeMillis() / 1000 + ".jpg");
        request.setDescription("Downloading");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Ovijog");
        downloadmanager.enqueue(request);


    }


}