package vn.edu.fpt.fa24;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import vn.edu.fpt.fa24.Helpers.SessionHelper;
import vn.edu.fpt.fa24.Helpers.StringHelper;
import vn.edu.fpt.fa24.Models.Authentication.RegisterModel;
import vn.edu.fpt.fa24.Services.AuthenticationService;
import vn.edu.fpt.fa24.Services.Callbacks.ResponseCallBack;

public class RegisterActivity extends AppCompatActivity {
    EditText mEmail, mPassword, mUserName, mConfirmPassword, mPhone;
    RelativeLayout mLoginBtn;
    Button mRegisterBtn;
    AuthenticationService service;
    SessionHelper session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setContentView(R.layout.activity_register);
        initializations();
        clickListeners();
    }

    private void initializations() {
        mEmail = findViewById(R.id.registerEmail);
        mUserName = findViewById(R.id.registerName);
        mPassword = findViewById(R.id.registerPassword);
        mLoginBtn = findViewById(R.id.loginBtn);
        mConfirmPassword = findViewById(R.id.registerConfirmPassword);
        mPhone = findViewById(R.id.registerPhone);
        mRegisterBtn = findViewById(R.id.registerBtn);
        service = new AuthenticationService();
        session = new SessionHelper(RegisterActivity.this);
    }

    private void clickListeners() {
        mLoginBtn.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });
        mRegisterBtn.setOnClickListener(view -> {
            String email = mEmail.getText().toString();
            String username = mUserName.getText().toString();
            String password = mPassword.getText().toString();
            String confirmPassword = mConfirmPassword.getText().toString();
            String phone = mPhone.getText().toString();

            if (email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phone.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!StringHelper.isValidUsername(username)) {
                Toast.makeText(RegisterActivity.this, "Username must be at lease 8 - 20 characters, do not including special character and uppercase character!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!StringHelper.isValidEmail(email)) {
                Toast.makeText(RegisterActivity.this, "Email must be in correct format (Ex: daylaemail@gmail.com, ...)", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!StringHelper.isValidPhoneNumber(phone)) {
                Toast.makeText(RegisterActivity.this, "Phone number must be in correct format (Ex: 091..., 054...)", Toast.LENGTH_SHORT).show();
                return;
            }


            if (!StringHelper.isValidPassword(password)) {
                Toast.makeText(RegisterActivity.this, "Password must be at lease 8 - 20 characters, including 1 uppercase, 1 lowercase and 1 speacial character!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(RegisterActivity.this, "Password not match", Toast.LENGTH_SHORT).show();
                return;
            }

            RegisterModel model = new RegisterModel(username, password, confirmPassword, email, phone, "");
            service.Register(model, new ResponseCallBack<String>() {
                @Override
                public void onSuccess(String response) {
                    runOnUiThread(() -> {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);

                        session.saveUserId(response);
                        Toast.makeText(RegisterActivity.this, "Register successfully", Toast.LENGTH_SHORT).show();

                        finish();
                    });
                }

                @Override
                public void onFailure(String error) {
                    runOnUiThread(() -> {
                        Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_SHORT).show();
                    });
                }
            });

        });
    }

}