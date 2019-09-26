package com.example.sensores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }
    public void acelerometro(View view){
        Intent intent=new Intent(this, Acelerometro.class);
        startActivity(intent);
    }
    public void proximidad(View view){
        Intent intent=new Intent(this, Proximidad.class);
        startActivity(intent);
    }

    public void sensorLuz(View view){
    Intent intent=new Intent(this, SensorLuz.class);
    startActivity(intent);
    }

}
