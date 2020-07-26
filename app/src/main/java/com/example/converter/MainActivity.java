package com.example.converter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submit();
    }
    public void input(View view)
    {
        EditText input = (EditText)findViewById(R.id.input);
        RadioButton decimal = (RadioButton)findViewById(R.id.decimal);
        RadioButton roman = (RadioButton)findViewById(R.id.roman);

        if(!roman.isChecked() && !decimal.isChecked()){
            Toast.makeText(MainActivity.this,"select a radio button",Toast.LENGTH_SHORT).show();
            return;
        }
        if(roman.isChecked())
        {
            String romanInput = input.getText()
                    .toString();
            if(romanInput == null || romanInput.isEmpty()){
                Toast.makeText(MainActivity.this,"Invalid input",Toast.LENGTH_SHORT).show();
            }
            else {
                int output = romanToDecimal(romanInput);
                if (output == -1) {
                    Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                } else {
                    display(view, String.valueOf(output));
                }
            }
        }

        if(decimal.isChecked()) {
            String decimalInput = input.getText().toString();
            if (decimalInput == null || !decimalInput.matches("[0-9]+")) {
                Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
            } else {
                try{
                    int decimalNumber = Integer.valueOf(decimalInput);
                    if (decimalNumber <= 0) {
                        Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                    } else {
                        String output = integerToRoman(decimalNumber);
                        display(view, output);
                    }
                }catch(NumberFormatException nfe) {
                    Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    public void submit()
    {
        Button submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input(view);
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        RadioButton decimal = (RadioButton)findViewById(R.id.decimal);
        RadioButton roman = (RadioButton)findViewById(R.id.roman);

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.roman:
                if (checked) {
                    decimal.setChecked(false);
                }
                break;
            case R.id.decimal:
                if (checked) {
                    roman.setChecked(false);
                }
                break;
        }
    }

    public void display(View view, String displayText)
    {
        TextView output = (TextView)findViewById(R.id.output);
        output.setText(displayText);
    }

    // utils
    private int value(char r)
    {
        if (r == 'I')
            return 1;
        if (r == 'V')
            return 5;
        if (r == 'X')
            return 10;
        if (r == 'L')
            return 50;
        if (r == 'C')
            return 100;
        if (r == 'D')
            return 500;
        if (r == 'M')
            return 1000;
        return -1;
    }

    // given romal numeral
    private int romanToDecimal(String str)
    {
        // Initialize result
        str = str.toUpperCase();
        int res = 0;

        for (int i = 0; i < str.length(); i++) {
            // Getting value of symbol s[i]
            int s1 = value(str.charAt(i));
            if(s1 == -1){
                return  -1;
            }

            // Getting value of symbol s[i+1]
            if (i + 1 < str.length()) {
                int s2 = value(str.charAt(i + 1));

                // Comparing both values
                if (s1 >= s2) {
                    // Value of current symbol
                    // is greater or equalto
                    // the next symbol
                    res = res + s1;
                }
                else {
                    // Value of current symbol is
                    // less than the next symbol
                    res = res + s2 - s1;
                    i++;
                }
            }
            else {
                res = res + s1;
                i++;
            }
        }

        return res;
    }
    public static String integerToRoman(int num) {

        if(num>3999){
            return "Numbers above 3999 are not supported";
        }
        System.out.println("Integer: " + num);
        int[] values = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
        String[] romanLiterals = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};

        StringBuilder roman = new StringBuilder();

        for(int i=0;i<values.length;i++) {
            while(num >= values[i]) {
                num -= values[i];
                roman.append(romanLiterals[i]);
            }
        }
        return roman.toString();
    }

}