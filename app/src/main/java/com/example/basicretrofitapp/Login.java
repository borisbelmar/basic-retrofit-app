package com.example.basicretrofitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.basicretrofitapp.models.User;
import com.example.basicretrofitapp.utils.InputValidator;
import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

    private TextInputLayout tilEmail, tilPassword;
    private Button btnSubmit;
    private TextView tvRegister;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Referencias de la vista

        btnSubmit = findViewById(R.id.btn_submit);
        tilEmail = findViewById(R.id.til_email);
        tilPassword = findViewById(R.id.til_password);
        tvRegister = findViewById(R.id.tv_register);

        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = tilEmail.getEditText().getText().toString();
                String password = tilPassword.getEditText().getText().toString();

                InputValidator inputValidator = new InputValidator(getBaseContext());

                inputValidator.isEmail(tilEmail);
                inputValidator.isRequired(tilPassword);

                if (inputValidator.validate()) {
                    if (email.equals("test@gmail.com") && password.equals("123456")) {

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("email", email);
                        editor.commit();

                        Toast.makeText(getBaseContext(), "Bienvenido", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(view.getContext(), Home.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getBaseContext(), "Credenciales erroneas", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getBaseContext(), "Asegurate de completar bien los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Register.class);
                startActivity(intent);
            }
        });
    }
}