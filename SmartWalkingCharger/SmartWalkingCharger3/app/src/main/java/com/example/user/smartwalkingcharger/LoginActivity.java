package com.example.user.smartwalkingcharger;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Set;

public class LoginActivity extends AppCompatActivity {

    private Button mBtLaunchActivity;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initBluetooth();
        mBtLaunchActivity = findViewById(R.id.signinbutton);

        mBtLaunchActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchActivity();
            }
        });

    }


    private  void initBluetooth() {
        BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {

            Toast.makeText(getApplicationContext(),"Device doesnt Support Bluetooth",Toast.LENGTH_SHORT).show();

        }
        else
        {
            if(!bluetoothAdapter.isEnabled())
            {

                Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

                startActivityForResult(enableAdapter, 0);

            }
        }

    }

    private void launchActivity() {

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
