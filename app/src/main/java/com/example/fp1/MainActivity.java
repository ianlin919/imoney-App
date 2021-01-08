package com.example.fp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button btn_cld;
    private Button btn_notebook;
    private Button btn_Out;
    private Button btn_salary;
    private Button btn_chart;
    private ListView listview;
    private TextView tv_salary;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> items = new ArrayList<>();
    private SQLiteDatabase dbic,dbep;
    private Integer salary=0,spend=0;
    CalendarView calendarview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = findViewById(R.id.listview);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,items);//顯示支出收入連接
        listview.setAdapter(adapter);
        dbic = new Myincome(this).getWritableDatabase();
        dbep = new Myexpend(this).getReadableDatabase();
        tv_salary = findViewById(R.id.tv_salary);
        calendarview = findViewById(R.id.cld);
        calendarview.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Toast.makeText(MainActivity.this, "您選擇的時間是：" + year + "年" + month + "月" + dayOfMonth + "日", Toast.LENGTH_SHORT).show();
                Cursor c;
                    c = dbic.rawQuery("SELECT * FROM myTable WHERE year ='" + year + "' and month = '" + month + "'", null);
                    c.moveToFirst();
                    items.clear();
                    for (int i = 0; i < c.getCount(); i++) {
                        int y =Integer.parseInt(c.getString(0));
                        salary = salary + y;
                        c.moveToNext();
                    }
                    //本月收入

                    c = dbep.rawQuery("SELECT * FROM myTable WHERE year ='" + year + "' and month = '" + month +
                            "' and day ='" + dayOfMonth + "'", null);
                    c.moveToFirst();
                    items.clear();
                    for (int i = 0; i < c.getCount(); i++) {
                        int p =Integer.parseInt(c.getString(0));
                        spend = spend + p;
                        c.moveToNext();
                    }
                    //是日總支出

                    c = dbic.rawQuery("SELECT * FROM myTable WHERE year ='" + year + "' and month = '" + month +
                            "' and day ='" + dayOfMonth + "'", null);
                    c.moveToFirst();
                    items.clear();
                    for (int i = 0; i < c.getCount(); i++) {
                        items.add("收入:+" + c.getString(0) + "\n原因:" + c.getString(1));
                        c.moveToNext();
                    }
                    //是日收入

                    c = dbep.rawQuery("SELECT * FROM myTable WHERE year ='" + year + "' and month = '" + month
                            + "' and day ='" + dayOfMonth + "'", null);
                    c.moveToFirst();
                    for (int i = 0; i < c.getCount(); i++) {

                        items.add("支出:-" + c.getString(0) + "\n原因:" + c.getString(1));
                        c.moveToNext();
                    }
                    adapter.notifyDataSetChanged();
                    c.close();
                    //是日支出

                    tv_salary.setText(String.format("本月收入:\n%d$NTD\n\n\n是日總支出:\n%d$NTD",salary,spend));//顯示總支出及本月收入
                    salary = 0;
                    spend = 0;//顯示完後歸0
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


    }
    @Override
    public void onDestroy(){
    super.onDestroy();
    dbep.close();
    dbic.close();
    }
}
