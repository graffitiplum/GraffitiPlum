package org.hacktivity.graffitiplum;

import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;


class DataTask extends AsyncTask<Node, Void, Void> {

    private final String[] contents;
    //private MyListener myListener;
    GoogleApiClient myGoogleApiClient;
    Context c;

    public DataTask(Context c, String[] contents, GoogleApiClient googleApiClient) {
        this.c = c;
        this.contents = contents;
        this.myGoogleApiClient = googleApiClient;
        //this.myListener = myListener;
    }

    @Override
    protected Void doInBackground(Node... nodes) {

        PutDataMapRequest dataMap = PutDataMapRequest.create ("/Stinger/randomData");
        dataMap.getDataMap().putStringArray("contents", contents);

        PutDataRequest request = dataMap.asPutDataRequest();

        DataApi.DataItemResult dataItemResult = Wearable.DataApi
                .putDataItem(myGoogleApiClient, request).await();


        //Log.d ("[DEBUG] SendDataCoolTask - doInBackground", "/myapp/myevent" status, "+getStatus());
        return null;
    }
}