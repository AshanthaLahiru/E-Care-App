package com.example.ashantha.ecareapp;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HeartBeat extends AppCompatActivity {

    JSONParser jsonParser = new JSONParser();
    String API_URL="http://testapi.moracodex.com/";
    TextView hbText,name;

    String u_id;
    String u_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_beat);

        hbText=(TextView) findViewById(R.id.hbRate);

        Intent in=getIntent();

        name=(TextView) findViewById(R.id.hbname);

        u_id       =in.getStringExtra("id");
        u_name = in.getStringExtra("name");

        name.setText("User : "+u_name+"\nId : "+u_id );


        callAsynchronousTask();
    }


    public void callAsynchronousTask()
    {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try
                        {

                            new GetSensorData().execute();
                        }
                        catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 3000); //execute in every 50000 ms
    }


    class GetSensorData extends AsyncTask<String, String, String>
    {
        String hb;


        @Override
        protected String doInBackground(String... args)
        {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", u_id));

            JSONObject json = jsonParser.makeHttpRequest(API_URL,"POST", params);


try {

    hb = json.getString("hb");

    if(Integer.parseInt(hb)>100 || Integer.parseInt(hb)<60)
    {

        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    System.out.println("**********************************************************");
    System.out.println(json.toString());
}
catch (Exception e){}
            /*try
            {
                JSONObject json = jsonParser.makeHttpRequest(API_URL,"POST", params);



                hb          =   json.toString();


                System.out.println("**********************************************************");
                System.out.println(json.toString());

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }*/

            return null;
        }

        @Override
        protected void onPostExecute(String s)
        {

                hbText.setText(hb+" bpm");

        }
    }
}
