package com.learn.percetron;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.learn.percetron.core.Analyst;
import com.learn.percetron.core.DataFactory;
import com.learn.percetron.core.NeuralNetwork;

import java.io.File;

public class MainActivity extends Activity {

    Button predict, feedback;
    NeuralNetwork net;
    File f;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);
        predict = findViewById(R.id.main);
//        feedback = findViewById(R.id.feedbackEnter_m);
        f = new File(MainActivity.this.getExternalFilesDir(null) + "NeuralNetwork");

        net = load(f);

        predict.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PredictActivity.class);intent.putExtra("net", net);
                intent.putExtra("file", f);
                intent.putExtra("net", net);
                startActivity(intent);
            }
        });

//        feedback.setOnClickListener(new Button.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this, FeedbackActivity.class);
//                intent.putExtra("file", f);
//                startActivity(intent);
//            }
//        });


    }




}
