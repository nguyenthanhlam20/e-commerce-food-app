package vn.edu.fpt.fa24.Services;

import vn.edu.fpt.fa24.Helpers.JsonHelper;
import vn.edu.fpt.fa24.Models.Cart.CartModel;
import vn.edu.fpt.fa24.Requests.CartRequest;
import vn.edu.fpt.fa24.Requests.DeleteFromCartRequest;
import vn.edu.fpt.fa24.Callbacks.ResponseCallBack;

public class CartService {
    public void getCardByUserId(String userId, ResponseCallBack<CartModel> callback) {
        ClientService clientService = new ClientService("Cart/get-cart-of-user/" + userId);

        clientService.executeGet(new ResponseCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                JsonHelper<CartModel> jsonHelper = new JsonHelper();
                CartModel cart = jsonHelper.parse(response, CartModel.class);
                callback.onSuccess(cart);
            }

            @Override
            public void onFailure(String error) {
                callback.onFailure(error);
            }
        });
    }

    public void addToCart(CartRequest request, ResponseCallBack<String> callback) {
        ClientService clientService = new ClientService("Cart/add-to-cart");
        clientService.addParam("userId", request.getUserId());
        clientService.addParam("productId", request.getProductId());
        clientService.addParam("quantity", request.getQuantity());
        clientService.addParam("unitPrice", request.getUnitPrice().replace("$", ""));

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

    public void updateCart(int cardId, CartRequest request, ResponseCallBack<String> callback) {
        ClientService clientService = new ClientService("Cart/" + cardId);
        clientService.addParam("userId", request.getUserId());
        clientService.addParam("productId", request.getProductId());
        clientService.addParam("quantity", request.getQuantity());
        clientService.addParam("unitPrice", request.getUnitPrice().replace("$", ""));

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

    public void deleteFromCart(DeleteFromCartRequest request, ResponseCallBack<String> callback) {
        ClientService clientService = new ClientService("Cart/" + request.getUserId() + "/" + request.getProductId());
        clientService.executeDelete(new ResponseCallBack<String>() {
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
