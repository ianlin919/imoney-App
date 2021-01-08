package com.example.fp1;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class activity_salary extends AppCompatActivity {
    private Button btn_back;
    private EditText ed_money,ed_reason,ed_year,ed_month,ed_day;
    private Button btn_determine,btn_cancel;

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> items = new ArrayList<>();
    private SQLiteDatabase dbrw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ed_money = findViewById(R.id.ed_money);
        ed_reason= findViewById(R.id.ed_reason);
        ed_year = findViewById(R.id.ed_year);
        ed_month = findViewById(R.id.ed_month);
        ed_day = findViewById(R.id.ed_day);
        btn_determine = findViewById(R.id.btn_determine);
        listView = findViewById(R.id.listView_chart);
        btn_cancel =findViewById(R.id.btn_cancel);

        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,items);
        listView.setAdapter(adapter);
        dbrw = new Myincome(this).getWritableDatabase();

        btn_determine.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (ed_money.length() < 1  || ed_year.length() < 1 || ed_reason.length() < 1 || ed_month.length() < 1 || ed_day.length() < 1)
                    Toast.makeText(activity_salary.this, "欄位請勿空白", Toast.LENGTH_SHORT).show();
                else {
                    try {
                        dbrw.execSQL("INSERT INTO myTable(money,reason,year,month,day)VALUES(?,?,?,?,?)", new Object[]{ed_money.getText().toString(),
                                ed_reason.getText().toString(), ed_year.getText().toString(), ed_month.getText().toString(), ed_day.getText().toString()});
                        Toast.makeText(activity_salary.this, "已新增金額" + ed_money.getText().toString() + " 原因" + ed_reason.getText().toString()+
                                        " 日期" + ed_year.getText().toString() + "/" + ed_month.getText().toString() + "/" + ed_day.getText().toString(),
                                Toast.LENGTH_SHORT).show();

                        ed_money.setText("");
                        ed_reason.setText("");
                        ed_year.setText("");
                        ed_month.setText("");
                        ed_day.setText("");

                        Cursor c;
                        c = dbrw.rawQuery("SELECT * FROM myTable",null);
                        c.moveToFirst();
                        items.clear();
                        for (int i = 0;i < c.getCount();i++)
                        {
                            items.add("金額:"+c.getString(0)+"\t原因:"+c.getString(1)+"\t日期:"+c.getString(2)+"/"+c.getString(3)+"/"+c.getString(4));
                            c.moveToNext();
                        }
                        adapter.notifyDataSetChanged();
                        c.close();

                    } catch (Exception e) {
                        Toast.makeText(activity_salary.this, "新增失敗:" + e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ed_money.setText("");
                ed_reason.setText("");
                ed_year.setText("");
                ed_month.setText("");
                ed_day.setText("");
            }
        });
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        dbrw.close();
    }
    }