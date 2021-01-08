package com.example.fp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

public class Cld_Activity extends AppCompatActivity {
    private Button btn_back;
    private ListView lv_cld;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> items = new ArrayList<>();
    private SQLiteDatabase dbrw;
    private Calendar d = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cld_);
        int year = d.get(Calendar.YEAR);
        int month = d.get(Calendar.MONTH);
        int day = d.get(Calendar.DAY_OF_MONTH);
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
        lv_cld = findViewById(R.id.lv_cld);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,items);
        lv_cld.setAdapter(adapter);
        dbrw =new DB(this).getWritableDatabase();
        Cursor c;
        c = dbrw.rawQuery("SELECT * FROM myTable WHERE year ='" + year + "' and month = '" + month+1 + "'" , null);
            c.moveToFirst();
            items.clear();
            for (int i = 0; i < c.getCount(); i++) {
                int nday = Integer.parseInt(c.getString(3));
                nday = nday - day;
                String dd = String.valueOf(nday);
                items.add(c.getString(1)+"\t/"+c.getString(2)+"\t/"+c.getString(3)+"\n事件:"
                        + c.getString(0) + "\t內容:" + c.getString(4) + "\n即將到期:" + dd + "\t天後");
                c.moveToNext();
            }
            adapter.notifyDataSetChanged();
            c.close();
        }
    }