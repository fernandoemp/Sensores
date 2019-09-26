package com.example.sensores;


import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class Acelerometro extends AppCompatActivity implements SensorEventListener {
    /** Called when the activity is first created. */
    TextView x,y,z;
    private Sensor mAccelerometer;
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acelerometro);
        x = (TextView)findViewById(R.id.xID);
        y = (TextView)findViewById(R.id.yID);
        z = (TextView)findViewById(R.id.zID);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        btn1 = (Button) findViewById(R.id.btnAcelerom);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String spreadSheetId = "1mCa51N7llG0CdBkTBRgVbzAlYHE_kn8yiE4UwuBypP4";
                String tabla = "Acelerometro";
                String url = "https://script.google.com/macros/s/AKfycbxZ9dEZIWVREWcrl34dlGvUsMdq3SropTKoV2cKpdKnzNcUAFQ/exec";
                JSONObject json = new JSONObject();
                try {
                    json.put("spreadsheet_id",spreadSheetId);
                    json.put("sheet",tabla);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray lista = new JSONArray();
                JSONArray row1 = new JSONArray();
                row1.put(x.getText());
                row1.put(y.getText());
                row1.put(z.getText());
                lista.put(row1);
                try {
                    json.put("rows",lista);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestQueue queue = Volley.newRequestQueue(Acelerometro.this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                queue.add(jsonObjectRequest);
            }
        });
    }

        protected void onResume()
    {
        super.onResume();
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (sensors.size() > 0) //dispositivo android tiene acelerometro
        {
            sm.registerListener(this, sensors.get(0), SensorManager.SENSOR_DELAY_GAME);
        }
    }

    protected void onPause()
    {
        SensorManager mSensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorManager.unregisterListener(this, mAccelerometer);
        super.onPause();
    }

    protected void onStop()
    {
        SensorManager mSensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorManager.unregisterListener(this, mAccelerometer);
        super.onStop();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        this.x.setText("X = "+event.values[SensorManager.DATA_X]);
        this.y.setText("Y = "+event.values[SensorManager.DATA_Y]);
        this.z.setText("Z = "+event.values[SensorManager.DATA_Z]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
