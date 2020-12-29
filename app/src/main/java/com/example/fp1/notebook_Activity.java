package com.example.fp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class notebook_Activity extends AppCompatActivity {
    private Button btn_back;
    private Button btn_add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook_);
        btn_back = findViewById(R.id.btn_back);
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
    }
}