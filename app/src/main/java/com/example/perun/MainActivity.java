package com.example.perun;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPython();
    }

    private void initPython() {
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
    }

    private String getSolarCalc() {
        Python python = Python.getInstance();
        PyObject pythonFile = python.getModule("solarEclipseLib");
        return pythonFile.callAttr("solarCalc").toString();
    }

    public void printSolarCalc(View view) {
        TextView textView = findViewById(R.id.textView);
        textView.setText(getSolarCalc());
    }
}
