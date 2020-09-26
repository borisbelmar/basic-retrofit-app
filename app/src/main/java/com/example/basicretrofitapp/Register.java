package com.example.basicretrofitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.basicretrofitapp.dto.AccessTokenDTO;
import com.example.basicretrofitapp.dto.LoginDTO;
import com.example.basicretrofitapp.models.User;
import com.example.basicretrofitapp.services.RetrofitClient;
import com.example.basicretrofitapp.services.UserService;
import com.example.basicretrofitapp.ui.DatePickerFragment;
import com.example.basicretrofitapp.utils.DateConvert;
import com.example.basicretrofitapp.utils.InputValidator;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Register extends AppCompatActivity {

    private TextInputLayout tilFirstname, tilLastname, tilEmail, tilBirth, tilPassword, tilPasswordRepeat;
    private Button btnSubmit;
    private TextView tvLogin;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnSubmit = findViewById(R.id.btn_submit);
        tilFirstname = findViewById(R.id.til_firstname);
        tilLastname = findViewById(R.id.til_lastname);
        tilEmail = findViewById(R.id.til_email);
        tilBirth = findViewById(R.id.til_birth);
        tilPassword = findViewById(R.id.til_password);
        tilPasswordRepeat = findViewById(R.id.til_password_repeat);
        tvLogin = findViewById(R.id.tv_login);

        // DatePicker listener
        tilBirth.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(tilBirth);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstname = tilFirstname.getEditText().getText().toString();
                String lastname = tilLastname.getEditText().getText().toString();
                String email = tilEmail.getEditText().getText().toString();
                String birth = tilBirth.getEditText().getText().toString();
                String password = tilPassword.getEditText().getText().toString();

                InputValidator inputValidator = new InputValidator(getBaseContext());

                inputValidator.isRequired(tilFirstname);
                inputValidator.isRequired(tilLastname);
                inputValidator.isEmail(tilEmail);
                inputValidator.isRequired(tilFirstname);
                inputValidator.isRequired(tilBirth);
                inputValidator.isRequired(tilPassword);
                inputValidator.isRequired(tilPasswordRepeat);
                inputValidator.isEqual(tilPasswordRepeat, tilPassword);

                if (inputValidator.validate()) {
                    retrofit = RetrofitClient.getRetrofitInstance();
                    UserService service = retrofit.create(UserService.class);
                    User newUser = new User(email, firstname, lastname)
                            .withPassword(password)
                            .withBirth(birth);
                    Call<User> call = service.register(newUser);

                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                User registeredUser = response.body();
                                Toast.makeText(getBaseContext(), registeredUser.toString(), Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(v.getContext(), Login.class);
                                startActivity(intent);
                                finish();
                            } else if (response.code() == 409) {
                                Toast.makeText(getBaseContext(), "El usuario ya existe", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getBaseContext(), "Error desconocido", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            t.printStackTrace();
                            Toast.makeText(getBaseContext(), "Error al conectarse con el servicio", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getBaseContext(), "Asegurate de completar bien los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Login.class);
                startActivity(intent);
            }
        });
    }

    private void showDatePickerDialog(final TextInputLayout til) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = String.format("%d-%02d-%02d", year, (month +1), day);
                til.getEditText().setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}