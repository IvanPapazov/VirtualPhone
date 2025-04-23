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

public class LogInActivity extends AppCompatActivity {
    Button buttonNewAccount;
    Button btnCont;
    EditText passLogIn;
    EditText emailLogIn;
    ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        emailLogIn = findViewById(R.id.LogInEditTextEmail);
        passLogIn = findViewById(R.id.LogInEditTextPassword);

        btnCont = findViewById(R.id.LogInButtonContinue);
        btnCont.setOnClickListener(v -> CheckAccount());
        buttonNewAccount = findViewById(R.id.LogInButtonNewAccount);
        buttonNewAccount.setOnClickListener(v -> openCreateAccount());
    }

    private void CheckAccount() {
        SharedPreferences LogInSharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();

        String json = LogInSharedPreferences.getString("accounts", null);
        Type type = new TypeToken<ArrayList<User>>() {
        }.getType();
        users = gson.fromJson(json, type);

        String emailLocal = emailLogIn.getText().toString();
        String passLocal = passLogIn.getText().toString();
        boolean isLogInSuccessful = false;

        if (users != null) {
            for (User item : users) {
                if (Objects.equals(item.getEmail(), emailLocal) && Objects.equals(item.getPassword(), passLocal)) {
                    isLogInSuccessful = true;
                    SharedPreferences prefBoolean = getApplicationContext().getSharedPreferences("curUserPr", MODE_PRIVATE);
                    SharedPreferences.Editor editPref = prefBoolean.edit();
                    editPref.putString("currentUser", item.getName());
                    editPref.apply();
                    break;
                }
            }
        }
        if (isLogInSuccessful && CommonLogIn.isEmailCorrect(emailLogIn) && CommonLogIn.isPasswordCorrect(passLogIn)) {
            Toast.makeText(LogInActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

            SharedPreferences prefBoolean = getApplicationContext().getSharedPreferences("BooleanPref", MODE_PRIVATE);
            SharedPreferences.Editor editPref = prefBoolean.edit();
            editPref.putBoolean("isLoggedIn", true);
            editPref.apply();


            Intent i = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(i);
        } else if (CommonLogIn.isEmailCorrect(emailLogIn) && CommonLogIn.isPasswordCorrect(passLogIn) && !isLogInSuccessful) {
            Toast.makeText(LogInActivity.this, "Wrong email or password", Toast.LENGTH_SHORT).show();
        }
    }

    public void openCreateAccount() {
        Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
}