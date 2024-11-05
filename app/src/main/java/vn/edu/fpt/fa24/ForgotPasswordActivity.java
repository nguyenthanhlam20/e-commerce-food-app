package vn.edu.fpt.fa24;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import vn.edu.fpt.fa24.Callbacks.ResponseCallBack;
import vn.edu.fpt.fa24.Helpers.JsonHelper;
import vn.edu.fpt.fa24.Helpers.SessionHelper;
import vn.edu.fpt.fa24.Helpers.StringHelper;
import vn.edu.fpt.fa24.Models.Authentication.RegisterModel;
import vn.edu.fpt.fa24.Services.AuthenticationService;

public class ForgotPasswordActivity extends AppCompatActivity {
    TextView fEmail, fPassword, fConfirmPassword;
    Button resetPassword;
    RelativeLayout mLoginBtn;
    AuthenticationService service;
    SessionHelper sessionHelper;
    JsonHelper<RegisterModel> jsonHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        initializations();
        clickListeners();
    }

    private void initializations() {
        fEmail = findViewById(R.id.forgotEmail);
        fPassword = findViewById(R.id.forgotPassword);
        fConfirmPassword = findViewById(R.id.forgotConfirmPassword);
        resetPassword = findViewById(R.id.forgotBtn);
        mLoginBtn = findViewById(R.id.loginBtn);
        service = new AuthenticationService();
        sessionHelper = new SessionHelper(ForgotPasswordActivity.this);
        jsonHelper = new JsonHelper<>();
    }

    private void clickListeners() {
        mLoginBtn.setOnClickListener(view -> {
            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        resetPassword.setOnClickListener(view -> {
            String email = fEmail.getText().toString();
            String password = fPassword.getText().toString();
            String confirmPassword = fConfirmPassword.getText().toString();

            Intent intent = new Intent(ForgotPasswordActivity.this, EmailConfirmActivity.class);
            intent.putExtra("Type", "ForgotPassword");

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(ForgotPasswordActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!StringHelper.isValidEmail(email)) {
                Toast.makeText(ForgotPasswordActivity.this, "Email must be in correct format (Ex: daylaemail@gmail.com, ...)", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!StringHelper.isValidPassword(password)) {
                Toast.makeText(ForgotPasswordActivity.this, "Password must be at lease 8 - 20 characters, including 1 uppercase, 1 lowercase and 1 speacial character!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password.equals(confirmPassword)) {
                Toast.makeText(ForgotPasswordActivity.this, "Password not match", Toast.LENGTH_SHORT).show();
                return;
            }

            RegisterModel model = new RegisterModel("", password, confirmPassword, email, "", "");
            service.SendEmail(model, new ResponseCallBack<String>() {
                @Override
                public void onSuccess(String response) {
                    runOnUiThread(() -> {
                        intent.putExtra("RegisterModelJson", jsonHelper.toJson(model));
                        intent.putExtra("RegistCode", response);
                        intent.putExtra("Type", "ForgotPassword");
                        startActivity(intent);

                        finish();
                    });
                }
                @Override
                public void onFailure(String error) {
                    runOnUiThread(() -> {
                        Toast.makeText(ForgotPasswordActivity.this, error, Toast.LENGTH_SHORT).show();
                    });
                }
            });
        });
    }


}