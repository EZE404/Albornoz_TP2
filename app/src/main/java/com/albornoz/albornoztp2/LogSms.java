package com.albornoz.albornoztp2;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

public class LogSms extends Service {

    Thread tarea;

    public LogSms() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Uri mensajes = Uri.parse("content://sms/inbox");
        ContentResolver contentResolver = getContentResolver();

        tarea = new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    Cursor cursor = contentResolver.query(
                            mensajes,
                            null,
                            null,
                            null,
                            "date desc"
                    );

                    if (cursor != null && cursor.getCount() > 0) {

                        int count = 0;
                        String date, person, body;

                        while (cursor.moveToNext() && count <= 4) {
                            // rescatar los campos del cursor
                            date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                            person = cursor.getString(cursor.getColumnIndexOrThrow("person"));
                            body = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                            Log.d("SMS", date + " / " + person + "/n/t" + body + "/n");
                            count++;
                        }
                    }

                    if (cursor != null) {
                        cursor.close();
                    }

                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        Log.d("CATCH_SLEEP", e.getMessage());
                        break;
                    }
                }

            }
        });

        tarea.start();
        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}