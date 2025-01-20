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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
     * Formats date and truncates unnecessary decimal places from separation value.
     *
     * @return A formatted string containing the contents of the file.
     */
    private String readDataFromAssets() {
        StringBuilder data = new StringBuilder();
        AssetManager assetManager = getAssets();
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());

        try (InputStream inputStream = assetManager.open("solar_eclipse_data.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                // Log each line to check if it's being read correctly
                Log.d("FileReading", "Read line: " + line);

                // Parse each line to extract the date and integer part of the separation value
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    String dateStr = parts[0] + " " + parts[1]; // Combine date and time
                    String sep = String.format(Locale.getDefault(), "%.2f", Double.parseDouble(parts[1])); // Format to 2 decimal places

                    try {
                        Date date = inputFormat.parse(dateStr);
                        if (date != null) {
                            String formattedDate = outputFormat.format(date); // Reformat date
                            data.append(formattedDate).append("   ").append(sep).append("°\n"); // Append to output
                        }
                    } catch (ParseException e) {
                        Log.e("DateParsing", "Error parsing date: " + e.getMessage());
                    }
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
