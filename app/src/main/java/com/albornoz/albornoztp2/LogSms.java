package com.albornoz.albornoztp2;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Telephony;
import android.util.Log;

public class LogSms extends Service {
    Thread tarea;
    public LogSms() {
    }

    @SuppressLint("Range")
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
                            new String[]{"date", "address", "body"},
                            null,
                            null,
                            "date desc"
                    );

                    if (cursor != null && cursor.getCount() > 0) {

                        int count = 0;
                        String number, body;

                        while (cursor.moveToNext() && count <= 4) {
                            // rescatar los campos del cursor
                            number = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS));
                            body = cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY));
                            Log.d("sms", "Tel:"+number+" Mensaje:"+body);
                            count++;
                        }

                        if (cursor != null) {
                            cursor.close();
                        }
                    }

                    try {
                        Thread.sleep(9000);
                    } catch (InterruptedException e) {
                        Log.d("catch_sleep", e.getMessage());
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