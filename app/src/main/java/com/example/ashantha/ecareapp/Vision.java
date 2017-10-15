package com.example.ashantha.ecareapp;

import android.content.Intent;
import android.graphics.Color;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Vision extends AppCompatActivity {


    JSONParser jsonParser = new JSONParser();
    String API_URL="http://testapi.moracodex.com/";
    TextView visionHB,visionName,visionStatus,visionPressure;

    String u_id;
    String u_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision);

        visionHB=(TextView) findViewById(R.id.visionHB);
        visionStatus=(TextView) findViewById(R.id.visionStatus);
        visionName=(TextView) findViewById(R.id.visionName);
        visionPressure=(TextView) findViewById(R.id.pressureTV);

        Intent in=getIntent();

        u_id       =in.getStringExtra("id");
        u_name = in.getStringExtra("name");

        visionName.setText(u_name);


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

                            new Vision.GetSensorData().execute();
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
        String hb,pres;


        @Override
        protected String doInBackground(String... args)
        {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", u_id));

            JSONObject json = jsonParser.makeHttpRequest(API_URL,"POST", params);


            try {

                hb = json.getString("hb");
                pres = json.getString("pressure");
                if((Integer.parseInt(hb)>100 || Integer.parseInt(hb)<60) || (Integer.parseInt(pres)>120 || Integer.parseInt(pres)<90))
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

            visionHB.setText(hb + " BPM");
            visionPressure.setText(pres+ "mm Hg");

            {
                visionStatus.setText("Status : Normal");
                visionHB.setTextColor(Color.parseColor("#000000"));
                visionStatus.setTextColor(Color.parseColor("#000000"));
                visionPressure.setTextColor(Color.parseColor("#000000"));
            }
            if(Integer.parseInt(hb)>100 || Integer.parseInt(hb)<60) {
                visionStatus.setText("Status : Critical Situation");
                visionHB.setTextColor(Color.parseColor("#ff0000"));
                visionStatus.setTextColor(Color.parseColor("#ff0000"));
            }
            if(Integer.parseInt(pres)>120 || Integer.parseInt(pres)<90) {
                visionStatus.setText("Status : Critical Situation");
                visionPressure.setTextColor(Color.parseColor("#ff0000"));
                visionStatus.setTextColor(Color.parseColor("#ff0000"));
            }




        }


    }
}
