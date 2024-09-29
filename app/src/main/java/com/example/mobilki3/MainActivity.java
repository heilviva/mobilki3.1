package com.example.mobilki3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button[] numberButtons = new Button[10];
    Button[] operationButtons = new Button[5];
    EditText inputEdit;
    TextView outputView;
    String input = "";
    String operation = "";
    double result = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputEdit = findViewById(R.id.inputEdit);
        outputView = findViewById(R.id.outputView);


        for (int i = 0; i < 10; i++) {
            numberButtons[i] = findViewById(getResources().getIdentifier("button" + i, "id", getPackageName()));
            numberButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    input += ((Button) v).getText().toString();
                    inputEdit.setText(input);
                }
            });
        }


        operationButtons[0] = findViewById(R.id.forplus);
        operationButtons[1] = findViewById(R.id.forminus);
        operationButtons[2] = findViewById(R.id.forumn);
        operationButtons[3] = findViewById(R.id.fordelenie);
        operationButtons[4] = findViewById(R.id.equalsButton);

        for (Button button : operationButtons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == R.id.equalsButton) {
                        calculateResult();
                    } else {
                        operation = ((Button) v).getText().toString();
                        input += operation;
                        inputEdit.setText(input);
                    }
                }
            });
        }


        Button logButton = findViewById(R.id.logButton);
        Button sqrtButton = findViewById(R.id.sqrtButton);
        Button percentButton = findViewById(R.id.percentButton);
        Button clearButton = findViewById(R.id.clearButton);

        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input += "log(";
                inputEdit.setText(input);
            }
        });

        sqrtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input += "sqrt(";
                inputEdit.setText(input);
            }
        });

        percentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input += "%";
                inputEdit.setText(input);
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input = "";
                inputEdit.setText(input);
                outputView.setText("");
            }
        });
    }

    private void calculateResult() {
        try {
            result = evaluateExpression(input);
            outputView.setText(String.valueOf(result));
            input = "";
            inputEdit.setText(input);
        } catch (Exception e) {
            outputView.setText("Ошибка");
        }
    }

    private double evaluateExpression(String expression) {
        expression = expression.replaceAll("%", "/100");
        expression = expression.replaceAll("sqrt\\(([^\\)]+)\\)", "Math.sqrt($1)");
        expression = expression.replaceAll("log\\(([^\\)]+)\\)", "Math.log10($1)");

        String[] parts = expression.split("[+*/-]");
        double[] numbers = new double[parts.length];
        for (int i = 0; i < parts.length; i++) {
            numbers[i] = Double.parseDouble(parts[i]);
        }
        double result = numbers[0];
        for (int i = 1; i < parts.length; i++) {
            char op = expression.charAt(expression.indexOf(parts[i - 1]) + parts[i - 1].length());
            switch (op) {
                case '+':
                    result += numbers[i];
                    break;
                case '-':
                    result -= numbers[i];
                    break;
                case '*':
                    result *= numbers[i];
                    break;
                case '/':
                    result /= numbers[i];
                    break;
            }
        }
        return result;
    }

}