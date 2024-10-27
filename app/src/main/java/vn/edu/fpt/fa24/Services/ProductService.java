package vn.edu.fpt.fa24.Services;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import vn.edu.fpt.fa24.Helpers.JsonHelper;
import vn.edu.fpt.fa24.Models.ProductModel;
import vn.edu.fpt.fa24.Callbacks.ResponseCallBack;

public class ProductService {
    public void getProducts(ResponseCallBack<ArrayList<ProductModel>> callback) {
        ClientService clientService = new ClientService("Product/available-products");
        clientService.executeGet(new ResponseCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                Type type = new TypeToken<ArrayList<ProductModel>>() {}.getType();

                JsonHelper<ArrayList<ProductModel>> jsonHelper = new JsonHelper();
                ArrayList<ProductModel> items = jsonHelper.parseList(response, type);
                callback.onSuccess(items);
            }

            @Override
            public void onFailure(String error) {
                callback.onFailure(error);
            }
        });
    }

    public void getTrendingProducts(ResponseCallBack<ArrayList<ProductModel>> callback) {
        ClientService clientService = new ClientService("Product/new-arrival-products");
        clientService.executeGet(new ResponseCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                Type type = new TypeToken<ArrayList<ProductModel>>() {}.getType();

                JsonHelper<ArrayList<ProductModel>> jsonHelper = new JsonHelper();
                ArrayList<ProductModel> items = jsonHelper.parseList(response, type);
                callback.onSuccess(items);
            }

            @Override
            public void onFailure(String error) {
                callback.onFailure(error);
            }
        });
    }

    public void getProductsByCategory(String categoryId, ResponseCallBack<ArrayList<ProductModel>> callback) {
        ClientService clientService = new ClientService("Product/available-products-by-category?categoryId=" + categoryId);
        clientService.executeGet(new ResponseCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                Type type = new TypeToken<ArrayList<ProductModel>>() {}.getType();

                JsonHelper<ArrayList<ProductModel>> jsonHelper = new JsonHelper();
                ArrayList<ProductModel> items = jsonHelper.parseList(response, type);
                callback.onSuccess(items);
            }

            @Override
            public void onFailure(String error) {
                callback.onFailure(error);
            }
        });
    }

    public void getProductById(int productId, ResponseCallBack<ProductModel> callback) {
        ClientService clientService = new ClientService("Product/product-by-id?productId=" + productId);
        clientService.executeGet(new ResponseCallBack<String>() {
            @Override
            public void onSuccess(String response) {

                JsonHelper<ProductModel> jsonHelper = new JsonHelper();
                ProductModel item = jsonHelper.parse(response, ProductModel.class);
                callback.onSuccess(item);
            }

            @Override
            public void onFailure(String error) {
                callback.onFailure(error);
            }
        });
    }
}
