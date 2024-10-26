package vn.edu.fpt.fa24.Models.Authentication;

public class RegisterModel {
    private String username;
    private String password;
    private String rePassword; // Consider renaming this field for clarity
    private String email;
    private String phone;
    private String otp;

    // Constructor
    public RegisterModel(String username, String password, String rePassword,
                                 String email, String phone, String otp) {
        this.username = username;
        this.password = password;
        this.rePassword = rePassword;
        this.email = email;
        this.phone = phone;
        this.otp = otp;
    }

    // Getters and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

}
