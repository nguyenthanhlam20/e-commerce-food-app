package vn.edu.fpt.fa24;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import vn.edu.fpt.fa24.Helpers.JsonHelper;
import vn.edu.fpt.fa24.Helpers.SessionHelper;
import vn.edu.fpt.fa24.Models.AccountModel;
import vn.edu.fpt.fa24.Models.Authentication.LoginModel;
import vn.edu.fpt.fa24.Services.AuthenticationService;
import vn.edu.fpt.fa24.Callbacks.ResponseCallBack;

public class LoginActivity extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button mLoginBtn;
    RelativeLayout mRegisterBtn;
    AuthenticationService service;
    SessionHelper sessionHelper;
    JsonHelper<AccountModel> jsonHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializations();
        clickListeners();

//        if(sessionHelper.isLoggedIn()) {
//            goToHomePage();
//        }
    }

    private void goToHomePage() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
//        sessionHelper.saveUserId(String.valueOf(1));
    }

    private void initializations() {
        mEmail = findViewById(R.id.loginEmail);
        mPassword = findViewById(R.id.loginPassword);
        mLoginBtn = findViewById(R.id.loginBtn);
        mRegisterBtn = findViewById(R.id.registerBtn);
        service = new AuthenticationService();
        sessionHelper = new SessionHelper(LoginActivity.this);
        jsonHelper = new JsonHelper<>();
    }

    private void clickListeners() {
        mRegisterBtn.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });

        mLoginBtn.setOnClickListener(view -> {
            String email = mEmail.getText().toString();
            String password = mPassword.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            } else {
                LoginModel model = new LoginModel(email, password);
                service.Login(model, new ResponseCallBack<String>() {
                    @Override
                    public void onSuccess(String response) {
                        runOnUiThread(() -> {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);

                            AccountModel account = jsonHelper.parse(response, AccountModel.class);
                            sessionHelper.saveUserId(String.valueOf(account.getUserId()));
                            sessionHelper.saveAccountId(String.valueOf(account.getAccountId()));
                            sessionHelper.saveIsLoggedIn(true);

                            Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        });
                    }

                    @Override
                    public void onFailure(String error) {
                        runOnUiThread(() -> Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show());
                    }
                });
            }
        });
    }
}