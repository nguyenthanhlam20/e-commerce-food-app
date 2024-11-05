package vn.edu.fpt.fa24.Services;

import com.google.gson.Gson;

import vn.edu.fpt.fa24.Helpers.JsonHelper;
import vn.edu.fpt.fa24.Models.UserModel;
import vn.edu.fpt.fa24.Callbacks.ResponseCallBack;

public class UserService {

    public void getUser(String accountId, ResponseCallBack<UserModel> callback) {
        ClientService clientService = new ClientService("User/find-by-account-id/" + accountId);
        clientService.executeGet(new ResponseCallBack<String>() {
            @Override
            public void onSuccess(String response) {

                JsonHelper<UserModel> jsonHelper = new JsonHelper();
                UserModel model = jsonHelper.parse(response, UserModel.class);
                callback.onSuccess(model);
            }

            @Override
            public void onFailure(String error) {
                callback.onFailure(error);
            }
        });
    }

    public void updateUser(String accountId, UserModel user, ResponseCallBack<String> callback) {
        ClientService clientService = new ClientService("User/" + accountId);
        clientService.setJsonData(new Gson().toJson(user));

        clientService.executePut(new ResponseCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                callback.onSuccess(response);
            }

            @Override
            public void onFailure(String error) {
                callback.onFailure(error);
            }
        });
    }

}
