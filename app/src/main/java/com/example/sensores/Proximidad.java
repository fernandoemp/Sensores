package com.example.sensores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.LinearLayout;
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

public class Proximidad extends AppCompatActivity implements SensorEventListener {
    LinearLayout ln;
    SensorManager sm;
    Sensor sensor;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximidad);
        ln = (LinearLayout) findViewById(R.id.linear);
        tv = (TextView) findViewById(R.id.texto);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }
    protected void onResume()
    {
        super.onResume();
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = sm.getSensorList(Sensor.TYPE_PROXIMITY);
        if (sensors.size() > 0) //dispositivo android tiene acelerometro
        {
            sm.registerListener(this, sensors.get(0), SensorManager.SENSOR_DELAY_GAME);
        }
    }
    protected void onPause()
    {
        SensorManager mSensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorManager.unregisterListener(this, sensor);
        super.onPause();
    }
    protected void onStop()
    {
        SensorManager mSensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorManager.unregisterListener(this, sensor);
        super.onStop();
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        String texto1 = String.valueOf(event.values[0]);
        this.tv.setText(texto1);

        String spreadShetId = "1mCa51N7llG0CdBkTBRgVbzAlYHE_kn8yiE4UwuBypP4";
        String tablaAcelerom = "Proximidad";
        String url = "https://script.google.com/macros/s/AKfycbxZ9dEZIWVREWcrl34dlGvUsMdq3SropTKoV2cKpdKnzNcUAFQ/exec";
        JSONObject json = new JSONObject();
        try {
            json.put("spreadsheet_id", spreadShetId);
            json.put("sheet", tablaAcelerom);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray lista = new JSONArray();
        JSONArray row1 = new JSONArray();


        row1.put(tv.getText());

        lista.put(row1);
        try {
            json.put("rows", lista);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(Proximidad.this);
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




    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
