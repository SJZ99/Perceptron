package com.learn.percetron;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.learn.percetron.core.Analyst;
import com.learn.percetron.core.DataFactory;
import com.learn.percetron.core.NeuralNetwork;

import java.io.File;

public class PredictActivity extends Activity {
    Spinner weather, temperature, color, get;
    Button save, start, feedback_p;
    EditText time, distance;
    String[] wea = new String[]{"晴天", "陰天", "雨天"};
    String[] temper = new String[]{"熱", "微熱", "微涼", "涼"};
    String[] col = new String[]{"亮", "暗"};
    NeuralNetwork net;
    File f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.predict);
        componentInitial();

        setIntent(getIntent());
        f = fileInitial();
        net = checkNet(netInitial());

        start.setOnClickListener(startListener);
        save.setOnClickListener(saveListener);

        feedback_p.setOnClickListener(feedbackListener);
    }

    private void componentInitial(){
        weather = findViewById(R.id.weather);
        temperature = findViewById(R.id.temperature);
        color = findViewById(R.id.color);
        time = findViewById(R.id.time);
        save = findViewById(R.id.save);
        start = findViewById(R.id.start);
        feedback_p = findViewById(R.id.feedbackEnter_p);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, wea);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weather.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, temper);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        temperature.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, col);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        color.setAdapter(adapter);
    }

    private Button.OnClickListener startListener =
            new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(!time.getText().toString().isEmpty()) {
                        double[] input = new double[]{0, 0, 0, 0, 0, 0, 0, 0, Analyst.time(Double.parseDouble(time.getText().toString()))};
                        input[Analyst.weather(weather.getSelectedItem().toString())] = 1;
                        input[Analyst.temperature(temperature.getSelectedItem().toString())] = 1;
                        input[7] = color.getSelectedItem().toString() == "亮" ? 1 : 0;

                        DataFactory.input = input;

                        net.setInput(input);
                        net.calculate();
                        new AlertDialog.Builder(PredictActivity.this)
                                .setTitle("結果")
                                .setMessage("\n" + Analyst.isGetBus(net.getOutput()[0]) + Analyst.distanceReverseToString(net.getOutput()[1]))
                                .setPositiveButton("確定", null)
                                .show();
                    }else {
                        new AlertDialog.Builder(PredictActivity.this)
                                .setTitle("結果")
                                .setMessage("\n請先輸入上車時間")
                                .setPositiveButton("確定", null)
                                .show();
                        return;
                    }
                }
            };

    private Button.OnClickListener saveListener =
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    net.save(f.getPath().toString());
                    Toast t = Toast.makeText(PredictActivity.this, "save success", Toast.LENGTH_SHORT);
                    t.show();
                }
            };

    private Button.OnClickListener feedbackListener =
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(PredictActivity.this, FeedbackActivity.class);
                    intent.putExtra("file", f);
                    intent.putExtra("net", net);
                    startActivity(intent);
                }
            };


}
