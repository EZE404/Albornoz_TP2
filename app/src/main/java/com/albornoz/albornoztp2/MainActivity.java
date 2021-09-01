package com.albornoz.albornoztp2;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pedirPermisos();
        arrancarServicio();
    }

    private void pedirPermisos() {
        if(
                Build.VERSION.SDK_INT>= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(new String[]{Manifest.permission.READ_SMS},1000);
        }
    }

    private void arrancarServicio() {
        i = new Intent(this, LogSms.class);
        this.startService(i);
    }

    @Override
    protected void onDestroy() {
        this.stopService(i);
        super.onDestroy();
    }
}