package com.gfb.albumapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gfb.albumapp.R;
import com.gfb.albumapp.entity.User;
import com.gfb.albumapp.service.UserService;


public class RegisterActivity extends BaseActivity {

    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;
    private Button btSave;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupToolbar();
        user = (User) getIntent().getSerializableExtra("user");
        etEmail = findViewById(R.id.etEmail);
        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        btSave = findViewById(R.id.btSave);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        if (user != null) {
            etEmail.setText(user.getEmail());
            etName.setText(user.getName());
            etPassword.setText(user.getPassword());
        }
    }

    private void saveData() {
        if (!checkEmpty(etName)) {
            showMessage("Campo nome não pode estar vázio.");
            return;
        }
        if (!checkEmpty(etEmail)) {
            showMessage("Campo email não pode estar vázio.");
            return;

        }
        if (!checkEmpty(etPassword)) {
            showMessage("Campo senha não pode estar vázio.");
            return;
        }

        if (user == null) {
            save();
        } else {
            update();
        }
    }

    private void save() {
        user = new User();
        user.setEmail(getValue(etEmail));
        user.setName(getValue(etName));
        user.setPassword(getValue(etPassword));
        user.save();

        showMessage("Usuário salvo com sucesso");
        finish();
    }

    private void update() {
        user.setEmail(getValue(etEmail));
        user.setName(getValue(etName));
        user.setPassword(getValue(etPassword));
        user.update();
        UserService.saveLocalUser(user.getEmail(), this);

        showMessage("Usuário atualizado com sucesso");
        finish();
    }
}
