package com.example.fp1;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class add_memo extends AppCompatActivity {
    private Button btn_back,btn_check;
    private EditText ed_event,ed_year,ed_month,ed_day,ed_content;
    private SQLiteDatabase dbrw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memo);
        dbrw = new DB(this).getWritableDatabase();
        ed_event = findViewById(R.id.ed_event);
        ed_year = findViewById(R.id.ed_year);
        ed_month = findViewById(R.id.ed_month);
        ed_day = findViewById(R.id.ed_day);
        ed_content = findViewById(R.id.ed_content);
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // 返回按鈕

        btn_check =findViewById(R.id.btn_check);
        btn_check.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (ed_event.length()<1 || ed_year.length()<1|| ed_content.length()<1||ed_day.length()<1||ed_month.length()<1)
                    Toast.makeText(add_memo.this,"欄位請勿留空",Toast.LENGTH_SHORT).show();
                else{
                    try{
                        dbrw.execSQL("INSERT INTO myTable(event,year,month,day,notee)VALUES(?,?,?,?,?)"
                                ,new Object[]{ed_event.getText().toString(),
                                        ed_year.getText().toString(),
                                        ed_month.getText().toString(),
                                        ed_day.getText().toString(),
                                        ed_content.getText().toString()});
                        Toast.makeText(add_memo.this,"新增成功!",Toast.LENGTH_SHORT).show();

                        finish();
                    }catch (Exception e){
                        Toast.makeText(add_memo.this,"新增失敗:"+e.toString(),Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
        @Override
        public void onDestroy(){
            super.onDestroy();
            dbrw.close();
        }
    }