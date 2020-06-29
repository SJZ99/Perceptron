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

public class FeedbackActivity extends Activity {
    Button feedback, save_f, predictEnter;
    EditText distance;
    Spinner get;
    String[] isget = new String[]{"搭上", "沒搭上"};
    NeuralNetwork net;
    File f;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.feedback);

        setIntent(getIntent());
        f = fileInitial();
        net = checkNet(netInitial());
        if(net.isNaN())
            finish();

        feedback = findViewById(R.id.feedback);
        save_f = findViewById(R.id.save_f);
        predictEnter = findViewById(R.id.predictEnter_f);
        distance = findViewById(R.id.distance);
        get = findViewById(R.id.get);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, isget);
        get.setAdapter(adapter);

        feedback.setOnClickListener(feedbackListener);
        save_f.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                net.save(f.getPath());
                Toast t = Toast.makeText(FeedbackActivity.this, "save success", Toast.LENGTH_SHORT);
                t.show();
            }
        });
        predictEnter.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(FeedbackActivity.this, PredictActivity.class);
                intent.putExtra("file", f);
                intent.putExtra("net", net);
                startActivity(intent);
                if(net.isNaN())
                    finish();
//                finish();
            }
        });
    }

    private Button.OnClickListener feedbackListener =
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    double[] result = new double[2];
                    if(!distance.getText().toString().isEmpty()){
                        result[0] = get.getSelectedItem().toString() == "搭到" ? 1 : 0;
                        result[1] = Analyst.distance(Double.parseDouble(distance.getText().toString()));
                    }else{
                        new AlertDialog.Builder(FeedbackActivity.this)
                                .setTitle("提示")
                                .setMessage("\n請先輸入距離")
                                .setPositiveButton("確定", null)
                                .show();
                        return;
                    }
                    DataFactory.result = result;
                    net.learnInNewThread(DataFactory.feedback());
                    Toast t = Toast.makeText(FeedbackActivity.this, "feedback success", Toast.LENGTH_SHORT);
                    t.show();

                }
            };

}
