package com.example.mymath2;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MathOrdering extends AppCompatActivity{

    private LinearLayout numbersContainer;
    private ArrayList<Integer> numbersList;
    private TextView instructionTextAscending;
    private TextView instructionTextDescending;
    private Button checkButton;

    // Variables to store information about the currently dragged item
    private TextView draggedItem;
    private int draggedItemIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mathordering);

        numbersContainer = findViewById(R.id.numbersContainer);
        instructionTextAscending = findViewById(R.id.instructionTextAscending);
        instructionTextDescending = findViewById(R.id.instructionTextDescending);
        checkButton = findViewById(R.id.checkButton);

        numbersList = generateRandomNumbers(5); // Generate 5 random numbers
        // Decide whether to sort in ascending or descending order randomly
        boolean ascendingOrder = new Random().nextBoolean();
        updateInstructionText(ascendingOrder);
        displayNumbers();

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
    }

    private ArrayList<Integer> generateRandomNumbers(int count) {
        ArrayList<Integer> numbers = new ArrayList<>();
        Random random = new Random();
        while (numbers.size() < count) {
            int randomNumber = random.nextInt(100); // Generate random numbers between 0 and 99
            if (!numbers.contains(randomNumber)) {
                numbers.add(randomNumber);
            }
        }
        return numbers;
    }

    private void displayNumbers() {
        for (int i = 0; i < numbersList.size(); i++) {
            TextView textView = createTextView(numbersList.get(i));
            textView.setTag(i); // Set tag to identify the position of the TextView in the list
            numbersContainer.addView(textView);
        }
    }

    private TextView createTextView(int number) {
        TextView textView = new TextView(this);
        textView.setText(String.valueOf(number));
        textView.setPadding(16, 16, 16, 16); // Add padding for better visual separation
        textView.setTextSize(24); // Set text size to 24sp
        textView.setTextColor(Color.BLACK); // Set text color to black
        textView.setBackground(getResources().getDrawable(R.drawable.rounded_rectangle)); // Set background shape
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8, 0, 8, 0); // Add margins between TextViews
        textView.setLayoutParams(layoutParams);
        textView.setOnTouchListener(new NumberTouchListener());
        textView.setOnDragListener(new NumberDragListener());
        return textView;
    }

    private class NumberTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                // Start drag operation
                draggedItem = (TextView) v;
                draggedItemIndex = (int) v.getTag();
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(null, shadowBuilder, v, 0);
                return true;
            }
            return false;
        }
    }

    private class NumberDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DROP:
                    // Get the TextView where the item is dropped
                    TextView dropTarget = (TextView) v;
                    int dropTargetIndex = (int) dropTarget.getTag();

                    // Swap the positions of the dragged item and the drop target
                    Collections.swap(numbersList, draggedItemIndex, dropTargetIndex);

                    // Remove all views from the container and display numbers again
                    numbersContainer.removeAllViews();
                    displayNumbers();

                    // Reset variables
                    draggedItem = null;
                    draggedItemIndex = -1;

                    break;
            }
            return true;
        }
    }


    private void updateInstructionText(boolean ascendingOrder) {
        if (ascendingOrder) {
            instructionTextAscending.setVisibility(View.VISIBLE);
            instructionTextDescending.setVisibility(View.GONE);
        } else {
            instructionTextAscending.setVisibility(View.GONE);
            instructionTextDescending.setVisibility(View.VISIBLE);
        }
    }

    private void checkAnswer() {
        ArrayList<Integer> sortedNumbers = new ArrayList<>(numbersList);

        // Sort in ascending or descending order based on the previous instruction
        if (instructionTextAscending.getVisibility() == View.VISIBLE) {
            Collections.sort(sortedNumbers);
        } else {
            Collections.sort(sortedNumbers, Collections.reverseOrder());
        }

        if (numbersList.equals(sortedNumbers)) {
            // Correct answer
            Toast.makeText(MathOrdering.this, "Correct Answer", Toast.LENGTH_SHORT).show();
            regenerateNumbers();
        } else {
            // Wrong answer
            Toast.makeText(MathOrdering.this, "Wrong Answer", Toast.LENGTH_SHORT).show();
            // Allow 2 seconds for the "Wrong Answer" message to display before resetting
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // displayNumbers(); // Reset the numbers to their original positions
                }
            }, 2000);
        }
    }

    private void regenerateNumbers() {
        boolean ascendingOrder = new Random().nextBoolean();
        updateInstructionText(ascendingOrder);
        numbersList.clear(); // Clear the existing numbers
        numbersList.addAll(generateRandomNumbers(5)); // Generate new random numbers
        numbersContainer.removeAllViews(); // Remove all views from the container
        displayNumbers(); // Display the new set of numbers
    }
}
