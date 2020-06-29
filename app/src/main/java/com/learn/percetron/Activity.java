package com.learn.percetron;

import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.learn.percetron.core.DataFactory;
import com.learn.percetron.core.NeuralNetwork;

import java.io.File;

public class Activity extends AppCompatActivity {
    Intent intent;

    public void setIntent(Intent intent){
        this.intent = intent;
    }

    public File fileInitial(){
        return (File)intent.getSerializableExtra("file");
    }

    public NeuralNetwork netInitial(){
        return (NeuralNetwork)intent.getSerializableExtra("net");
    }

    public NeuralNetwork checkNet(NeuralNetwork net){
        if(net != null && !net.isNaN()){
            Toast t = Toast.makeText(this, "pass success", Toast.LENGTH_SHORT);
            t.show();
        }else{
//            net.whenNaNLearn(DataFactory.trainingData());
            Toast t = Toast.makeText(this, "pass error, go back and try again", Toast.LENGTH_SHORT);
            t.show();
        }
        return net;
    }

    public NeuralNetwork load(File f){
        NeuralNetwork net;
        if((net = (NeuralNetwork)NeuralNetwork.load(f.getPath().toString())) != null){

            if(net.whenNaNLearn(DataFactory.trainingData())){
                Toast t = Toast.makeText(this, "retrain success", Toast.LENGTH_SHORT);
                t.show();
            }else{
                Toast t = Toast.makeText(this, "load success", Toast.LENGTH_SHORT);
                t.show();
            }
        }else{
            net = new NeuralNetwork();
            net.whenNaNLearn(DataFactory.trainingData());
            Toast t = Toast.makeText(this, "learn success", Toast.LENGTH_SHORT);
            t.show();
        }
        return net;
    }

}
