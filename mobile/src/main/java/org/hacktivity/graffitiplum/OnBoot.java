package org.hacktivity.graffitiplum;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OnBoot extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        // Create Intent
        Intent serviceIntent = new Intent(context, QueenService.class);
        // Start service
        context.startService(serviceIntent);

    }

}
