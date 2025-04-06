package com.example.mymath2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Objects;

public class MathComposition extends AppCompatActivity{

    private int targetNumber;
    private ArrayList<Integer> answerOptions;
    private int firstSelectedOption = -1;
    private int secondSelectedOption = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mathcomposition);

        generateNumberAndAnswers();

        TextView targetNumberTextView = findViewById(R.id.targetNumberTextView);
        targetNumberTextView.setText(String.valueOf(targetNumber));

        Button option1Button = findViewById(R.id.option1Button);
        Button option2Button = findViewById(R.id.option2Button);
        Button option3Button = findViewById(R.id.option3Button);
        Button option4Button = findViewById(R.id.option4Button);

        // Set the background color to purple
        option1Button.setBackgroundColor(getResources().getColor(android.R.color.holo_purple));
        option2Button.setBackgroundColor(getResources().getColor(android.R.color.holo_purple));
        option3Button.setBackgroundColor(getResources().getColor(android.R.color.holo_purple));
        option4Button.setBackgroundColor(getResources().getColor(android.R.color.holo_purple));

        option1Button.setText(String.valueOf(answerOptions.get(0)));
        option2Button.setText(String.valueOf(answerOptions.get(1)));
        option3Button.setText(String.valueOf(answerOptions.get(2)));
        option4Button.setText(String.valueOf(answerOptions.get(3)));

        option1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOptionClick(option1Button, answerOptions.get(0));
            }
        });

        option2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOptionClick(option2Button, answerOptions.get(1));
            }
        });

        option3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOptionClick(option3Button, answerOptions.get(2));
            }
        });

        option4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOptionClick(option4Button, answerOptions.get(3));
            }
        });

        Button checkButton = findViewById(R.id.checkButton);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
    }

    private void generateNumberAndAnswers() {
        Random random = new Random();
        targetNumber = random.nextInt(100) + 1; // Generate a random number between 1 and 100

        // Generate two random numbers that can be added together to become the target number
        int firstNumber = random.nextInt(targetNumber);
        int secondNumber = targetNumber - firstNumber;

        answerOptions = new ArrayList<>();
        answerOptions.add(firstNumber);
        answerOptions.add(secondNumber);

        // Generate three other random numbers that are smaller than the target number
        for (int i = 0; i < 3; i++) {
            int option;
            do {
                option = random.nextInt(targetNumber); // Generate a random number
            } while (option == firstNumber || option == secondNumber); // Ensure it's different from the correct answer options
            answerOptions.add(option);
        }

        // Shuffle the elements of the answerOptions ArrayList
        Collections.shuffle(answerOptions);
    }

    private void handleOptionClick(Button optionButton, int option) {
        if (firstSelectedOption == option) {
            // If the option is already selected, deselect it
            firstSelectedOption = -1;
            optionButton.setBackgroundColor(getResources().getColor(android.R.color.holo_purple));
        } else if (secondSelectedOption == option) {
            // If the option is already selected, deselect it
            secondSelectedOption = -1;
            optionButton.setBackgroundColor(getResources().getColor(android.R.color.holo_purple));
        } else if (firstSelectedOption == -1) {
            firstSelectedOption = option;
            optionButton.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
        } else if (secondSelectedOption == -1) {
            secondSelectedOption = option;
            optionButton.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
        } else {
            // Clear previously selected options if two options are already selected
            firstSelectedOption = -1;
            secondSelectedOption = -1;
            resetOptionButtonBackgrounds();
        }
    }

    private void checkAnswer() {
        if (firstSelectedOption != -1 && secondSelectedOption != -1) {
            int sum = firstSelectedOption + secondSelectedOption;
            if (sum == targetNumber) {
                Toast.makeText(MathComposition.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                generateNumberAndAnswers();
                refreshUI();
                firstSelectedOption = -1;
                secondSelectedOption = -1;
            } else {
                Toast.makeText(MathComposition.this, "Incorrect Answer . Please try again!", Toast.LENGTH_SHORT).show();
                firstSelectedOption = -1;
                secondSelectedOption = -1;
            }
            resetOptionButtonBackgrounds(); // Reset button backgrounds after checking answer
        } else {
            Toast.makeText(MathComposition.this, "Select two options first!", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetOptionButtonBackgrounds() {
        Button option1Button = findViewById(R.id.option1Button);
        Button option2Button = findViewById(R.id.option2Button);
        Button option3Button = findViewById(R.id.option3Button);
        Button option4Button = findViewById(R.id.option4Button);

        // Set the background color to purple
        option1Button.setBackgroundColor(getResources().getColor(android.R.color.holo_purple));
        option2Button.setBackgroundColor(getResources().getColor(android.R.color.holo_purple));
        option3Button.setBackgroundColor(getResources().getColor(android.R.color.holo_purple));
        option4Button.setBackgroundColor(getResources().getColor(android.R.color.holo_purple));
    }

    private void refreshUI() {
        TextView targetNumberTextView = findViewById(R.id.targetNumberTextView);
        targetNumberTextView.setText(String.valueOf(targetNumber));

        Button option1Button = findViewById(R.id.option1Button);
        Button option2Button = findViewById(R.id.option2Button);
        Button option3Button = findViewById(R.id.option3Button);
        Button option4Button = findViewById(R.id.option4Button);

        option1Button.setText(String.valueOf(answerOptions.get(0)));
        option2Button.setText(String.valueOf(answerOptions.get(1)));
        option3Button.setText(String.valueOf(answerOptions.get(2)));
        option4Button.setText(String.valueOf(answerOptions.get(3)));
    }
}

