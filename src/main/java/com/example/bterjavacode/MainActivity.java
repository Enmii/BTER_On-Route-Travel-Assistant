package com.example.bterjavacode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button GS = findViewById(R.id.getStarted);
        GS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityfindRouteMenu();
            }
        });
        Button PP = findViewById(R.id.privacyPolicy);
        PP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityPrivacyPolicy();
            }
        });


    }
    public void openActivityPrivacyPolicy(){
        Intent intent = new Intent(this, privacyPolicy.class);
        startActivity(intent);
        finish();
    }
    public void openActivityfindRouteMenu(){
        Intent intent = new Intent(this, findRouteMenu.class);
        startActivity(intent);
        finish();
    }


}