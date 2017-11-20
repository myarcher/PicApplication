package com.select.untils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

/**
 * Created by jyjin on 2017/10/31.
 */

public class LightSensorManager {
    private static LightSensorManager instance;
    private SensorManager mSensorManager;
    private LightSensorListener mLightSensorListener;
    CallBackListener backListener;
    Context context;
    private LightSensorManager(Context context,CallBackListener backListener) {
        this.backListener=backListener;
        this.context=context;
        start(context);
        
    }

    public static LightSensorManager getInstance(Context context,CallBackListener backListener) {
        if (instance == null) {
            instance = new LightSensorManager(context,backListener);
        }
        return instance;
    }

    public void start(Context context) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor lightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);// 获取光线传感器  
        if (lightSensor!= null) {// 光线传感器存在时  
            mLightSensorListener = new LightSensorListener();
            mSensorManager.registerListener(mLightSensorListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);// 注册事件监听  
        }
    }

  

    private class LightSensorListener implements SensorEventListener {
        private float lux;// 光线强度  
        
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public void onSensorChanged(SensorEvent event) {
         //   Toast.makeText(context,event.values[0]+"<><>"+event.sensor.getType(),Toast.LENGTH_SHORT).show();
            if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
// 获取光线强度 
                lux = event.values[0];
                backListener.back(lux);
            }
        }
    }
}
