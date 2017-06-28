
package com.aleksus.handtohand1.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aleksus.handtohand1.DefaultCallback;
import com.aleksus.handtohand1.ExampleUser;
import com.aleksus.handtohand1.R;
import com.backendless.Backendless;
import com.backendless.BackendlessUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText passwordField;
    private EditText nameField;
    private EditText emailField;

    private Button registerButton;

    private String password;
    private String name;
    private String email;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initUI();
    }

    private void initUI() {
        passwordField = (EditText) findViewById(R.id.passwordField);
        nameField = (EditText) findViewById(R.id.nameField);
        emailField = (EditText) findViewById(R.id.emailField);
        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRegisterButtonClicked();
            }
        });
    }

    private void onRegisterButtonClicked() {
        String passwordText = passwordField.getText().toString().trim();
        String nameText = nameField.getText().toString().trim();
        String emailText = emailField.getText().toString().trim();

        if (passwordText.isEmpty()) {
            showToast("Поле 'Пароль' не может быть пустым.");
            return;
        }

        if (emailText.isEmpty()) {
            showToast("Поле 'Логин (EMail)' не может быть пустым.");
            return;
        }

        if (!passwordText.isEmpty()) {
            password = passwordText;
        }

        if (!nameText.isEmpty()) {
            name = nameText;
        }

        if (!emailText.isEmpty()) {
            email = emailText;
        }

        ExampleUser user = new ExampleUser();

        if (password != null) {
            user.setPassword(password);
        }

        if (name != null) {
            user.setName(name);
        }

        if (email != null) {
            user.setEmail(email);
        }

        Backendless.UserService.register(user, new DefaultCallback<BackendlessUser>(RegisterActivity.this) {
            @Override
            public void handleResponse(BackendlessUser response) {
                super.handleResponse(response);
                startActivity(new Intent(RegisterActivity.this, RegistrationSuccessActivity.class));
                finish();
            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
                