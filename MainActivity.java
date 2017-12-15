package com.example.audiocheck.audiocheck;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button main_start_btn,main_stop_btn,main_bac_btn; // 시작 / 종료 버튼
    TextView main_textview; // 텍스트 박스
    float volume = 10000; // 소리 체크
    private MyMediaRecorder mRecorder; // recorder 기능 생성
    private static final int msgWhat = 1; // 핸들러 메시지
    private static final int refreshTime = 100; // 핸들러 메시지 #0.1초 체크
    Double titmecount=0.00;
    boolean[] totalscorecheck;
    DBhelper db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = new DBhelper(getApplicationContext());

        main_start_btn = (Button)findViewById(R.id.main_start_btn);
        main_stop_btn = (Button)findViewById(R.id.main_stop_btn);
        main_bac_btn = (Button)findViewById(R.id.main_bac_btn);
        main_textview = (TextView) findViewById(R.id.main_textview);
        main_start_btn.setOnClickListener(this);
        main_stop_btn.setOnClickListener(this);
        main_bac_btn.setOnClickListener(this);


        // 점수 카운팅 배열 생성
        totalscorecheck = new boolean[50];
        for(int a =0; a < totalscorecheck.length; a++){
            totalscorecheck[a] =  false;
        }

        mRecorder = new MyMediaRecorder();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_start_btn:
                titmecount=0.00;
                File file = FileUtil.createFile("temp.amr");
                if (file != null) {
                    startRecord(file);
                } else {
                }
                break;
            case  R.id.main_stop_btn:
                mRecorder.delete();
                handler.removeMessages(msgWhat);
                break;
            case  R.id.main_bac_btn:
                titmecount=0.00;
                mRecorder.delete();
                handler.removeMessages(msgWhat);
                finish();
                break;
            default:
                break;
        }

    }


    // 핸들러
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            handler.sendEmptyMessageDelayed(msgWhat, refreshTime);
            volume = mRecorder.getMaxAmplitude();
            // 소리 체크 및 카운트 증가
            if(volume > 0 && volume < 1000000) {
                volume = 20 * (float)(Math.log10(volume));
                String time = String.format("%.1f ",titmecount );
                main_textview.setText(String.valueOf((int) (volume)) + "DB ///" + time + "초");
                // 박수소리 = 90DB이상 체크
                if(volume>90){
                    timecheck(titmecount);
                }
                titmecount += 0.1;

                // 종료
                if(titmecount <= 80){

                }else {
                    endaudiocheck();
                }
            }
        }
    };


    // 80초 이후 종료
    private void endaudiocheck() {
        mRecorder.delete();
        handler.removeMessages(msgWhat);
        int totalscore = 100;
        for(int a =0; a < totalscorecheck.length; a++){
            if(totalscorecheck[a] == false){
                totalscore -= 2;
            }
        }
        main_textview.setText(String.valueOf(totalscore)+"점 입니다.");
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        String getTime = sdf.format(date);
        db.insert(getTime,totalscore);
        volume = 0;
    }


    // 핸들러 시작
    private void startListenAudio() {
        handler.sendEmptyMessageDelayed(msgWhat, refreshTime);
    }

    // 파일형식 생성
    public void startRecord(File fFile){
        try{
            mRecorder.setMyRecAudioFile(fFile);
            if (mRecorder.startRecorder()) {
                startListenAudio();
            }else{
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // 핸들러 종료
    @Override
    protected void onPause() {
        super.onPause();
        mRecorder.delete();
        handler.removeMessages(msgWhat);
    }

    //핸들러 종료
    @Override
    protected void onDestroy() {
        handler.removeMessages(msgWhat);
        mRecorder.delete();
        super.onDestroy();
    }

    //  점수 카운팅 테이블
    private void timecheck(Double titmecount) {

        if(titmecount>= 7.2  && 7.5 >= titmecount){
            totalscorecheck[0] = true;
        }
        if(titmecount>= 7.7  && 8.5 >= titmecount){
            totalscorecheck[1] = true;
        }
        if(titmecount>= 8.4  && 8.6 >= titmecount){
            totalscorecheck[2] = true;
        }
        if(titmecount>= 10.4  && 10.9 >= titmecount){
            totalscorecheck[3] = true;
        }
        if(titmecount>= 11.3  && 11.8 >= titmecount){
            totalscorecheck[4] = true;
        }
        if(titmecount>= 13.9  && 14.3 >= titmecount){
            totalscorecheck[5] = true;
        }
        if(titmecount>= 15.2  && 15.4 >= titmecount){
            totalscorecheck[6] = true;
        }
        if(titmecount>= 15.5  && 16.1 >= titmecount){
            totalscorecheck[7] = true;
        }
        if(titmecount>= 17.8  && 18.0 >= titmecount){
            totalscorecheck[8] = true;
        }
        if(titmecount>= 18.6  && 19.0 >= titmecount){
            totalscorecheck[9] = true;
        }
        if(titmecount>= 21.3  && 21.5 >= titmecount){
            totalscorecheck[10] = true;
        }
        if(titmecount>= 22.2  && 22.5 >= titmecount){
            totalscorecheck[11] = true;
        }
        if(titmecount>= 23.3  && 24.7 >= titmecount){
            totalscorecheck[12] = true;
        }
        if(titmecount>= 24.8  && 25.1 >= titmecount){
            totalscorecheck[13] = true;
        }
        if(titmecount>= 25.6  && 25.9 >= titmecount){
            totalscorecheck[14] = true;
        }
        if(titmecount>= 27.1  && 27.8 >= titmecount){
            totalscorecheck[15] = true;
        }
        if(titmecount>= 28.3  && 28.8 >= titmecount){
            totalscorecheck[16] = true;
        }
        if(titmecount>= 29.5  && 30.0 >= titmecount){
            totalscorecheck[17] = true;
        }
        if(titmecount>= 30.6  && 30.9 >= titmecount){
            totalscorecheck[18] = true;
        }
        if(titmecount>= 33.2  && 33.5 >= titmecount){
            totalscorecheck[19] = true;
        }
        if(titmecount>= 33.7  && 34.0 >= titmecount){
            totalscorecheck[20] = true;
        }
        if(titmecount>= 36.8  && 36.9 >= titmecount){
            totalscorecheck[21] = true;
        }
        if(titmecount>= 37.3  && 37.6 >= titmecount){
            totalscorecheck[22] = true;
        }
        if(titmecount>= 37.7  && 38.0 >= titmecount){
            totalscorecheck[23] = true;
        }
        if(titmecount>= 39.4  && 39.8 >= titmecount){
            totalscorecheck[24] = true;
        }
        if(titmecount>= 40.0  && 40.3 >= titmecount){
            totalscorecheck[25] = true;
        }
        if(titmecount>= 43.1  && 43.4 >= titmecount){
            totalscorecheck[26] = true;
        }
        if(titmecount>= 43.8  && 44.2 >= titmecount){
            totalscorecheck[27] = true;
        }
        if(titmecount>= 46.1  && 46.4 >= titmecount){
            totalscorecheck[28] = true;
        }
        if(titmecount>= 47.5  && 47.9 >= titmecount){
            totalscorecheck[29] = true;
        }
        if(titmecount>= 49.4  && 49.6 >= titmecount){
            totalscorecheck[30] = true;
        }
        if(titmecount>= 50.9  && 51.1 >= titmecount){
            totalscorecheck[31] = true;
        }
        if(titmecount>= 53.0  && 53.4 >= titmecount){
            totalscorecheck[32] = true;
        }
        if(titmecount>= 56.6  && 57.0 >= titmecount){
            totalscorecheck[33] = true;
        }
        if(titmecount>= 58.6  && 59.2 >= titmecount){
            totalscorecheck[34] = true;
        }
        if(titmecount>= 59.9  && 60.2 >= titmecount){
            totalscorecheck[35] = true;
        }
        if(titmecount>= 61.8  && 62.1 >= titmecount){
            totalscorecheck[36] = true;
        }
        if(titmecount>= 62.7  && 63.1 >= titmecount){
            totalscorecheck[37] = true;
        }
        if(titmecount>= 64.4  && 64.7 >= titmecount){
            totalscorecheck[38] = true;
        }
        if(titmecount>= 64.9  && 65.3 >= titmecount){
            totalscorecheck[39] = true;
        }
        if(titmecount>= 67.1  && 67.4 >= titmecount){
            totalscorecheck[40] = true;
        }
        if(titmecount>= 67.8  && 68.3 >= titmecount){
            totalscorecheck[41] = true;
        }
        if(titmecount>= 72.1  && 72.4 >= titmecount){
            totalscorecheck[42] = true;
        }
        if(titmecount>= 75.8  && 76.1 >= titmecount){
            totalscorecheck[43] = true;
        }
        if(titmecount>= 76.2  && 76.5 >= titmecount){
            totalscorecheck[44] = true;
        }

        if(titmecount>= 76.2  && 76.5 >= titmecount){
            totalscorecheck[45] = true;
        }
        if(titmecount>= 76.2  && 76.5 >= titmecount){
            totalscorecheck[46] = true;
        }
        if(titmecount>= 76.2  && 76.5 >= titmecount){
            totalscorecheck[47] = true;
        }
        if(titmecount>= 76.2  && 76.5 >= titmecount){
            totalscorecheck[48] = true;
        }
        if(titmecount>= 76.2  && 76.5 >= titmecount){
            totalscorecheck[49] = true;
        }
    }



}
