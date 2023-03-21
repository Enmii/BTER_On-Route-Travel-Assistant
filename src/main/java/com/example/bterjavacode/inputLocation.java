package com.example.bterjavacode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.HashMap;
import java.util.List;
import java.lang.*;
import java.util.Map;


public class inputLocation extends AppCompatActivity {

    EditText sourceNode;
    EditText targetNode;
    TextView textView5;
    public String source, target;
    public double distance;
    static final Map<String,Character> dest = new HashMap<String,Character>();
    static{
        dest.put("SOUTH STATION",'A');
        dest.put("VILLAGE SQUARE",'B');
        dest.put("PUREGOLD",'C');
        dest.put("PERPETUAL HELP HOSPITAL",'D');
        dest.put("MAMA LOU'S",'E');
        dest.put("EL GRANDE",'F');
        dest.put("BF SOUTHLAND CLASSIC",'G');
        dest.put("FESTIVAL MALL", 'H');
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_location);
        Button SR = findViewById(R.id.shortestRoute);
        Button CR = findViewById(R.id.cheapestRoute);
        Button VM = findViewById(R.id.viewMap);

        sourceNode = (EditText)findViewById(R.id.sourceNode);
        targetNode = (EditText)findViewById(R.id.targetNode);
        textView5 = (TextView)findViewById(R.id.textView5);

        Editable s = sourceNode.getText();
        Editable t = targetNode.getText();

        //Startup python module
        if(!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }
        Python py = Python.getInstance();
        //Call AStarAlgorithm.py
        final PyObject aStar = py.getModule("AStarAlgorithm");
        final PyObject cheapest = py.getModule("CheapestPath");

        SR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call main method of AStarAlgorithm.py
                String path;
                source = s.toString().toUpperCase();
                target = t.toString().toUpperCase();
                try{
                    PyObject obj = aStar.callAttr("main", dest.get(source), dest.get(target));
                    path = Astar.aStarAlgo(dest.get(source), dest.get(target));

                    textView5.setText("Shortest Path Found: " + path + " Weight: " + obj);
                }
                catch(Exception e){
                    textView5.setText("Invalid Input!");
                }

            }
        });
        CR.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                try{
                    source = s.toString().toUpperCase();
                    target = t.toString().toUpperCase();
                    PyObject obj = cheapest.callAttr("main", dest.get(source), dest.get(target));
                    textView5.setText("Cheapest Path Found w/ calculated fare:" + obj.toString());
                }
                catch(Exception e){
                    textView5.setText("Invalid Input!");
                }

            }
        });
        VM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //View map for user reference
                viewMap();
            }
        });
    }

    public void openoptimizingRoute(){
        Intent intent = new Intent(this, optimizingRoute.class);
        startActivity(intent);
    }
    public void viewMap(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

}