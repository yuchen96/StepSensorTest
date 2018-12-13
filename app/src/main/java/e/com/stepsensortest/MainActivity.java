package e.com.stepsensortest;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;



public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView tv_1;
    private TextView tv_2;
    private SensorManager msensorManager;
    protected int nowstep =0;
    protected int totalstep=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         tv_1 =(TextView)findViewById(R.id.tv_1);
         tv_2 =(TextView)findViewById(R.id.tv_2);
         msensorManager =(SensorManager)getSystemService(Context.SENSOR_SERVICE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        int i = 0;
        List<Sensor> sensorList =msensorManager.getSensorList(Sensor.TYPE_ALL);//List<>，<>里是泛型，指定Sensor对象
        for (Sensor sensor :sensorList){
            if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR){
                i=i+1;
            }
            else if (sensor.getType() == Sensor.TYPE_STEP_COUNTER){
                i=i+1;
            }
        if (i!=2){
                tv_1.setText("当前设备传感器不全");
        }
        else {
              msensorManager.registerListener(this,msensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR),SensorManager.SENSOR_DELAY_FASTEST);
              msensorManager.registerListener(this,msensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),SensorManager.SENSOR_DELAY_FASTEST);
              }

        }

        }

       @Override
       protected void onPause() {
        super.onPause();
        msensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_STEP_DETECTOR)
        {
            if(event.values[0]==1.0f)
                nowstep++;
        }
        else if (event.sensor.getType()==Sensor.TYPE_STEP_COUNTER){
            totalstep = (int)event.values[0];
        }
        String hint1 =String.format("本次共走了%d步",nowstep);
        tv_1.setText(hint1);
        String hint2 =String.format("自本次开机起一共走了%d步",totalstep);
        tv_2.setText(hint2);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
