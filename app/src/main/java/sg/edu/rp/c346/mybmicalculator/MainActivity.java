package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight, etHeight;
    Button calc, reset;
    TextView date, BMI,outcome;

    String dateTime = "";
    float bmi = 0;
    String result ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);
        calc = findViewById(R.id.btnCalc);
        reset = findViewById(R.id.btnRst);
        date = findViewById(R.id.tvDate);
        BMI = findViewById(R.id.tvBMI);
        outcome = findViewById(R.id.tvOutcome);

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float kg = Float.parseFloat(etWeight.getText().toString());
                float m = Float.parseFloat(etHeight.getText().toString());

                bmi = kg / (m * m);

                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                dateTime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH) + 1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                if(bmi <18.5){
                  result = "You are underweight";
                }else if(bmi >= 18.5 && bmi <=24.9){
                    result = "Your BMI is normal";
                }else if(bmi >=25 && bmi <=29.9){
                    result = "You are overweight";
                }else{
                    result = "You are obese";
                }
                outcome.setText(result);
                date.setText("Your calculated date: "+dateTime);
                BMI.setText(String.format("%s %.3f", "Last Calculated BMI: ", bmi));
                saveData();

            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSavedData();

                date.setText("");
                BMI.setText("");
                etWeight.setText("");
                etHeight.setText("");
                outcome.setText("");
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        float getBmi = prefs.getFloat("bmi", 0);
        String getDateTime = prefs.getString("dateTime", "");
        String re = prefs.getString("out","");

        outcome.setText(re);
        date.setText("Your calculated date: "+getDateTime);
        BMI.setText("Your calculated BMI: "+getBmi);
    }


    private void saveData() {
        Toast.makeText(this, "Data saved!", Toast.LENGTH_SHORT).show();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor prefsEdit = prefs.edit();
        prefsEdit.putFloat("bmi", bmi);
        prefsEdit.putString("dateTime", dateTime);
        prefsEdit.putString("out",result);
        prefsEdit.commit();
    }

    private void deleteSavedData(){
        Toast.makeText(this, "Data deleted!", Toast.LENGTH_SHORT).show();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor prefsEdit = prefs.edit();
        prefsEdit.putFloat("bmi", 0);
        prefsEdit.putString("dateTime", " ");
        prefsEdit.putString("out"," ");
        prefsEdit.commit();
    }

}
