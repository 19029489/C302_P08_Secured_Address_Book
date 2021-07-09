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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewContactDetailsActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etMobile;
    private Button btnUpdate, btnDelete;
    private int contactId;

    private AsyncHttpClient client;

    private String loginId, apikey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact_details);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etMobile = findViewById(R.id.etMobile);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        Intent intent = getIntent();
        contactId = intent.getIntExtra("contact_id", -1);
        loginId = intent.getStringExtra("loginId");
        apikey = intent.getStringExtra("apikey");

        client = new AsyncHttpClient();

        RequestParams params = new RequestParams();
        params.add("loginId", loginId);
        params.add("apikey", apikey);

        //for real devices, use the current location's ip address
        client.post("http://10.0.2.2/C302_P08_SecuredCloudAddressBook/getContactDetails.php?id=" + contactId, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    Log.i("JSON Results: ", response.toString());

                    etFirstName.setText(response.getString("firstname"));
                    etLastName.setText(response.getString("lastname"));
                    etMobile.setText(response.getString("mobile"));
                }
                catch (JSONException e){
                    e.printStackTrace();
                }

            }//end onSuccess
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = new RequestParams();
                params.add("loginId", loginId);
                params.add("apikey", apikey);
                params.add("id", "" + contactId);
                params.add("FirstName", etFirstName.getText().toString());
                params.add("LastName", etLastName.getText().toString());
                params.add("Mobile", etMobile.getText().toString());

                client.post("http://10.0.2.2/C302_P08_SecuredCloudAddressBook/updateContact.php", params, new JsonHttpResponseHandler() {
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

                    }//end onSuccess
                });

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = new RequestParams();
                params.add("loginId", loginId);
                params.add("apikey", apikey);
                params.add("id", "" + contactId);

                client.post("http://10.0.2.2/C302_P08_SecuredCloudAddressBook/deleteContact.php", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        try {
                            Log.i("JSON Results: ", response.toString());

                            Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }//end onSuccess
                });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Code for step 1 start
        Intent intent = getIntent();
        contactId = intent.getIntExtra("contact_id", -1);
        loginId = intent.getStringExtra("loginId");
        apikey = intent.getStringExtra("apikey");
    }
}