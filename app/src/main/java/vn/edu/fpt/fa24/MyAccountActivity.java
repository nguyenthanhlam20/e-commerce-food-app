package vn.edu.fpt.fa24;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import vn.edu.fpt.fa24.Callbacks.DatePickerCallback;
import vn.edu.fpt.fa24.Helpers.DateHelper;
import vn.edu.fpt.fa24.Helpers.SessionHelper;
import vn.edu.fpt.fa24.Helpers.StringHelper;
import vn.edu.fpt.fa24.Models.UserModel;
import vn.edu.fpt.fa24.Callbacks.ResponseCallBack;
import vn.edu.fpt.fa24.Services.UserService;

public class MyAccountActivity extends AppCompatActivity {

    private UserService userService;
    private SessionHelper sessionHelper;
    private DateHelper dateHelper;
    private EditText mFirstName, mLasName, mAddress, mDob, mEmail, mPhone;
    private RadioButton rMale, rFemale;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        initialization();
        queryUser();
        setListeners();
    }

    private UserModel getUserInput() {
        UserModel user = new UserModel();
        String formattedDate = dateHelper.formatDate(mDob.getText().toString());
        user.setAccountId(sessionHelper.getUserId());
        user.setFirstName(mFirstName.getText().toString());
        user.setLastName(mLasName.getText().toString());
        user.setAddress(mAddress.getText().toString());
        user.setBirthDate(formattedDate);
        user.setEmail(mEmail.getText().toString());
        user.setPhone(mPhone.getText().toString());
        user.setGender(rMale.isChecked());
        return user;
    }

    private Boolean isValidInput() {
        UserModel user = getUserInput();

        if (user.getFirstName().isEmpty() ||
                user.getLastName().isEmpty() ||
                user.getEmail().isEmpty() ||
                user.getPhone().isEmpty() ||
                user.getAddress().isEmpty() ||
                user.getBirthDate().isEmpty()) {
            Toast.makeText(MyAccountActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!StringHelper.isValidEmail(user.getEmail())) {
            Toast.makeText(MyAccountActivity.this, "Email must be in correct format (Ex: daylaemail@gmail.com, ...)", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!StringHelper.isValidPhoneNumber(user.getPhone())) {
            Toast.makeText(MyAccountActivity.this, "Phone number must be in correct format (Ex: 091..., 054...)", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!dateHelper.isValidPastDate(user.getBirthDate())) {
            Toast.makeText(MyAccountActivity.this, "Birthdate must be in the past", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private void setListeners() {
        mDob.setOnClickListener(v -> {
            dateHelper.showDatePickerDialog(new DatePickerCallback() {
                @Override
                public void setDate(String date) {
                    mDob.setText(date);
                }
            });
        });

        saveBtn.setOnClickListener(view -> {
            if (!isValidInput()) return;

            UserModel updateUser = getUserInput();
            userService.updateUser(updateUser, new ResponseCallBack<String>() {
                @Override
                public void onSuccess(String response) {
                    runOnUiThread(() -> Toast.makeText(MyAccountActivity.this, response, Toast.LENGTH_SHORT).show());
                }

                @Override
                public void onFailure(String error) {
                    runOnUiThread(() -> Toast.makeText(MyAccountActivity.this, error, Toast.LENGTH_SHORT).show());
                }
            });
        });
    }

    private void queryUser() {
        userService.getUser(sessionHelper.getUserId(), new ResponseCallBack<UserModel>() {
            @Override
            public void onSuccess(UserModel user) {
                runOnUiThread(() -> setUser(user));
            }

            @Override
            public void onFailure(String error) {
                runOnUiThread(() -> Toast.makeText(MyAccountActivity.this, error, Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void setUser(UserModel user) {
        mFirstName.setText(user.getFirstName());
        mLasName.setText(user.getLastName());
        mAddress.setText(user.getAddress());
        mDob.setText(dateHelper.showDate(user.getBirthDate()));
        mEmail.setText(user.getEmail());
        mPhone.setText(user.getPhone());
        rMale.setChecked(user.isGender());
        rFemale.setChecked(!user.isGender());
    }

    private void initialization() {
        mFirstName = findViewById(R.id.firstName);
        mLasName = findViewById(R.id.lastName);
        mAddress = findViewById(R.id.address);
        mDob = findViewById(R.id.dateOfBirth);
        mEmail = findViewById(R.id.emailAddress);
        mPhone = findViewById(R.id.phoneNumber);
        rMale = findViewById(R.id.radioMale);
        rFemale = findViewById(R.id.radioFemale);
        saveBtn = findViewById(R.id.saveBtn);

        sessionHelper = new SessionHelper(MyAccountActivity.this);
        dateHelper = new DateHelper(MyAccountActivity.this);
        userService = new UserService();
    }
}