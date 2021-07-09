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

import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CreateContactActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etMobile;
    private Button btnCreate;

    private AsyncHttpClient client;

    private String loginId, apikey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etMobile = findViewById(R.id.etMobile);
        btnCreate = findViewById(R.id.btnCreate);

        client = new AsyncHttpClient();


        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = getIntent();
                loginId = intent.getStringExtra("loginId");
                apikey = intent.getStringExtra("apikey");

                RequestParams params = new RequestParams();
                params.add("loginId", loginId);
                params.add("apikey", apikey);
                params.add("FirstName", etFirstName.getText().toString());
                params.add("LastName", etLastName.getText().toString());
                params.add("Mobile", etMobile.getText().toString());

                //TODO: call createContact.php to save new contact details
                client.post("http://10.0.2.2/C302_P08_SecuredCloudAddressBook/createContact.php", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        try {
                            Log.i("JSON Results: ", response.toString());

                            Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}