package vn.edu.fpt.fa24;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import vn.edu.fpt.fa24.Callbacks.ResponseCallBack;
import vn.edu.fpt.fa24.Helpers.JsonHelper;
import vn.edu.fpt.fa24.Helpers.SessionHelper;
import vn.edu.fpt.fa24.Helpers.StringHelper;
import vn.edu.fpt.fa24.Models.Authentication.RegisterModel;
import vn.edu.fpt.fa24.Services.AuthenticationService;

public class EmailConfirmActivity extends AppCompatActivity {
    EditText mVerifyCode;
    Button mVerifyBtn;
    AuthenticationService service;
    SessionHelper session;
    JsonHelper<RegisterModel> jsonHelper;
    private String regisCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_confirm);
        initializations();
        clickListeners();
    }

    private void initializations() {
        regisCode = getIntent().getStringExtra("RegisCode");
        mVerifyCode = findViewById(R.id.verifyCode);
        mVerifyBtn = findViewById(R.id.verifyBtn);
        service = new AuthenticationService();
        session = new SessionHelper(EmailConfirmActivity.this);
        jsonHelper = new JsonHelper<>();
    }

    private void clickListeners() {
        mVerifyBtn.setOnClickListener(view -> {
            String verificationCode = mVerifyCode.getText().toString();

            if (verificationCode.isEmpty()) {
                Toast.makeText(EmailConfirmActivity.this, "Please enter verification code", Toast.LENGTH_SHORT).show();
            } else if (!verificationCode.equalsIgnoreCase(regisCode)) {
                Toast.makeText(EmailConfirmActivity.this, "Account registration failed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EmailConfirmActivity.this, RegisterActivity.class);
                startActivity(intent);
            } else {
                String json = getIntent().getStringExtra("RegisterModelJson");
                RegisterModel model = jsonHelper.parse(json, RegisterModel.class);
                service.Register(model, new ResponseCallBack<String>() {
                    @Override
                    public void onSuccess(String response) {
                        runOnUiThread(() -> {
                            Intent intent = new Intent(EmailConfirmActivity.this, LoginActivity.class);
                            startActivity(intent);

                            session.saveUserId(response);
                            Toast.makeText(EmailConfirmActivity.this, "Register successfully", Toast.LENGTH_SHORT).show();

                            finish();
                        });
                    }

                    @Override
                    public void onFailure(String error) {
                        runOnUiThread(() -> {
                            Toast.makeText(EmailConfirmActivity.this, error, Toast.LENGTH_SHORT).show();
                        });
                    }
                });
            }
        });
    }

}