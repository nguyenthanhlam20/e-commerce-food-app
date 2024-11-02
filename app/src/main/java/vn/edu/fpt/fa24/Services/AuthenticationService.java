package vn.edu.fpt.fa24.Services;

import vn.edu.fpt.fa24.Models.Authentication.LoginModel;
import vn.edu.fpt.fa24.Models.Authentication.RegisterModel;
import vn.edu.fpt.fa24.Callbacks.ResponseCallBack;

public class AuthenticationService {
    public void Login(LoginModel model, ResponseCallBack<String> responseCallBack) {
        ClientService clientService = new ClientService("Account/login");

        // Adding parameters
        clientService.addParam("username", model.getUsername());
        clientService.addParam("password", model.getPassword());

        // Execute POST request
        clientService.executePost(responseCallBack);
    }

    public void SendEmail(RegisterModel model, ResponseCallBack<String> responseCallBack) {
        ClientService clientService = new ClientService("Email/send");

        // Adding parameters
        clientService.addParam("username", model.getUsername());
        clientService.addParam("password", model.getPassword());
        clientService.addParam("email", model.getEmail());
        clientService.addParam("phone", model.getPhone());
        clientService.addParam("confirmPassword", model.getRePassword());
        clientService.addParam("registCode", model.getOtp());

        // Execute POST request
        clientService.executePost(responseCallBack);
    }

    public void Register(RegisterModel model, ResponseCallBack<String> responseCallBack) {
        ClientService clientService = new ClientService("Account/register");

        // Adding parameters
        clientService.addParam("username", model.getUsername());
        clientService.addParam("password", model.getPassword());
        clientService.addParam("email", model.getEmail());
        clientService.addParam("phone", model.getPhone());
        clientService.addParam("re_password", model.getRePassword());
        clientService.addParam("registCode", model.getOtp());

        // Execute POST request
        clientService.executePost(responseCallBack);
    }
}
