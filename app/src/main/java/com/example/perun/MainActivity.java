package com.example.perun;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
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
        setContentView(R.layout.activity_main); // Set the layout

        // Find the TextView to display the data
        TextView textView = findViewById(R.id.textView);

        // Read data from the file and log it
        String eclipseData = readDataFromAssets();
        Log.d("MainActivity", "Data to display: " + eclipseData); // Log the data to be set to the TextView

        // Set the text to the TextView
        textView.setText(eclipseData);
    }

    /**
     * Reads data from the solar_eclipse_data.txt file in the assets folder.
     * The file should contain lines with a date and a separation value.
     * The method removes the fractional part of the separation value for display.
     *
     * @return A formatted string containing the contents of the file.
     */
    private String readDataFromAssets() {
        StringBuilder data = new StringBuilder();
        AssetManager assetManager = getAssets();

        try (InputStream inputStream = assetManager.open("solar_eclipse_data.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                // Log each line to check if it's being read correctly
                Log.d("FileReading", "Read line: " + line);

                // Parse each line to extract the date and integer part of the separation value
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    String date = parts[0]; // The date string
                    String sep = parts[1].split("\\.")[0]; // Integer part of the separation value
                    data.append(date).append("   ").append(sep).append("\n"); // Format output
                }
            }
        } catch (IOException e) {
            // Log the error if the file can't be read
            Log.e("FileReading", "Error reading file: " + e.getMessage());
            data.append("Error reading file: ").append(e.getMessage());
        }

        // Log the final data to check if it's correctly built
        Log.d("FileReading", "Final data: " + data.toString());

        return data.toString(); // Return the formatted data
    }
}
