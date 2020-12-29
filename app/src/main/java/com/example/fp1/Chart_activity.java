package com.example.fp1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import java.util.ArrayList;
import java.util.List;

public class Chart_activity extends AppCompatActivity {
    private Button btn_back;
    private TextView textView;
    private AnyChartView anyChartView;
    private String[] weeks = {"Mon","Tues","Wen","Thurs","Fri","Sat","Sun"};
    private int[] costs= {500,800,2000,0,0,0,100};
    private int total;
    private float avg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_activity);
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textView = findViewById(R.id.money);
        anyChartView = findViewById(R.id.any_chart_view);
        setupPieChart();
        String[] messageArray = new String[]{"Mon:" + costs[0] + "元", "Tus:" + costs[1] + "元", "Wen:" + costs[2] + "元", "Thurs:" + costs[3] + "元", "Fri:" + costs[4] + "元", "Sat:" + costs[5] + "元", "Sun:" + costs[6] + "元"};
        ArrayAdapter<String> messageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messageArray);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(messageAdapter);

        textView.setText(String.format("總共花費:" + total + "       平均花費:" + avg));
    }
        public void setupPieChart(){
            Pie pie = AnyChart.pie();
            List<DataEntry> dataEntries = new ArrayList<>();

            for(int i =0;i<weeks.length;i++){
                dataEntries.add(new ValueDataEntry(weeks[i],costs[i]));
            }
            for(int i =0;i<weeks.length;i++){
                total=total+costs[i];
            }
            avg=(total/7);
            pie.data(dataEntries);
            anyChartView.setChart(pie);
        }
    }
