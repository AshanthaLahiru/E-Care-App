package com.example.ashantha.ecareapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    TextView name;
    String u_id;
    String u_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent in=getIntent();

        u_id       =in.getStringExtra("id");
        u_name = in.getStringExtra("name");

        //name.setText("Hello, "+u_name);
        OpenDialogBox();

    }

    public void HeartBeat(View view){

        Intent intent = new Intent(this, HeartBeat.class);


        intent.putExtra("id",u_id);
        intent.putExtra("name",u_name);

        startActivity(intent);
    }

    public void viewLog(View view){

        Intent intent = new Intent(this, Vision.class);


        intent.putExtra("id",u_id);
        intent.putExtra("name",u_name);

        startActivity(intent);
    }

    public void OpenDialogBox()
    {

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Hello, "+u_name)
                .setMessage("I'm your ECare guidance system. You can check your health status from this application. If there is" +
                        " an unusual situation with your body, I'll inform you.")
                .setPositiveButton("Got It", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })

                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

}


