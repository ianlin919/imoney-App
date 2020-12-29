package com.example.fp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    private Button btn_cld ;
    private Button btn_notebook;
    private Button btn_Out;
    private Button btn_salary;
    private Button btn_chart;
    private SQLiteDatabase dbrw;/*database*/
    private ListView listview;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> items =new ArrayList<>();
    CalendarView calendarview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarview =  findViewById(R.id.cld);
        calendarview.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Toast.makeText(MainActivity.this, "您選擇的時間是：" + year + "年" + month + "月" + dayOfMonth + "日", Toast.LENGTH_SHORT).show();
            }
        });
        //日期顯示


        //按鈕選擇
        btn_cld = findViewById(R.id.btn_cld);
        btn_cld.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this,
                        Cld_Activity.class), 1);
            }
        });
        //連接行事曆介面

        btn_notebook = findViewById(R.id.btn_notebook);
        btn_notebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this,
                        notebook_Activity.class), 1);
            }
        });
        //連接記事本介面

        btn_Out = findViewById(R.id.btn_out);
        btn_Out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this,
                        activity_Out.class), 1);
            }
        });
        //連接支出介面

        btn_salary = findViewById(R.id.btn_in);
        btn_salary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this,
                        activity_salary.class), 1);
            }
        });
        //連接收入介面


        btn_chart = findViewById(R.id.btn_chart);
        btn_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this,
                        Chart_activity.class), 1);
            }
        });
        //連接圖表介面


        //宣告adapter，用來顯示當日的資料
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, items);

        dbrw = new DB(this).getWritableDatabase();
        }
        @Override
        public void onDestroy(){
            super.onDestroy();
            dbrw.close();
        }
    }