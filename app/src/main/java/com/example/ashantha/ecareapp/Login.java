package com.example.ashantha.ecareapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    EditText un;
    EditText pw;

    String email;
    String password;

    JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        un=(EditText) findViewById(R.id.un);
        pw=(EditText) findViewById(R.id.pw);

        Button login = (Button) findViewById(R.id.btnLogin);

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                LoginAuth();
            }
        });

    }

    class AuthenticateLogin extends AsyncTask<String, String, String>
    {
        String alert;
        String success;

        @Override
        protected String doInBackground(String... args)
        {
            String API_URL="http://testapi.moracodex.com/Validate.php";

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("email",email));
            params.add(new BasicNameValuePair("password",password));

            try
            {
                JSONObject json = jsonParser.makeHttpRequest(API_URL,"POST", params);



                success             =   json.getString("message");
                //String userName      =   json.getString("name");
                System.out.println(email+"******"+password+"*****************************"+success+json.getInt("id"));

                if(success.equalsIgnoreCase("true"))
                {
                    //JSONObject user     =   json.getJSONObject("data");
                    Intent i = new Intent(Login.this, MainActivity.class);

                    String u_id       =    json.getString("id");
                    //String u_email  = user.getString("email");
                    String u_name = json.getString("name");

                    i.putExtra("id",u_id);
                    i.putExtra("name",u_name);
                    //i.putExtra("u_full_name",u_full_name);

                    startActivity(i);
                    finish();
                }


            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s)
        {
            if (!success.equalsIgnoreCase("true"))
            {
                OpenDialogBox();
            }
        }
    }

    public void LoginAuth()
    {
        if (validateData())
        {
            new AuthenticateLogin().execute();
        }
    }

    public void GetInputData()
    {
        email = un.getText().toString();
        password = pw.getText().toString();
    }


    public void OpenDialogBox()
    {

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Invalid Credentials")
                .setMessage("Entered email or password is incorrect!")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })

                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    public boolean validateData()
    {
        GetInputData();

        if(un.length()==0)
        {
            un.setError("Email is Required !");
            return false;
        }

        if (password.length()==0)
        {
            pw.setError("Password is Required !");
            return false;
        }

        return true;
    }


    public void Register(View view){

        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}
