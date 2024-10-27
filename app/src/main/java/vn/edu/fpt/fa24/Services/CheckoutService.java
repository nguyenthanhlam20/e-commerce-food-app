package vn.edu.fpt.fa24.Services;

import com.google.gson.Gson;

import vn.edu.fpt.fa24.Requests.CartRequest;
import vn.edu.fpt.fa24.Requests.CheckoutRequest;
import vn.edu.fpt.fa24.Callbacks.ResponseCallBack;

public class CheckoutService {
    public void checkout(CheckoutRequest request, ResponseCallBack<String> callback) {
        ClientService<CartRequest> clientService = new ClientService("Order/checkout");

        Gson gson = new Gson();
        clientService.setJsonData(gson.toJson(request));
        clientService.executePost(new ResponseCallBack<String>() {
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
