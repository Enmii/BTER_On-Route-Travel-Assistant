package com.example.bterjavacode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class findRouteMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_route_menu);
        Button IL = findViewById(R.id.findRoute);
        IL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityinputLocation();
            }
        });

    }
    public void openActivityinputLocation(){
        Intent intent = new Intent(this, inputLocation.class);
        startActivity(intent);
    }
}