package com.example.ehiniscroll;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ButtonInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button myButton = findViewById(R.id.myButton);
        Map<String, Integer> unlinkedButtonInfo = getButtonInfo(myButton);
        for (Map.Entry<String, Integer> entry : unlinkedButtonInfo.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            System.out.println(key + ": " + value);
        }


        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Button Clicked!");
                Map<String, Integer> currentButtonInfo = getButtonInfo(myButton);
                currentButtonInfo.put("yPos", 0);
                currentButtonInfo.put("xPos", 0);

                for (Map.Entry<String, Integer> entry : currentButtonInfo.entrySet()) {
                    String key = entry.getKey();
                    int value = entry.getValue();
                    System.out.println(key + ": " + value);
                }

                if (currentButtonInfo.equals(unlinkedButtonInfo)) {
                    System.out.println("I was here");
                    changeButtonPropertiesToLinked(myButton);
                    changeButtonPositionToTopLeft(myButton);
                } else {
                    setButtonProperties(myButton, unlinkedButtonInfo);
                }
            }
        });
    }

    private void changeButtonPropertiesToLinked(Button button) {
        button.setText("Click To Stop link");

        LayoutParams params = new LayoutParams(300, 200);
        button.setLayoutParams(params);
    }

    private void changeButtonPositionToTopLeft(Button button) {
        LayoutParams params = (LayoutParams) button.getLayoutParams();

        params.setMargins(50, 100, 0, 0);
        button.setLayoutParams(params);
    }

    private Map<String, Integer> getButtonInfo(Button button) {
        Map<String, Integer> buttonInfo = new HashMap<>();

        // Get text
        String buttonText = button.getText().toString();
        Log.d(TAG, "Button Text: " + buttonText);

        // Get layout parameters
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)
                button.getLayoutParams();
        Integer width = params.width;
        Integer height = params.height;

        buttonInfo.put("width", width);
        buttonInfo.put("height", height);

        Log.d(TAG, "Button Width: " + width + "px, Height: " + height + "px");

        // Get position
        int[] location = new int[2];
        button.getLocationOnScreen(location);
        Integer x = location[0];
        Integer y = location[1];

        buttonInfo.put("xPos", x);
        buttonInfo.put("yPos", y);

        Log.d(TAG, "Button Position: x=" + x + "px, y=" + y + "px");

        return buttonInfo;
    }

    private void setButtonProperties(Button button, Map<String, Integer> map) {
        Integer width = map.get("width");
        Integer height = map.get("height");
        Integer xPos = map.get("xPos");
        Integer yPos = map.get("yPos");

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        params.setMargins(xPos, yPos, 0, 0);

        button.setLayoutParams(params);
    }

//    private void setButtonPositionToMiddle(Button button) {
//        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
//
//        ConstraintSet constraintSet = new ConstraintSet();
//        constraintSet.clone(constraintLayout);
//
//        // Set constraints to center the button
//        constraintSet.connect(myButton.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
//        constraintSet.connect(myButton.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
//        constraintSet.connect(myButton.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
//        constraintSet.connect(myButton.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
//
//        // Apply the constraints
//        constraintSet.applyTo(constraintLayout);
//    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}