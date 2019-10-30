package com.chaychan.androidoservicecompat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.chaychan.aosc.ServiceCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startVideoUploadService(View view){
        Toast.makeText(this, "startVideoUploadService", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, VideoUploadService.class);
        ServiceCompat.startService(this, intent);
    }

    public void stopVideoUploadService(View view){
        stopService(new Intent(this,VideoUploadService.class));
    }

    public void startRecordAudioService(View view){
        Intent intent = new Intent(this, RecordAudioService.class);
        ServiceCompat.startService(this, intent);
    }

    public void stopRecordAudioService(View view){
        stopService(new Intent(this,RecordAudioService.class));
    }
}
