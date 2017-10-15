package com.example.ashantha.ecareapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity {

    EditText un;
    EditText pw;
    EditText nme;

    String email;
    String name;
    String password;

    JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        un=(EditText) findViewById(R.id.run);
        pw=(EditText) findViewById(R.id.rpw);
        nme=(EditText) findViewById(R.id.rnm);

        Button register = (Button) findViewById(R.id.btnRegister);

        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                LoginAuth();

                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });

    }

    class PatientRegister extends AsyncTask<String, String, String>
    {


        @Override
        protected String doInBackground(String... args)
        {
            String API_URL="http://testapi.moracodex.com/Register.php";

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("email",email));
            params.add(new BasicNameValuePair("password",password));
            params.add(new BasicNameValuePair("name",name));

            JSONObject json = jsonParser.makeHttpRequest(API_URL,"POST", params);


            return null;
        }

        @Override
        protected void onPostExecute(String s)
        {

        }
    }

    public void LoginAuth()
    {
        if (validateData())
        {
            new Register.PatientRegister().execute();
        }
    }

    public void GetInputData()
    {
        email = un.getText().toString();
        password = pw.getText().toString();
        name = nme.getText().toString();
    }

    /*
    public void OpenDialogBox(String msg)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        View v = getLayoutInflater().inflate(R.layout.dialog, null);
        TextView title = (TextView) v.findViewById(R.id.dialog_title);
        title.setText(getResources().getString(R.string.app_name));

        TextView info = (TextView) v.findViewById(R.id.dialog_info);
        info.setText(msg);
        builder.setView(v);

        builder.setNegativeButton(getResources().getString(R.string.btn_ok), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });

        builder.show();

    }*/

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

        if (nme.length()==0)
        {
            nme.setError("Name is Required !");
            return false;
        }

        return true;
    }
}
