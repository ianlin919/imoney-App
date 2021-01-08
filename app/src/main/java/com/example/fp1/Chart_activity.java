package com.example.fp1;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import java.util.Calendar;
import java.util.List;

public class Chart_activity extends AppCompatActivity {
    private Button btn_back;
    private TextView textView;
    private AnyChartView anyChartView;
    private String[] sort;
    private int[] costs;
    final String k="food";
    private int total,n;
    private float avg;
    private ListView lv_chart;
    private SQLiteDatabase dbep;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> items = new ArrayList<>();
    private Calendar d = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_activity);
        int year = d.get(Calendar.YEAR);
        int month = d.get(Calendar.MONTH);
        textView = findViewById(R.id.money);
        costs = new int[100];
        sort = new String[100];
        int dday=0;
        dbep = new Myexpend(this).getReadableDatabase();
        anyChartView = findViewById(R.id.any_chart_view);
        lv_chart = findViewById(R.id.listView_chart);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,items);//顯示支出收入連接
        lv_chart.setAdapter(adapter);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //返回鍵


        Cursor c;
        c = dbep.rawQuery("SELECT * FROM myTable WHERE year ='" + year + "' and month = '" + month+1 + "'", null);
        c.moveToFirst();
        items.clear();
        for (int i = 0; i < c.getCount(); i++) {
            sort[i] = c.getString(1);
            int p =Integer.parseInt(c.getString(0));
            costs[i] =p;
            int ndday =Integer.parseInt(c.getString(4));
            if(ndday>dday)  //只將不同日期的支出做獨立
                n=n+1;
                dday = Integer.parseInt(c.getString(4));
            items.add("支出:" + c.getString(0) + "\n種類:" + c.getString(1));
            c.moveToNext();
        }
        setupPieChart();
        adapter.notifyDataSetChanged();
        c.close();
        //儲存並顯示當月所有支出



       textView.setText(String.format("總共花費:" + total + "       平均花費(/日):" + avg));
    }
        public void setupPieChart(){
            int costfood = 0,costclothes =0, costlive = 0,costvehicle = 0,costamuse = 0;
            Pie pie = AnyChart.pie();
            List<DataEntry> dataEntries = new ArrayList<>();

            for(int i =0;i<sort.length;i++) {
                if (sort[i] != null) {
                    if (sort[i].equals("food")|| sort[i].equals("dinner")|| sort[i].equals("breakfast")|| sort[i].equals("lunch"))
                        costfood = costfood + costs[i];
                    else if (sort[i].equals("T-shirt") || sort[i].equals("pants")|| sort[i].equals("bag") || sort[i].equals("clothes") || sort[i].equals("jacket")|| sort[i].equals("coat"))
                        costclothes = costclothes + costs[i];
                    else if (sort[i].equals("live") || sort[i].equals("rent") || sort[i].equals("loan") || sort[i].equals("water") || sort[i].equals("electricity") || sort[i].equals( "management fee"))
                        costlive = costlive + costs[i];
                    else if (sort[i].equals("vehicle") || sort[i].equals("car") || sort[i].equals("ticket") || sort[i].equals("taxi") || sort[i].equals("airplane") || sort[i].equals("bus") || sort[i].equals("train"))
                        costvehicle = costvehicle + costs[i];
                    else if (sort[i].equals("amuse") || sort[i].equals("game") || sort[i].equals("movie") || sort[i].equals("snacks") || sort[i].equals("video") || sort[i].equals("play"))
                        costamuse = costamuse + costs[i];
                    else
                        dataEntries.add(new ValueDataEntry(sort[i], costs[i]));
                }
            }
                dataEntries.add(new ValueDataEntry("food",costfood));
                dataEntries.add(new ValueDataEntry("clothes",costclothes));
                dataEntries.add(new ValueDataEntry("live",costlive));
                dataEntries.add(new ValueDataEntry("vehicle",costvehicle));
                dataEntries.add(new ValueDataEntry("amuse",costamuse));

            for(int i =0;i<sort.length;i++){
                total=total+costs[i];
            }
            avg=(total/n);
            pie.data(dataEntries);
            anyChartView.setChart(pie);
        }
    }
