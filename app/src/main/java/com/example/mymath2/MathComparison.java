package com.example.mymath2;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MathComparison extends AppCompatActivity {

    private TextView questionTextView;
    private TextView numbersTextView;
    private int number1, number2;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mathcomparison);

        questionTextView = findViewById(R.id.questionTextView);
        numbersTextView = findViewById(R.id.numbersTextView);
        random = new Random();
        generateNumbers();
    }
    public void onBiggerButtonClick(View view) {
        checkGuess(true);
    }

    public void onSmallerButtonClick(View view) {
        checkGuess(false);
    }

    private void generateNumbers() {
        number1 = random.nextInt(100) + 1; // Generates a random number between 1 and 100
        number2 = random.nextInt(100) + 1; // Generates another random number between 1 and 100
        questionTextView.setText("Comparison between " + number1 + " and " + number2 + " ,is " + number1 + " greater or smaller?");
        numbersTextView.setText(number1  +"\t"+"\t"+"\t"+"\t"+"\t"+"\t"+ number2); // Clear the numbers text
    }

    private void checkGuess(boolean isBigger) {
        String message;

        if ((isBigger && number1 > number2) || (!isBigger && number1 < number2)) {
            message = "The answer is correct.";
        } else {
            message = "The answer is incorrect.";
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        if (message.contains("The answer is correct")) {
            generateNumbers(); // Generate new numbers for the next round
        }
    }
}