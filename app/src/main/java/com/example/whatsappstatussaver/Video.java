package com.example.whatsappstatussaver;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;

public class Video extends AppCompatActivity {

    ImageView download,mydir,share;

    VideoView mparvideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        getSupportActionBar().setTitle("Video");

        mparvideo=findViewById(R.id.videosingle);
        share=findViewById(R.id.share);
        download=findViewById(R.id.download);
        mydir=findViewById(R.id.mychatapp);

        mydir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                String destpathdir= Environment.getExternalStorageDirectory()+"/StatusSaver/";
                Uri uri = Uri.parse(destpathdir);
                intent.setDataAndType(uri, "*/*");
                startActivity(intent);

            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Share is Clicked",Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent=getIntent();
        String destpath=intent.getStringExtra("DEST_PATH_VIDEO");
        String file=intent.getStringExtra("FILE_VIDEO");
        String uri=intent.getStringExtra("URI_VIDEO");
        String filename=intent.getStringExtra("FILENAME_VIDEO");

        File destpath2=new File(destpath);
        File file1=new File(file);

        MediaController mediaController=new MediaController(this);
        mediaController.setAnchorView(mparvideo);
        Uri uri1=Uri.parse(uri);
        mparvideo.setMediaController(mediaController);
        mparvideo.setVideoURI(uri1);
        mparvideo.requestFocus();
        mparvideo.start();
   //     Glide.with(getApplicationContext()).load(uri).into(mparticularimage);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    org.apache.commons.io.FileUtils.copyFileToDirectory(file1,destpath2);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                MediaScannerConnection.scanFile(getApplicationContext(),
                        new String[]{destpath + filename},
                        new String[]{"*/*"},
                        new MediaScannerConnection.MediaScannerConnectionClient() {
                            @Override
                            public void onMediaScannerConnected() {

                            }

                            @Override
                            public void onScanCompleted(String path, Uri uri) {

                            }
                        });

                Dialog dialog=new Dialog(Video.this);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.show();
                Button button=dialog.findViewById(R.id.okbutton);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });


    }
}