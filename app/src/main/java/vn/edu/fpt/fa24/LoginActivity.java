package vn.edu.fpt.fa24;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import vn.edu.fpt.fa24.Helpers.JsonHelper;
import vn.edu.fpt.fa24.Helpers.SessionHelper;
import vn.edu.fpt.fa24.Models.AccountModel;
import vn.edu.fpt.fa24.Models.Authentication.LoginModel;
import vn.edu.fpt.fa24.Services.AuthenticationService;
import vn.edu.fpt.fa24.Callbacks.ResponseCallBack;

public class LoginActivity extends AppCompatActivity {
    EditText mUsername, mPassword;
    CheckBox checkBoxSavePassword;
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
        sessionHelper.saveUserId(String.valueOf(1));
        sessionHelper.saveAccountId(String.valueOf(1));
    }

    private void initializations() {
        mUsername = findViewById(R.id.loginUsername);
        mPassword = findViewById(R.id.loginPassword);
        checkBoxSavePassword = findViewById(R.id.checkBoxSavePassword);
        mLoginBtn = findViewById(R.id.loginBtn);
        mRegisterBtn = findViewById(R.id.registerBtn);
        service = new AuthenticationService();
        sessionHelper = new SessionHelper(LoginActivity.this);
        jsonHelper = new JsonHelper<>();

        restoreUsernamePassword();
    }

    private void clickListeners() {
        mRegisterBtn.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });

        mLoginBtn.setOnClickListener(view -> {
            String username = mUsername.getText().toString();
            String password = mPassword.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            } else {
                LoginModel model = new LoginModel(username, password);
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
                            saveUsernamePasswordToPreference(username, password , checkBoxSavePassword.isChecked());

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

    private void saveUsernamePasswordToPreference(String username, String password, boolean status) {
        // B1: Create file for saving
        SharedPreferences sharedPreferences= getSharedPreferences("H",MODE_PRIVATE);

        // B2: Enable editor mode
        SharedPreferences.Editor editor= sharedPreferences.edit();

        if (!status){ //If exist password
            editor.clear(); //Clear old password
        }else{
            //Save new data
            editor.putString("USERNAME",username);
            editor.putString("PASSWORD",password);
            editor.putBoolean("REMEMBER",status);
        }

        //Commit data to file
        editor.commit();
    }

    private List<Object> restoreUsernamePassword(){
        List<Object> list= new ArrayList<>();
        //B1: Open file
        SharedPreferences sharedPreferences= getSharedPreferences("H",MODE_PRIVATE);

        //B2: Check request
        boolean check= sharedPreferences.getBoolean("REMEMBER", false);

        if (check) {
            String username= sharedPreferences.getString("USERNAME", "");
            mUsername.setText(username);
            String password= sharedPreferences.getString("PASSWORD", "");
            mPassword.setText(password);

            list.add(username);
            list.add(password);
            list.add(check);
        }

        checkBoxSavePassword.setChecked(check);
        return list;
    }
}