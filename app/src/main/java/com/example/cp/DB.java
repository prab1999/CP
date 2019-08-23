package com.example.cp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;


public class DB extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "usersdb";
    private static final String TABLE_Users = "userdetails";
    private static final String KEY_ID = "id";
    private static final String KEY_NUM = "num";
    private static final String KEY_SUB="sub";
    private static final String KEY_VER="ver";
    private static final String KEY_CODE="code";
    private static final String KEY_QUESTIONS = "question";
    private static final String KEY_TAGS = "tag";
    private static final String KEY_LASTDATE="date";
    private static final String KEY_UNSOLVED="unsolved";
    private static final String KEY_ATTEMPT="attempt";

    public DB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_Users + "("
                + KEY_ID + " TEXT PRIMARY KEY ,"
                + KEY_QUESTIONS + " TEXT,"
                + KEY_SUB + " TEXT,"
                + KEY_VER + " TEXT,"
                + KEY_CODE + " TEXT,"
                + KEY_LASTDATE + " TEXT,"
                + KEY_UNSOLVED + " TEXT,"
                + KEY_ATTEMPT + " TEXT,"
                + KEY_NUM + " int,"
                + KEY_TAGS + " TEXT"+ ")";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        db.setVersion(i1);
        onCreate(db);
    }
    void insertUserDetails(String id){
        String sub="",ver="",code=" ",questions="",tags=concatearray(new int[26]);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_ID,id);
        cValues.put(KEY_QUESTIONS, questions);
        cValues.put(KEY_SUB,sub);
        cValues.put(KEY_VER,ver);
        cValues.put(KEY_CODE,code);
        cValues.put(KEY_LASTDATE,"");
        cValues.put(KEY_UNSOLVED,"");
        cValues.put(KEY_ATTEMPT,"");
        cValues.put(KEY_NUM,0);
        cValues.put(KEY_TAGS, tags);
        db.insert(TABLE_Users,null, cValues);
        db.close();
    }
    public void UpdateUserDetails(String questions, int[] tagarr, String id,String sub,String ver,String code,int num,long sec,String attempts,String unsolv){
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        cal.setTimeInMillis(sec*1000L);
        System.out.println(sec);
        String min="0"+cal.get(Calendar.MINUTE);
        if(min.length()>2)min=min.substring(1);
        String hour="0"+cal.get(Calendar.HOUR_OF_DAY);
        if(hour.length()>2)hour=hour.substring(1);
        String dateString = cal.get(Calendar.DAY_OF_MONTH)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+
                cal.get(Calendar.YEAR)+" "+hour+":"+min;


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        String tags=concatearray(tagarr);
        cVals.put(KEY_QUESTIONS,questions);
        cVals.put(KEY_NUM,num);
        cVals.put(KEY_SUB,sub);
        cVals.put(KEY_CODE,code);
        cVals.put(KEY_VER,ver);
        cVals.put(KEY_TAGS, tags);
        cVals.put(KEY_UNSOLVED, unsolv);
        cVals.put(KEY_ATTEMPT, attempts);
        cVals.put(KEY_LASTDATE,dateString);
        int count = db.update(TABLE_Users, cVals, KEY_ID+" = ?",new String[]{String.valueOf(id)});

    }

    public HashMap<String, String> GetUserByUserId(String userid){
        SQLiteDatabase db = this.getReadableDatabase();
        String query="Select * from "+TABLE_Users+" where "+KEY_ID+" = '"+userid+"'";

        Cursor cursor = db.rawQuery(query,null);
        HashMap<String,String> user = new HashMap<>();

        if (cursor.moveToNext()){
            user.put("id",cursor.getString(cursor.getColumnIndex(KEY_ID)));
            user.put("questions",cursor.getString(cursor.getColumnIndex(KEY_QUESTIONS)));
            user.put("sub",cursor.getString(cursor.getColumnIndex(KEY_SUB)));
            user.put("ver",cursor.getString(cursor.getColumnIndex(KEY_VER)));
            user.put("tags",cursor.getString(cursor.getColumnIndex(KEY_TAGS)));
            user.put("code",cursor.getString(cursor.getColumnIndex(KEY_CODE)));
            user.put("num",cursor.getString(cursor.getColumnIndex(KEY_NUM)));
            user.put("date",cursor.getString(cursor.getColumnIndex(KEY_LASTDATE)));
            user.put("attempt",cursor.getString(cursor.getColumnIndex(KEY_ATTEMPT)));
            user.put("unsolved",cursor.getString(cursor.getColumnIndex(KEY_UNSOLVED)));

        }
        return  user;
    }
    public boolean checkuser(String userid){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c;

        String query="Select * from "+TABLE_Users+" where "+KEY_ID+" = '"+userid+"'";

        c=db.rawQuery(query,null);
        if(!c.moveToNext())
            return false;
        else return true;

    }
    public String concatearray(int[] arr){
        String ans="";
        for(int i=0;i<arr.length-1;i++){
            ans+=(arr[i]+"|");
        }
        ans+=arr[arr.length-1];
        return ans;
    }
}
