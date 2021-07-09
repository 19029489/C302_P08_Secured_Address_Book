package com.example.c302_p08_secured_address_book;

import com.loopj.android.http.*;
import cz.msebera.android.httpclient.*;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.entity.mime.Header;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;

    private AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.editTextUsername);
        etPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);

        client = new AsyncHttpClient();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO (1) When Login button is clicked, call doLogin.php web service to check if the user is able to log in
                // What is the web service URL?
                // What is the HTTP method?
                // What parameters need to be provided?

                // TODO (2) Using AsyncHttpClient, check if the user has been authenticated successfully
                // If the user can log in, extract the id and API Key from the JSON object, set them into Intent and start MainActivity Intent.
                // If the user cannot log in, display a toast to inform user that login has failed.

                RequestParams params = new RequestParams();
                params.add("username", etUsername.getText().toString());
                params.add("password", etPassword.getText().toString());

                //for real devices, use the current location's ip address
                client.post("http://10.0.2.2/C302_P08_SecuredCloudAddressBook/doLogin.php", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                            try {
                                Log.i("JSON Results: ", response.toString());

                                Boolean authenticated = response.getBoolean("authenticated");

                                if (authenticated == true) {
                                    String apikey = response.getString("apikey");
                                    String id = response.getString("id");

                                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                    i.putExtra("loginId", id);
                                    i.putExtra("apikey", apikey);
                                    startActivity(i);

                                } else {
                                    Toast.makeText(getApplicationContext(), "Login has failed", Toast.LENGTH_SHORT).show();
                                }

                            }
                            catch (JSONException e){
                                e.printStackTrace();
                            }

                    }//end onSuccess
                });

            }
        });
    }



}