package com.example.mycompass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    //device sensor manager
    private SensorManager sensorManager;
    private Sensor compass;
    //define the display widget compass picture
    private ImageView image;
    private TextView compangle;
    //record the compass picture angle turned
    private float currentDegree= 0f;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image=(ImageView)findViewById(R.id.ivCompass);
        compangle=(TextView)findViewById(R.id.angle);
        sensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        compass=sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (compass!=null){
            sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_GAME);
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_GAME);
    }
    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    @Override
    public void onSensorChanged(SensorEvent event){
        //get the angle around the z-axis rotated
        float degree= Math.round(event.values[0]);
        compangle.setText("Heading:"+Float.toString(degree)+"degrees");
        //create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(currentDegree,-degree, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
       //how long the animation will take place
        ra.setDuration(210);
        //set the animation after the end of the reservation status
        ra.setFillAfter(true);
       //start the animation
        image.startAnimation(ra);
        currentDegree=-degree;
    }
    @Override
    public void onAccuracyChanged(Sensor sensor,int accuracy){
        //not in use
    }
}
