
package com.example.administrator.promissionactivity;

import android.Manifest;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {
    private  final String TAG = getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 摇一摇的模块
     *
     * @param
     */
    @Permission(value=Manifest.permission.CAMERA)
    public void mShake(View view)
    {
            Log.i(TAG,"  摇到一个嫩模：  约不约");
    }

}
