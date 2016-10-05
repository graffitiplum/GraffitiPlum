package org.hacktivity.graffitiplum;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

public class QueenService extends Service implements DataApi.DataListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient googleApiClient;

    @Override
    public void onCreate() {

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API)
                .build();

        googleApiClient.connect();

        // TODO: add secondary localhost mysql database handling
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        // Receiver code
        Wearable.DataApi.addListener(googleApiClient, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {


        for (DataEvent event: dataEventBuffer) {

            //Toast.makeText(this, "onDataChanged", Toast.LENGTH_SHORT).show();

            String eventUri = event.getDataItem().getUri().toString();

            //Toast.makeText(this, eventUri, Toast.LENGTH_LONG).show();

            if (eventUri.contains ("randomData")) {

                // Received data
                DataMapItem dataItem = DataMapItem.fromDataItem (event.getDataItem());
                final String[] data = dataItem.getDataMap().getStringArray("contents");

                class SimpleThread extends Thread {
                    public SimpleThread(String str) {
                        super(str);
                    }

                    public void run() {
                        {
                            try {

                                // Update remote DB
                                Web.sendPost(
                                        "https://hacktivity.org/yellowjacket/pool.php",
                                        data[0]);
                            } catch (Exception e) {
                            }
                        }
                    }
                }
                new SimpleThread("st").start();


            }
        }


    }
}
