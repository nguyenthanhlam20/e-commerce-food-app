package vn.edu.fpt.fa24;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import vn.edu.fpt.fa24.Callbacks.ResponseCallBack;
import vn.edu.fpt.fa24.Helpers.JsonHelper;
import vn.edu.fpt.fa24.Models.Authentication.RegisterModel;
import vn.edu.fpt.fa24.Services.AuthenticationService;

public class EmailConfirmActivity extends AppCompatActivity {
    EditText mVerificationCode;
    Button mVerifyBtn;
    AuthenticationService service;
    JsonHelper<RegisterModel> jsonHelper;
    private String registCode;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_confirm);
        initializations();
        clickListeners();
    }

    private void initializations() {
        registCode = getIntent().getStringExtra("RegistCode");
        type = getIntent().getStringExtra("Type");
        mVerificationCode = findViewById(R.id.verificationCode);
        mVerifyBtn = findViewById(R.id.verifyBtn);
        service = new AuthenticationService();
        jsonHelper = new JsonHelper<>();
    }

    private void clickListeners() {
        mVerifyBtn.setOnClickListener(view -> {
            String verifyCode = mVerificationCode.getText().toString();

            if (verifyCode.isEmpty()) {
                Toast.makeText(EmailConfirmActivity.this, "Please enter verification code", Toast.LENGTH_SHORT).show();
            } else if(registCode.equalsIgnoreCase(verifyCode)) {
                Toast.makeText(EmailConfirmActivity.this, "Wrong verification code", Toast.LENGTH_SHORT).show();
            } else {
                String json = getIntent().getStringExtra("RegisterModelJson");
                RegisterModel model = jsonHelper.parse(json, RegisterModel.class);
                service.Register(model, new ResponseCallBack<String>() {
                    @Override
                    public void onSuccess(String response) {
                        runOnUiThread(() -> {
                            Intent intent = new Intent(EmailConfirmActivity.this, LoginActivity.class);
                            startActivity(intent);
                            if (type.equalsIgnoreCase("Register")){
                                Toast.makeText(EmailConfirmActivity.this, "Register successfully", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(EmailConfirmActivity.this, "Reset password successfully", Toast.LENGTH_SHORT).show();
                            }
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