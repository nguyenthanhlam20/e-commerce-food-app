package vn.edu.fpt.fa24.Services;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import vn.edu.fpt.fa24.Callbacks.ResponseCallBack;
import vn.edu.fpt.fa24.Helpers.JsonHelper;
import vn.edu.fpt.fa24.Models.Order.OrderHistoryModel;
import vn.edu.fpt.fa24.Models.ProductModel;

public class OrderHistoryService {

    public void getOrderHistories(String userId, ResponseCallBack<List<OrderHistoryModel>> callback) {
        ClientService clientService = new ClientService("Order/order-history/" + userId);
        clientService.executeGet(new ResponseCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                Type type = new TypeToken<ArrayList<OrderHistoryModel>>() {}.getType();

                JsonHelper<List<OrderHistoryModel>> jsonHelper = new JsonHelper();
                List<OrderHistoryModel> items = jsonHelper.parseList(response, type);
                callback.onSuccess(items);
            }

            @Override
            public void onFailure(String error) {
                callback.onFailure(error);
            }
        });
    }

    public void getOrderHistoryById(int id, ResponseCallBack<OrderHistoryModel> callback) {
        ClientService clientService = new ClientService("Order/" + id);
        clientService.executeGet(new ResponseCallBack<String>() {
            @Override
            public void onSuccess(String response) {

                JsonHelper<OrderHistoryModel> jsonHelper = new JsonHelper();
                OrderHistoryModel item = jsonHelper.parse(response, OrderHistoryModel.class);
                callback.onSuccess(item);
            }

            @Override
            public void onFailure(String error) {
                callback.onFailure(error);
            }
        });
    }
}
