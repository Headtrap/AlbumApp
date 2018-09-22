package com.gfb.albumapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gfb.albumapp.R;
import com.gfb.albumapp.entity.User;
import com.gfb.albumapp.service.UserService;

public class LoginActivity extends BaseActivity {

    private EditText etEmail;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        Button btLogin = findViewById(R.id.btLogin);
        TextView btRegister = findViewById(R.id.btRegister);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        User user = UserService.findUserByEmail(UserService.getLocalUser(this));
        if (user != null){
           showNext();
        }
    }

    private void validate() {
        if (!checkEmpty(etEmail)) {
            showMessage("Informe um email.");
            return;
        }
        if (!checkEmpty(etPassword)) {
            showMessage("Informe uma senha.");
            return;
        }

        User user = UserService.findUserByEmail(getValue(etEmail));
        if (user == null) {
            showMessage("Nenhum usuário encontrado com esse email.");
            return;
        }

        if (user.getPassword().equals(getValue(etPassword))) {
            UserService.saveLocalUser(getValue(etEmail), this);
            showNext();
        } else {
            showMessage("Senha inválida.");
        }

    }

    private void showNext() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
