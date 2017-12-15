package com.example.audiocheck.audiocheck;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

// 데이터 저장
public class DBhelper extends SQLiteOpenHelper {

    private static final String DB_NAME="dadev"; //db 이름
    private static final int DB_VER = 1; // db 버전
    private static final String DB_TABLE="audiocheck"; //table 이름


    public DBhelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    // 디비 테이블 생성
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE audiocheck(_id INTEGER PRIMARY KEY AUTOINCREMENT, newdate TEXT, " +
                "newscore INTEGER);");
    }

    // 버전변경시 업데이트
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = String.format("DELETE TABLE IF EXISTS %s",DB_TABLE);
        db.execSQL(query); // 테이블 삭제
        onCreate(db); // 완료한다.
    }


    //회원가입
    public void insert(String  date,int score){
        SQLiteDatabase db = getWritableDatabase(); // getWritableDatabase = 읽고 쓸수 있게
        db.execSQL("INSERT INTO audiocheck VALUES(NULL,'"+date+"', "+score+");");
        db.close();
    }

    // 게시판 글 목록 조회
    public ArrayList<DbModel> getdata() {
        ArrayList<DbModel> itemmodels = new ArrayList<DbModel>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from "+DB_TABLE;
        Cursor c = db.rawQuery(query, null);
        while (c.moveToNext()){
            DbModel model = new DbModel();
            String date = c.getString(1);
            int score = c.getInt(2);
            model.setDate(date);
            model.setScore(score);
            itemmodels.add(model);
        }
        c.close();
        db.close();
        return itemmodels;
    }


}
