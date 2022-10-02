package org.kodejava.android;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        editText.addTextChangedListener(new MoneyTextWatcher(editText));
        editText.setText("0");

        textView = findViewById(R.id.textView);
    }

    public void doGetValue(View view) {
        BigDecimal value = MoneyTextWatcher.parseCurrencyValue(editText.getText().toString());
        textView.setText(String.valueOf(value));
    }
}
