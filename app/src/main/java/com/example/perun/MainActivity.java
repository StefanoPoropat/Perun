package com.example.perun;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.textView);
        String content = readTextFile("solar_eclipse_data.txt");
        textView.setText(content);
    }

    private String readTextFile(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream inputStream = getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length > 1) {
                    stringBuilder.append(parts[0]).append(" ").append(parts[1]).append("\n");
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error reading file";
        }
        return stringBuilder.toString();
    }
}
