package com.example.basicretrofitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.basicretrofitapp.dto.AccessTokenDTO;
import com.example.basicretrofitapp.dto.LoginDTO;
import com.example.basicretrofitapp.models.User;
import com.example.basicretrofitapp.services.RetrofitClient;
import com.example.basicretrofitapp.services.UserService;
import com.example.basicretrofitapp.utils.InputValidator;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login extends AppCompatActivity {

    private TextInputLayout tilEmail, tilPassword;
    private Button btnSubmit;
    private TextView tvRegister;
    private SharedPreferences preferences;
    private Retrofit retrofit;

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
                    retrofit = RetrofitClient.getRetrofitInstance();
                    UserService service = retrofit.create(UserService.class);
                    Call<AccessTokenDTO> call = service.login(new LoginDTO(email, password));

                    call.enqueue(new Callback<AccessTokenDTO>() {
                        @Override
                        public void onResponse(Call<AccessTokenDTO> call, Response<AccessTokenDTO> response) {
                            if(response.isSuccessful()) {
                                String token = response.body().getAccessToken();

                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("token", token);
                                editor.commit();

                                Log.e("Token", token);

                                Toast.makeText(getBaseContext(), "Token: " + token, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(view.getContext(), Home.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getBaseContext(), "Credenciales erroneas", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<AccessTokenDTO> call, Throwable t) {
                            t.printStackTrace();
                            Toast.makeText(getBaseContext(), "Error al conectarse con el servicio", Toast.LENGTH_SHORT).show();
                        }
                    });
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