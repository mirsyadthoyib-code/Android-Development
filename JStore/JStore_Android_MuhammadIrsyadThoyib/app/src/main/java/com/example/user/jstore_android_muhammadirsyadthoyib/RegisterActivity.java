package com.example.user.jstore_android_muhammadirsyadthoyib;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    final EditText nameInput = (EditText) findViewById(R.id.nameInput);
    final EditText emailInput = (EditText) findViewById(R.id.emailInput);
    final EditText passInput = (EditText) findViewById(R.id.passInput);
    final Button registerButton = (Button) findViewById(R.id.registerButton);

    private String fullName;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                fullName = nameInput.getText().toString();
                email = emailInput.getText().toString();
                password = passInput.getText().toString();

                RegisterRequest registerRequest = new RegisterRequest(fullName, email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);

                queue.add(registerRequest);
            }
        });
    }

    Response.Listener<String> responseListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonResponse = new JSONObject(response);
                if (jsonResponse != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Register Success").create().show();
                }
            }
            catch (JSONException e) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage("Register Failed").create().show();
            }
        }
    };
}
