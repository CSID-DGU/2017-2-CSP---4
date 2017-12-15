package com.example.audiocheck.audiocheck;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ScoreActivity extends AppCompatActivity implements View.OnClickListener{
    DBhelper db;
    TextView listview;
    Button main_start_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_activity);
        db = new DBhelper(getApplicationContext());
        main_start_btn = (Button)findViewById(R.id.main_start_btn);
        listview = (TextView) findViewById(R.id.listview);
        main_start_btn.setOnClickListener(this);
        ArrayList<DbModel> modelArrayList = new ArrayList<DbModel>();
        modelArrayList = db.getdata();

        if(modelArrayList != null){
            String test = "";
            for(DbModel model : modelArrayList){
                test += model.getDate() + "//" + String.valueOf(model.getScore())+"점 \n";
            }
            listview.setText(test);
        }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.main_start_btn:
                finish();
                break;
            default:
                break;
        }
    }
}
