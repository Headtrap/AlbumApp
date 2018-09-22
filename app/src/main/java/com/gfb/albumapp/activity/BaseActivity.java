package com.gfb.albumapp.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;


public class BaseActivity extends AppCompatActivity {

    public void setupToolbar() {
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeButtonEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
        }
    }

    public boolean checkEmpty(EditText editText) {
        String s = String.valueOf(editText.getText());
        return !s.isEmpty() && editText.getText() != null;
    }

    public String getValue(EditText editText) {
        return String.valueOf(editText.getText());
    }

    public void handleException(Throwable t) {
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
    }

    public void showMessage(int message) {
        showMessage(getString(message));
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
