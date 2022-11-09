package com.relintonpinheirodev.consultordeenderecos.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BroadcastBateria extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra("level", 0);
        Log.i("Script", "bateria: "+level+"%");
        if(level < 50)
        {
            Toast.makeText(context.getApplicationContext(), "Seu dispositivo está com "+level+"% de bateria", Toast.LENGTH_SHORT).show();
        }
    }
}

