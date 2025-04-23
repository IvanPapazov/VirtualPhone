package com.os0.navigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    EditText name;
    Button btnContinue;
    ArrayList<User> users = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.SignUpEditTextName);
        email = findViewById(R.id.SignUpEditTextEmail);
        password = findViewById(R.id.SignUpEditTextPassword);
        btnContinue = findViewById(R.id.SignUpButtonContinue);
        btnContinue.setOnClickListener(v -> SignIn());
    }

    private void SignIn() {
        // Валидации и създаване на нов акаунт
        if (!isAccountAlreadyCreated() && isNameCorrect()
                && CommonLogIn.isEmailCorrect(email)
                && CommonLogIn.isPasswordCorrect(password)) {

            User account = new User(
                    name.getText().toString(),
                    email.getText().toString(),
                    password.getText().toString()
            );
            users.add(account);

            SharedPreferences spAccounts = getSharedPreferences("shared preferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = spAccounts.edit();
            Gson gson = new Gson();
            editor.putString("accounts", gson.toJson(users));
            editor.apply();

            // Отбелязваме, че потребителят е логнат
            SharedPreferences prefBoolean = getApplicationContext()
                    .getSharedPreferences("BooleanPref", MODE_PRIVATE);
            prefBoolean.edit()
                    .putBoolean("isLoggedIn", true)
                    .apply();

            // Преминаваме към MainActivity
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    private boolean isNameCorrect() {
        String nameLocal = name.getText().toString();

        if (nameLocal.isEmpty()) {
            name.setError("Field can't be empty");
            return false;
        }
        name.setError(null);
        return true;
    }

    private boolean isAccountAlreadyCreated() {
        SharedPreferences LogInSharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = LogInSharedPreferences.getString("accounts", null);
        Type type = new TypeToken<ArrayList<User>>() {
        }.getType();
        users = gson.fromJson(json, type);

        String emailLocal = email.getText().toString();
        String passLocal = password.getText().toString();

        if (users == null) {
            users = new ArrayList<>();
        }

        for (User item : users) {
            if (Objects.equals(item.getEmail(), emailLocal) && Objects.equals(item.getPassword(), passLocal)) {
                Toast.makeText(SignUpActivity.this, "This account already exists", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }
}