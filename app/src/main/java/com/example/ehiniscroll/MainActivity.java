package com.example.ehiniscroll;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button myButton = findViewById(R.id.myButton);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Button Clicked!");

                if (atMiddleOfScreen(myButton)) {
                    changeButtonPropertiesToLinked(myButton);
                    changeButtonPositionToTopLeft(myButton);
                } else {
                    setContentView(R.layout.activity_main);
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

    private Boolean atMiddleOfScreen(Button button) {
        String buttonText = button.getText().toString();

        if (buttonText.equals("Click To Start Link")) {
           return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    private void changeButtonPropertiesToUnlinked(Button button) {
        button.setText("Click To Start Link");

        LayoutParams params = new LayoutParams(200, 150);
        params.setMargins(0, 16, 0, 0);

        button.setLayoutParams(params);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}