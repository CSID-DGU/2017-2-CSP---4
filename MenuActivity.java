package com.example.audiocheck.audiocheck;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.File;


public class MenuActivity extends AppCompatActivity implements View.OnClickListener{
    Button main_start_btn,main_stop_btn;
    final static int REQUEST_WRITE = 100; // db
    final static int REQUEST_Audio = 200; // 미디어

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        main_start_btn = (Button)findViewById(R.id.main_start_btn);
        main_stop_btn = (Button)findViewById(R.id.main_stop_btn);
        main_start_btn.setOnClickListener(this);
        main_stop_btn.setOnClickListener(this);

        int permissionWrite  = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        //퍼미션 권한요청
        if ( permissionWrite != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE);
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.main_start_btn:
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                break;
            case  R.id.main_stop_btn:
                Intent intent1 = new Intent(getApplicationContext(),ScoreActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }

    //권한요청
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case REQUEST_WRITE :
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    int permissionRecord = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
                    if (permissionRecord != PackageManager.PERMISSION_GRANTED ) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_Audio);
                    }
                };
                break;
            case REQUEST_Audio :
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){};
                break;
            default:
                break;
        }
    }
}
