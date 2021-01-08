package com.example.fp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class notebook_Activity extends AppCompatActivity {
    private Button btn_back;
    private Button btn_add,btn_search;
    private EditText ed_search,ed_date;
    private SQLiteDatabase dbrw;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook_);
        listView = findViewById(R.id.listview_nb);
        ed_date = findViewById(R.id.ed_date);
        ed_search = findViewById(R.id.ed_search);
        btn_back = findViewById(R.id.btn_back);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,items);
        listView.setAdapter(adapter);
        dbrw =new DB(this).getWritableDatabase();
        Cursor c;
        c = dbrw.rawQuery("SELECT * FROM myTable",null);
        c.moveToFirst();
        items.clear();
        for (int i = 0;i < c.getCount();i++)
        {
            items.add("事件:"+c.getString(0)+"\t時間:"+c.getString(1)+"\t/"+c.getString(2)
                    +"\t/"+c.getString(3)+"\t內容:"+c.getString(4));
            c.moveToNext();
        }
        adapter.notifyDataSetChanged();
        c.close();


        btn_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        btn_add = findViewById(R.id.btn_new);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(notebook_Activity.this,add_memo.class);
                startActivity(intent);
            }
        });

        btn_search =findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View v){
                    Cursor c;
                    if(ed_search.length()<1)
                        c = dbrw.rawQuery("SELECT * FROM myTable",null);
                    else
                        c = dbrw.rawQuery("SELECT * FROM mytable WHERE event LIKE '"+ ed_search.getText().toString()
                    +"'",null);

                        c.moveToFirst();
                        items.clear();
                Toast.makeText(notebook_Activity.this,"共有"+c.getCount()+"筆資料",Toast.LENGTH_SHORT).show();
                        for (int i = 0;i < c.getCount();i++)
                        {
                            items.add("事件:"+c.getString(0)+"\t時間:"+c.getString(1)+"\t內容:"+c.getString(2));
                            c.moveToNext();
                        }
                        adapter.notifyDataSetChanged();
                        c.close();
                    }
        });

    }
}