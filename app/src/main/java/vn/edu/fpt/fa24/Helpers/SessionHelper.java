package vn.edu.fpt.fa24.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionHelper {

    private static final String PREF_NAME = "UserSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_ID = "userId";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionHelper(Context context) {
        this.context = context.getApplicationContext();
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Method to save session data
    public void saveIsLoggedIn(boolean value) {
        editor.putBoolean(KEY_IS_LOGGED_IN, value);
        editor.apply(); // Save changes asynchronously
    }

    // Method to save session data
    public void saveUserId(String value) {
        editor.putString(KEY_USER_ID, value);
        editor.apply(); // Save changes asynchronously
    }

    // Method to check if user is logged in
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // Method to retrieve user ID
    public String getUserId() {
        return sharedPreferences.getString(KEY_USER_ID, null);
    }

    // Method to clear session (log out)
    public void clearSession() {
        editor.clear();
        editor.apply();
    }
}
