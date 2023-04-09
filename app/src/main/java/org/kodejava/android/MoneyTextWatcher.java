package org.kodejava.android;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class MoneyTextWatcher implements TextWatcher {
    private static final Locale locale = new Locale("id", "ID");
    private static final DecimalFormat formatter = (DecimalFormat) NumberFormat.getCurrencyInstance(locale);
    private final WeakReference<EditText> editTextWeakReference;

    public MoneyTextWatcher(EditText editText) {
        editTextWeakReference = new WeakReference<>(editText);
        formatter.setMaximumFractionDigits(0);
        formatter.setRoundingMode(RoundingMode.FLOOR);

        DecimalFormatSymbols symbol = new DecimalFormatSymbols(locale);
        symbol.setCurrencySymbol(symbol.getCurrencySymbol() + " ");
        formatter.setDecimalFormatSymbols(symbol);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        EditText editText = editTextWeakReference.get();
        if (editText == null || editText.getText().toString().isEmpty()) {
            return;
        }
        editText.removeTextChangedListener(this);

        BigDecimal parsed = parseCurrencyValue(editText.getText().toString());
        String formatted = formatter.format(parsed);

        editText.setText(formatted);
        editText.setSelection(formatted.length());
        editText.addTextChangedListener(this);
    }

    public static BigDecimal parseCurrencyValue(String value) {
        try {
            String replaceRegex = String.format("[%s,.\\s]",
                    Objects.requireNonNull(formatter.getCurrency()).getSymbol(locale));
            String currencyValue = value.replaceAll(replaceRegex, "");
            currencyValue = "".equals(currencyValue) ? "0" : currencyValue;
            return new BigDecimal(currencyValue);
        } catch (Exception e) {
            Log.e("App", e.getMessage(), e);
        }
        return BigDecimal.ZERO;
    }
}
