package com.lezhi.image;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.lezhi.image.api.model.Pin;
import com.lezhi.image.widget.VerticalTextView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lezhi on 2017/5/30.
 */

public class ImageActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private ImageView mImageView;
    private Pin mPin;

    public static void startImageActivity(Context context, Pin pin) {
        Intent intent = new Intent();
        intent.putExtra("pin", pin);
        intent.setClass(context, ImageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        mImageView = (ImageView) findViewById(R.id.content_img);

        mPin = getIntent().getParcelableExtra("pin");
        if (mPin != null && mPin.getFile() != null) {
            Picasso.with(this)
                    .load(mPin.getFile().getImageUrl(Pin.ImageFile.FW658))
                    .into(mImageView);
        }

        ((VerticalTextView)findViewById(R.id.title_text)).setText(mPin.getBoard().getTitle());
        ((VerticalTextView)findViewById(R.id.desc_text)).setText(mPin.getBoard().getDescription());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                download();
            }
        });
    }

    public static File downLoadFromUrl(String urlStr, String fileName, String savePath) {
        InputStream inputStream;
        FileOutputStream fos;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            inputStream = conn.getInputStream();

            File file = new File(savePath + File.separator + fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
            fos = new FileOutputStream(file);
            byte data[] = new byte[1024 * 4];
            int count;
            while ((count = inputStream.read(data)) != -1) {
                fos.write(data, 0, count);
            }
            conn.disconnect();
            if (fos != null) {
                fos.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void doDownload() {
        new AsyncTask<Void, Void, File>() {
            @Override
            protected File doInBackground(Void... params) {
                String url = mPin.getFile().getImageUrl(Pin.ImageFile.FW658);
                return downLoadFromUrl(url, mPin.getFile().getKey(), Environment.getExternalStorageDirectory() + "/Download");
            }

            @Override
            protected void onPostExecute(File file) {
                Toast.makeText(ImageActivity.this, "download file " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    @Override
    public void onClick(View v) {

    }

    public void download() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            doDownload();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                doDownload();
            } else {
                Toast.makeText(ImageActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
