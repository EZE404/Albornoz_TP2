package com.albornoz.albornoztp2;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button start;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pedirPermisos();
        i = new Intent(this, LogSms.class);
        start = findViewById(R.id.startService);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.startService(i);
                start.setEnabled(false);
                Toast.makeText(MainActivity.this, "Servicio ejecutado", Toast.LENGTH_SHORT).show();
            }
        });
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

    @Override
    protected void onDestroy() {
        this.stopService(i);
        super.onDestroy();
    }


}